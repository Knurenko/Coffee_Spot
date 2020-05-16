package com.diplomework.coffeenative.data.model

data class Product(
    val id: Int,
    val name: String,
    val price: Int,
    val pic: String,
    val description: String,
    val type: ProductType
) {
    var count: Int = 1
    fun getStockPrice(): Int {
        return price * count
    }
}
