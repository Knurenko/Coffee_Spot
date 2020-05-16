package com.diplomework.coffeenative.data.repo

import com.diplomework.coffeenative.data.model.DailyReport
import java.text.SimpleDateFormat
import java.util.*
import kotlin.Comparator
import kotlin.collections.HashMap

object Statistics {

    var itemsSoldTotal = 0; private set
    var itemsSoldCurrentMonth = 0; private set

    var receivedMoneyTotal = 0; private set
    var receivedMoneyCurrentMonth = 0; private set

    var dailyReportHistory: List<DailyReport> = ArrayList(); private set

    fun calc() {
        val dailyReports: MutableMap<String, DailyReport> = HashMap()

        itemsSoldTotal = 0
        receivedMoneyTotal = 0

        itemsSoldCurrentMonth = 0
        receivedMoneyCurrentMonth = 0

        val stringPattern = "dd-MM-yyyy"

        @Suppress("DEPRECATION")
        fun isCurrentMonth(date: Date): Boolean =
            (Date().month == date.month && Date().year == date.year)

        fun getDateString(date: Date): String =
            SimpleDateFormat(stringPattern, Locale.getDefault()).format(date)

        DataProvider.orders.forEach {
            // init daily report map
            val date = getDateString(it.time)

            if (!dailyReports.containsKey(date)) {
                dailyReports[date] =
                    DailyReport(it.time)
            }
            dailyReports[date]?.addOrder(it)

            //calc month statistic
            if (isCurrentMonth(it.time)) {
                receivedMoneyCurrentMonth += it.totalPrice
                itemsSoldCurrentMonth += it.totalCount
            }

            // calc total statistic
            receivedMoneyTotal += it.totalPrice
            itemsSoldTotal += it.totalCount
        }

        dailyReportHistory = dailyReports.values.sortedWith(
            Comparator { o1, o2 -> o1.date.compareTo(o2.date) }
        )
    }
}