package com.diplomework.coffeenative.view.recycler

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.diplomework.coffeenative.R
import com.diplomework.coffeenative.data.model.Order
import java.text.SimpleDateFormat
import java.util.*

class OrderListAdapter(
    private val context: Context,
    private val orders: MutableList<Order>,
    private val listener: IItemDeleteListener
) :
    RecyclerView.Adapter<OrderListAdapter.ViewHolder>() {

    interface IItemDeleteListener {
        fun onDelete(item: Order, position: Int)
    }

    class ViewHolder(itemView: View, private val context: Context) :
        RecyclerView.ViewHolder(itemView) {
        private val idNumber: TextView = itemView.findViewById(R.id.order_number)
        private val totalCost: TextView = itemView.findViewById(R.id.order_total_cost)
        private val time: TextView = itemView.findViewById(R.id.order_time)

        private val recycler: RecyclerView = itemView.findViewById(R.id.order_item_recycler)

        fun bind(item: Order, listener: IItemDeleteListener, position: Int) {
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

            itemView.setOnLongClickListener {
                it.showContextMenu()
            }

            @Suppress("DEPRECATION")
            recycler.isLayoutFrozen = true

            itemView.setOnCreateContextMenuListener { menu, _, _ ->
                menu?.add("Удалить заказ №${item.id}")?.setOnMenuItemClickListener {
                    AlertDialog.Builder(context)
                        .setTitle("Удаление заказа #${item.id}")
                        .setMessage("Вы уверены?")
                        .setPositiveButton("Да") { _, _ -> listener.onDelete(item, position) }
                        .setNegativeButton("Отмена") { _, _ -> Unit }
                        .setCancelable(true)
                        .create()
                        .show()
                    true
                }
            }
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
        holder.bind(orders[position], listener, position)
    }
}