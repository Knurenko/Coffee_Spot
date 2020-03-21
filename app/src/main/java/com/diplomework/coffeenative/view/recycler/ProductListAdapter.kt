package com.diplomework.coffeenative.view.recycler

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.diplomework.coffeenative.R
import com.diplomework.coffeenative.data.Product

class ProductListAdapter (private val context: Context, private val products: MutableList<Product>) : RecyclerView.Adapter<ProductListAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val pic: ImageView = itemView.findViewById(R.id.product_item_pic)
        private val title: TextView = itemView.findViewById(R.id.product_item_title)
        private val price: TextView = itemView.findViewById(R.id.product_item_price)
        private val button: Button = itemView.findViewById(R.id.product_item_btn)

        fun bind (item: Product) {
            title.text = item.title
            price.text = item.price
            button.text = "В корзину"
            button.setOnClickListener {
//                Toast.makeText(context, "hello dude", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.product_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return products.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(products[position])
    }
}