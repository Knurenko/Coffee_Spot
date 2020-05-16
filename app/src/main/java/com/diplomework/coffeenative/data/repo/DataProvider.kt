package com.diplomework.coffeenative.data.repo

import com.diplomework.coffeenative.data.model.DataBundle
import com.diplomework.coffeenative.data.model.Order
import com.diplomework.coffeenative.data.model.Product
import com.diplomework.coffeenative.data.model.Transaction
import com.diplomework.coffeenative.data.network.NetworkApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.collections.ArrayList

/**
 * singleton to hold data of an app
 */
object DataProvider {

    // variable for network operations
    private val network = NetworkApi.createService()
    var isNetworkOnline = false

    var products: MutableList<Product> = ArrayList()
        private set(list) {
            products.clear()
            products.addAll(list)
        }

    var orders: MutableList<Order> = ArrayList()
        private set(list) {
            orders.clear()
            orders.addAll(list)
        }

    var itemsInCart: MutableList<Product> = ArrayList()
        private set(list) {
            itemsInCart.clear()
            itemsInCart.addAll(list)
        }

    private var transactionQueue: MutableList<Transaction> = ArrayList()

    // Product's methods
    fun findProduct(id: Int): Product = products.find { it.id == id } ?: products[0]
    fun addProductToCart(item: Product) {
        var alreadyInList = false

        itemsInCart.forEach {
            if (it.id == item.id) {
                it.count++
                alreadyInList = true
            }
        }
        if (!alreadyInList) {
            item.count = 1
            itemsInCart.add(item)
        }
    }

    suspend fun updateProducts() = withContext(Dispatchers.IO) {
        val fetch = async { network.fetchProducts() }

        products = fetch.await().toMutableList()
    }

    // Order's methods
    fun completeOrder(itemsList: List<Product>) {
        var maxId = 0
        orders.forEach { if (it.id > maxId) maxId = it.id }

        val newOrder = Order(maxId + 1, Date(), itemsList)
        val transaction = Transaction.fromOrder(newOrder)
        transaction.type = Transaction.Type.CREATE
        transactionQueue.add(transaction)

        orders.add(newOrder)
        itemsInCart.clear()

        Statistics.calc()
    }

    fun removeOrder(order: Order) {
        orders.remove(order)
        val transaction = Transaction.fromOrder(order)
        transaction.type = Transaction.Type.CREATE

        if (transactionQueue.contains(transaction)) {
            transactionQueue.remove(transaction)
        } else {
            transaction.type = Transaction.Type.DELETE
            transactionQueue.add(transaction)
        }
    }

    suspend fun updateOrders() = withContext(Dispatchers.IO) {

        // checks if there is some requests in queue
        while (transactionQueue.size > 0) {
            // gets first request from queue and executes it
            val transaction = transactionQueue.removeAt(0)
            when (transaction.type) {
                Transaction.Type.CREATE -> {
                    val add = async { network.sendTransaction(transaction) }
                    add.await()
                }

                Transaction.Type.DELETE -> {
                    val remove = async { network.removeTransaction(transaction.id) }
                    remove.await()
                }
                else -> {
                }
            }
        }

        // updates orders list
        val fetch = async { network.fetchTransactions() }
        val transactions = fetch.await()
        orders = transactions.map { it.toOrder() }.toMutableList()
    }

    /**
     * method to save product list values into shared preferences
     */
    fun saveDataToSharedPrefs() {
        val bundle = DataBundle(products, orders, itemsInCart, transactionQueue)
        PreferenceHelper.save(bundle, DataBundle.PREFS_TAG)
    }

    /**
     * method to load product list values from shared preferences
     */
    fun loadDataFromSharedPrefs() {
        val bundle = PreferenceHelper.load<DataBundle>(DataBundle.PREFS_TAG)

        if (bundle?.products != null && bundle.products.isNotEmpty()) {
            products = bundle.products.toMutableList()
        }
        if (bundle?.orders != null && bundle.orders.isNotEmpty()) {
            orders = bundle.orders.toMutableList()
        }
        if (bundle?.itemsInCart != null && bundle.itemsInCart.isNotEmpty()) {
            itemsInCart = bundle.itemsInCart.toMutableList()
        }
        if (bundle?.transactions != null && bundle.transactions.isNotEmpty()) {
            transactionQueue = bundle.transactions.toMutableList()
        }
    }
}
