package com.diplomework.coffeenative.data.model

import java.util.*

data class Order(
    val id: Int,
    val time: Date,
    val products: List<Product>
) {
    val totalPrice: Int

    init {
        var price = 0
        for (item: Product in products) {
            price += item.price
        }
        totalPrice = price
    }
}