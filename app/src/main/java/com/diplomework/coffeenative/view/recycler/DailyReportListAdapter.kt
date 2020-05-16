package com.diplomework.coffeenative.view.recycler

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.diplomework.coffeenative.R
import com.diplomework.coffeenative.data.model.DailyReport
import java.text.SimpleDateFormat
import java.util.*

class DailyReportListAdapter(private val context: Context, private val reports: List<DailyReport>) :
    RecyclerView.Adapter<DailyReportListAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val date: TextView = itemView.findViewById(R.id.daily_report_item_date)
        private val itemsSold: TextView = itemView.findViewById(R.id.daily_report_item_sold_count)
        private val moneyReceived: TextView =
            itemView.findViewById(R.id.daily_report_item_money_received)

        fun bind(item: DailyReport) {
            val pattern = "dd MM yyyy"
            date.text = SimpleDateFormat(pattern, Locale.getDefault()).format(item.date)
            itemsSold.text = item.itemsSold.toString()
            moneyReceived.text = item.moneyReceived.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(context).inflate(R.layout.daily_report_item, parent, false))

    override fun getItemCount(): Int = reports.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(reports[position])
    }
}