package com.diplomework.coffeenative.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.diplomework.coffeenative.R
import com.diplomework.coffeenative.data.model.Product
import com.diplomework.coffeenative.data.repo.DataProvider
import com.diplomework.coffeenative.view.recycler.ProductsOfOrderAdapter

class CartFragment : Fragment() {
    companion object {
        fun newInstance() = CartFragment()
    }

    private val _items: MutableList<Product> = DataProvider.getCarItems()
    private lateinit var _adapter: ProductsOfOrderAdapter

    private lateinit var totalPrice: TextView
    private lateinit var recycler: RecyclerView

    private fun refreshTotalCost() {
        var total = 0
        _items.forEach { total += it.price }
        totalPrice.text = total.toString()
    }

    private fun refreshUI() {
        refreshTotalCost()
        _adapter.notifyDataSetChanged()
    }

    private fun finishOrder() {
        DataProvider.completeOrder()
        Toast.makeText(requireContext(), "Заказ успешно оформлен!", Toast.LENGTH_SHORT).show()
        activity?.finish()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_cart, container, false)
        recycler = view.findViewById(R.id.cart_recycler)
        totalPrice = view.findViewById(R.id.cart_total_cost)

        _adapter = ProductsOfOrderAdapter(requireContext(), _items)

        recycler.adapter = _adapter
        recycler.layoutManager = LinearLayoutManager(requireContext())

        refreshUI()

        val proceedButton: Button = view.findViewById(R.id.proceed_order_btn)
        proceedButton.setOnClickListener { finishOrder() }

        activity?.title = "Корзина"

        return view
    }
}