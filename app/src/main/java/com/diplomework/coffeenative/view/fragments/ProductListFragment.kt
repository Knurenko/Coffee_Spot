package com.diplomework.coffeenative.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.diplomework.coffeenative.R
import com.diplomework.coffeenative.data.model.Product
import com.diplomework.coffeenative.data.repo.DataProvider
import com.diplomework.coffeenative.view.recycler.ProductListAdapter

class ProductListFragment : Fragment() {

    private lateinit var _adapter: ProductListAdapter
    private lateinit var _list: MutableList<Product>

    companion object {
        fun newInstance() = ProductListFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View =  inflater.inflate(R.layout.fragment_product_list, container, false)

        val recycler: RecyclerView = view.findViewById(R.id.product_list_recycler)
        _list = DataProvider.getProducts()

        val callback: ProductListAdapter.Callback = object : ProductListAdapter.Callback {
            override fun showProductInfo(clickedItem: Product) {
                Toast.makeText(
                    requireContext(),
                    "show product info! ${clickedItem.title}",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun addToCart(clickedItem: Product) {
                Toast.makeText(
                    requireContext(),
                    "add to cart! ${clickedItem.title}",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }

        _adapter = ProductListAdapter(requireContext(), _list, callback)
        recycler.adapter = _adapter
        recycler.layoutManager = GridLayoutManager(requireContext(), 2)

        return view
    }
}
