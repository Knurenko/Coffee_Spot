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
import com.diplomework.coffeenative.data.model.Product
import com.diplomework.coffeenative.data.network.NetworkApi
import com.squareup.picasso.Picasso

class ProductListAdapter(
    private val context: Context,
    private val products: MutableList<Product>,
    private val callback: Callback
) : RecyclerView.Adapter<ProductListAdapter.ViewHolder>() {

    interface Callback {
        fun showProductInfo(clickedItem: Product)
        fun addToCart(clickedItem: Product)
    }

    class ViewHolder(itemView: View, private val callback: Callback) :
        RecyclerView.ViewHolder(itemView) {
        private val pic: ImageView = itemView.findViewById(R.id.product_item_pic)
        private val title: TextView = itemView.findViewById(R.id.product_item_title)
        private val price: TextView = itemView.findViewById(R.id.product_item_price)
        private val button: Button = itemView.findViewById(R.id.product_item_btn)

        /**
         * binds instance of VIEW to instance of DATA
         * e.g. fill view fields with data values.
         */
        fun bind (item: Product) {
            title.text = item.name
            price.text = item.price.toString()

            val picAddress = "${NetworkApi.BASE_URL}${NetworkApi.PIC_STORAGE_URL}/${item.pic}"

            Picasso.get()
                .load(picAddress)
                .resize(300, 300)
                .centerCrop()
                .into(pic)

            button.text = "В корзину"
            button.setOnClickListener { callback.addToCart(item) }

            itemView.setOnClickListener { callback.showProductInfo(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.product_item, parent, false)
        return ViewHolder(view, callback)
    }

    override fun getItemCount(): Int {
        return products.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(products[position])
    }
}