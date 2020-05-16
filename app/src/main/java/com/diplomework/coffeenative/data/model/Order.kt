package com.diplomework.coffeenative.data.model

import java.util.*

data class Order(
    val id: Int,
    val time: Date,
    val products: List<Product>
) {
    val totalPrice: Int = products.sumBy { it.getStockPrice() }
    val totalCount: Int = products.sumBy { it.count }
}