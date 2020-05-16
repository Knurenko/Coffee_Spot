package com.diplomework.coffeenative.data.model

import android.annotation.SuppressLint
import com.diplomework.coffeenative.data.repo.DataProvider
import com.google.gson.GsonBuilder
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

data class Transaction(
    val id: Int,
    val date: String,
    val items: String,
    val payment: Int,
    var type: Type = Type.NOTHING
) {
    enum class Type {
        NOTHING,
        DELETE,
        CREATE
    }

    data class ProductCountBundle(val map: HashMap<Int, Int>) {
        companion object {
            fun fromList(list: List<Product>): ProductCountBundle {
                val map: MutableMap<Int, Int> = HashMap()
                list.forEach { map[it.id] = it.count }

                return ProductCountBundle(map as HashMap<Int, Int>)
            }
        }

        fun toList(): List<Product> {
            val items: MutableList<Product> = ArrayList()
            map.keys.forEach {
                val item = DataProvider.findProduct(it)
                item.count = map[it]!!

                items.add(item)
            }

            return items.toList()
        }
    }

    companion object {

        @SuppressLint("ConstantLocale")
        private val DATE_FORMAT = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())

        fun fromOrder(order: Order): Transaction {
            val date = DATE_FORMAT.format(order.time)
            val payment = order.totalPrice

            val products = ProductCountBundle.fromList(order.products)
            val items = GsonBuilder().create().toJson(products)

            return Transaction(order.id, date, items, payment)
        }
    }

    fun toOrder(): Order {
        val products =
            GsonBuilder().create().fromJson(items, ProductCountBundle::class.java).toList()
        val time = DATE_FORMAT.parse(date)!!

        return Order(id, time, products)
    }
}