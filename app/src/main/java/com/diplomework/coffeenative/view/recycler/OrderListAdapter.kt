package com.diplomework.coffeenative.view.recycler

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.diplomework.coffeenative.R
import com.diplomework.coffeenative.data.model.Order
import java.text.SimpleDateFormat
import java.util.*

class OrderListAdapter(private val context: Context, private val orders: MutableList<Order>) :
    RecyclerView.Adapter<OrderListAdapter.ViewHolder>() {

    class ViewHolder(itemView: View, private val context: Context) :
        RecyclerView.ViewHolder(itemView) {
        private val idNumber: TextView = itemView.findViewById(R.id.order_number)
        private val totalCost: TextView = itemView.findViewById(R.id.order_total_cost)
        private val time: TextView = itemView.findViewById(R.id.order_time)

        private val recycler: RecyclerView = itemView.findViewById(R.id.order_item_recycler)

        fun bind(item: Order) {
            idNumber.text = item.id.toString()
            totalCost.text = item.totalPrice.toString()

            val formattedDate = SimpleDateFormat(
                context.getString(R.string.order_item_time_pattern),
                Locale.getDefault()
            ).format(item.time)
            time.text = formattedDate

            val adapter = ProductsOfOrderAdapter(context, item.products)
            recycler.adapter = adapter
            recycler.layoutManager = LinearLayoutManager(context)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.order_item, parent, false),
            context
        )
    }

    override fun getItemCount(): Int {
        return orders.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(orders[position])
    }
}