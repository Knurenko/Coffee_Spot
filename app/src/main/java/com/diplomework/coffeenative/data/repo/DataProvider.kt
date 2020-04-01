package com.diplomework.coffeenative.data.repo

import android.util.Log
import com.diplomework.coffeenative.data.model.Order
import com.diplomework.coffeenative.data.model.Product
import com.diplomework.coffeenative.data.model.ProductType
import java.util.*
import kotlin.collections.ArrayList

/**
 * singleton to hold data of an app
 */
object DataProvider {

    private const val TAG = "check"

    // variables for data saving and restoring
    data class DataBundle(
        val products: List<Product>,
        val orders: List<Order>,
        val itemsInCart: List<Product>
    )
    private const val PREFS_TAG = "DataBundle_tag"

    private var products: MutableList<Product> = ArrayList()

    private var orders: MutableList<Order> = ArrayList()

    private var itemsInCart: MutableList<Product> = ArrayList()

    fun setProducts(list: List<Product>) {
        products.clear()
        products.addAll(list)

        logList("here we go, thats the products:", list)
    }
    fun getProducts() = products

    fun setOrders(list: List<Order>) {
        orders.clear()
        orders.addAll(list)

        logList("here we go, thats the orders:", list)
    }
    fun getOrders() = orders

    fun setCartItems(list: List<Product>) {
        itemsInCart.clear()
        itemsInCart.addAll(list)

        logList("here we go, thats items in cart", list)
    }

    fun getCarItems() = itemsInCart

    fun findProduct(id: Int): Product = products.find { it.id == id } ?: products[0]

    fun addProductToCart(item: Product) {
        itemsInCart.add(item)
    }

    fun removeproductFromCart(item: Product) {
        itemsInCart.remove(item)
    }

    fun completeOrder() {
        var maxId = 0
        orders.forEach { if (it.id > maxId) maxId = it.id }

        val newOrder = Order(maxId + 1, Date(), itemsInCart.toList())
        orders.add(newOrder)
        itemsInCart.clear()
    }

    /**
     * method to save product list values into shared preferences
     */
    fun saveDataToSharedPrefs() {
        val bundle = DataBundle(products, orders, itemsInCart)
        PreferenceHelper.save(bundle, PREFS_TAG)
    }

    /**
     * method to load product list values from shared preferences
     */
    fun loadDataFromSharedPrefs() {
        val bundle = PreferenceHelper.load<DataBundle>(PREFS_TAG)

        setProducts(if (bundle?.products != null && bundle.products.isNotEmpty()) bundle.products else fillDummyProducts())
        setOrders(if (bundle?.orders != null && bundle.orders.isNotEmpty()) bundle.orders else fillDummyOrders())
        if (bundle?.itemsInCart != null && bundle.itemsInCart.isNotEmpty()) setCartItems(bundle.itemsInCart)
    }

    private fun fillDummyOrders(): List<Order> {

        Log.i(TAG, "FILL ORDERS BY DUMMY CONTENT")
        val date = Date()

        val dummyOrders = ArrayList<Order>()

        dummyOrders.add(Order(1, date, listOf(products[0], products[1], products[2])))
        dummyOrders.add(Order(2, date, listOf(products[3], products[4])))
        dummyOrders.add(Order(3, date, listOf(products[5])))
        dummyOrders.add(Order(4, date, listOf(products[6], products[7], products[8])))

        return dummyOrders
    }

    private fun fillDummyProducts(): List<Product> {

        Log.i(TAG, "FILL PRODUCTS BY DUMMY CONTENT!")

        val dummyProducts = ArrayList<Product>()

        dummyProducts.add(
            Product(
                1, "Американо", 20,
                "https://upload.wikimedia.org/wikipedia/commons/thumb/4/45/A_small_cup_of_coffee.JPG/1200px-A_small_cup_of_coffee.JPG",
                "desc", ProductType.DRINK
            )
        )
        dummyProducts.add(
            Product(
                2, "Капучино", 15,
                "https://upload.wikimedia.org/wikipedia/commons/1/16/Classic_Cappuccino.jpg",
                "desc", ProductType.DRINK
            )
        )
        dummyProducts.add(
            Product(
                3, "Латте", 10,
                "https://gogol-mogol.su/uploads/posts/2017-02/1485941008_kofe.jpg",
                "desc", ProductType.DRINK
            )
        )
        dummyProducts.add(
            Product(
                4, "Эспрессо", 10,
                "https://cs12.pikabu.ru/post_img/big/2019/05/15/7/1557915500180338047.jpg",
                "desc", ProductType.DRINK
            )
        )
        dummyProducts.add(
            Product(
                5, "Лонг Блэк", 20,
                "https://www.coffeebeanshop.com.au/uploads/4/1/4/4/4144860/7703776_orig.jpg",
                "desc", ProductType.DRINK
            )
        )
        dummyProducts.add(
            Product(
                6, "Айриш Кофе", 25,
                "https://coffeefan.info/wp-content/uploads/2018/09/kofe-latte-makiato-v-domashnikh-usloviyakh.jpg",
                "desc", ProductType.DRINK
            )
        )
        dummyProducts.add(
            Product(
                7, "Мокка", 15,
                "http://kivahan.ru/wp-content/uploads/2015/05/coffee-325-e1431189401330.jpg",
                "desc", ProductType.DRINK
            )
        )
        dummyProducts.add(
            Product(
                8, "nikita", 999,
                "https://sun9-40.userapi.com/c857720/v857720670/1326e5/Q7i7ucO4C58.jpg",
                "desc", ProductType.DRINK
            )
        )
        dummyProducts.add(
            Product(
                9, "Сендвич с курицей", 25,
                "http://v.img.com.ua/nxs140/b/600x500/0/c0/0623c1332e823f2445a69c53574bcc00.jpg",
                "desc", ProductType.FOOD
            )
        )
        dummyProducts.add(
            Product(
                10, "Сендвич с ветчиной", 20,
                "http://v.img.com.ua/nxs140/b/600x500/0/c0/0623c1332e823f2445a69c53574bcc00.jpg",
                "desc", ProductType.FOOD
            )
        )
        dummyProducts.add(
            Product(
                11, "Гамбургер", 30,
                "https://lh6.googleusercontent.com/proxy/Hoa9mDyUK0b-JG9GxvtTgrMJjKTX0CIo6dKANFA-YqIqxHMfxw5xDLBJcUGuvFzu6x7kZ9NyBCEpzPa7x8xr--0b6bL8VecjoM00jFUsC2Ijak9bRSZ0V7d0",
                "desc", ProductType.FOOD
            )
        )
        dummyProducts.add(
            Product(
                12, "Хоп-Дог", 20,
                "https://www.menslife.com/upload/iblock/a2a/4_retsepta_khot_dogov_v_domashnikh_usloviyakh.jpg",
                "desc", ProductType.FOOD
            )
        )

        return dummyProducts
    }

    private fun <T> logList(prologue: String, list: List<T>) {
        Log.i(TAG, prologue)
        for (i in list.indices) Log.i(TAG, "item $i: ${list[i].toString()}")
    }
}
