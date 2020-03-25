package com.diplomework.coffeenative.data.model

data class Product(
    var id: Int,
    var title: String,
    var price: Int,
    var pic: String,
    var description: String,
    var productType: ProductType
)
