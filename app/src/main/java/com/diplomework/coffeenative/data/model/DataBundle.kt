package com.diplomework.coffeenative.data.model

data class DataBundle(
    val products: List<Product>,
    val orders: List<Order>,
    val itemsInCart: List<Product>,
    val transactions: List<Transaction>
) {
    companion object {
        const val PREFS_TAG = "DataBundle_tag"
    }
}