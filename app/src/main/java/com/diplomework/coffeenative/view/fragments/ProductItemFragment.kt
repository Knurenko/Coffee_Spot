package com.diplomework.coffeenative.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.diplomework.coffeenative.R
import com.diplomework.coffeenative.activity.SecondActivity.Companion.PRODUCT_ID
import com.diplomework.coffeenative.data.repo.DataProvider
import com.squareup.picasso.Picasso


class ProductItemFragment : Fragment() {

    companion object {
        fun newInstance() = ProductItemFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_product, container, false)

        val pic: ImageView = view.findViewById(R.id.product_pic)
        val title: TextView = view.findViewById(R.id.product_title)
        val cost: TextView = view.findViewById(R.id.product_cost)
        val description: TextView = view.findViewById(R.id.product_description)
        val btn: Button = view.findViewById(R.id.add_to_cart_button)

        val productId = activity?.intent?.getIntExtra(PRODUCT_ID, 0) ?: 0
        val product = DataProvider.findProduct(productId)

        activity?.title = product.title

        Picasso.get()
            .load(product.pic)
            .resize(300, 300)
            .centerCrop()
            .into(pic)
        title.text = product.title
        cost.text = product.price.toString()
        description.text = product.description

        btn.setOnClickListener { DataProvider.addProductToCart(product) }

        return view
    }
}
