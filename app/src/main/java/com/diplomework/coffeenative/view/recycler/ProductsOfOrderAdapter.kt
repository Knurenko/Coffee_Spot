package com.diplomework.coffeenative.view.recycler

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.diplomework.coffeenative.R
import com.diplomework.coffeenative.data.model.Product

class ProductsOfOrderAdapter(
    private val context: Context,
    private val products: List<Product>,
    private val callback: IStockCountChanger? = null
) :
    RecyclerView.Adapter<ProductsOfOrderAdapter.ViewHolder>() {

    interface IStockCountChanger {
        fun increase(item: Product)
        fun decrease(item: Product)
        fun showDeletingHint()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val count: TextView = itemView.findViewById(R.id.product_for_order_count)
        private val title: TextView = itemView.findViewById(R.id.product_for_order_title)
        private val price: TextView = itemView.findViewById(R.id.product_for_order_price)

        private val plusButton: TextView = itemView.findViewById(R.id.product_for_order_plus_button)
        private val minusButton: TextView =
            itemView.findViewById(R.id.product_for_order_minus_button)

        fun bind(item: Product, callback: IStockCountChanger?) {
            title.text = item.name
            Log.i("check", "item name: ${item.name} text length: ${item.name.length}")
            price.text = item.getStockPrice().toString()
            count.text = item.count.toString()

            if (callback != null) {
                checkVisibility(item)
                itemView.setOnLongClickListener {
                    callback.showDeletingHint()
                    false
                }
                minusButton.setOnClickListener {
                    if (item.count > 1) callback.decrease(item)
                }
                plusButton.setOnClickListener { callback.increase(item) }
            }
        }

        private fun checkVisibility(item: Product) {
            if (item.count > 1) {
                minusButton.visibility = View.VISIBLE
//                minusButton.isClickable = true
            } else {
                minusButton.visibility = View.GONE
//                minusButton.isClickable = false
            }
            plusButton.visibility = View.VISIBLE

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.product_item_for_order, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return products.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(products[position], callback)
    }
}