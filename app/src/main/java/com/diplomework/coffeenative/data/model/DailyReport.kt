package com.diplomework.coffeenative.data.model

import java.util.*

data class DailyReport(val date: Date) {
    var itemsSold: Int = 0; private set
    var moneyReceived: Int = 0; private set

    fun addOrder(order: Order) {
        itemsSold += order.totalCount
        moneyReceived += order.totalPrice
    }
}