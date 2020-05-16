package com.diplomework.coffeenative.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.diplomework.coffeenative.R
import com.diplomework.coffeenative.activity.SecondActivity
import com.diplomework.coffeenative.data.model.Product
import com.diplomework.coffeenative.data.repo.DataProvider
import com.diplomework.coffeenative.view.recycler.ProductListAdapter
import kotlinx.coroutines.launch

class ProductListFragment : Fragment() {

    private lateinit var _adapter: ProductListAdapter
    private lateinit var _recycler: RecyclerView
    private lateinit var swiper: SwipeRefreshLayout
    private lateinit var _noItemsHint: TextView
    private val _list: MutableList<Product> = DataProvider.products

    companion object {
        fun newInstance() = ProductListFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View =  inflater.inflate(R.layout.fragment_product_list, container, false)

        swiper = view.findViewById(R.id.product_list_swipe_container)
        swiper.setOnRefreshListener {
            swiper.isRefreshing = true
            onRefreshStart()
        }

        _noItemsHint = view.findViewById(R.id.products_empty_list_hint)
        _noItemsHint.text = getString(R.string.refresh_hint_text, "товаров")

        _recycler = view.findViewById(R.id.product_list_recycler)

        val callback: ProductListAdapter.Callback = object : ProductListAdapter.Callback {
            override fun showProductInfo(clickedItem: Product) {
                startActivity(SecondActivity.showProductIntent(requireContext(), clickedItem))
            }

            override fun addToCart(clickedItem: Product) {
                DataProvider.addProductToCart(clickedItem)

                Toast.makeText(
                    requireContext(),
                    "${clickedItem.name} добавлено в корзину",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        _adapter = ProductListAdapter(requireContext(), _list, callback)
        _recycler.adapter = _adapter
        _recycler.layoutManager = GridLayoutManager(requireContext(), 2)

        updateUI()
        return view
    }

    private fun updateUI() {
        _adapter.notifyDataSetChanged()
        if (_adapter.itemCount == 0) {
            // hide recycler and show message
            _recycler.visibility = View.INVISIBLE
            _noItemsHint.visibility = View.VISIBLE
        } else {
            _recycler.visibility = View.VISIBLE
            _noItemsHint.visibility = View.INVISIBLE
        }
    }

    private fun onRefreshStart() {
        if (DataProvider.isNetworkOnline) {
            lifecycleScope.launch {
                fetchProducts()
            }
        } else {
            val c = requireContext()
            Toast.makeText(c, c.getString(R.string.no_internet_message), Toast.LENGTH_SHORT).show()
        }
        onRefreshStop()
    }

    private fun onRefreshStop() {
        swiper.isRefreshing = false
        updateUI()
    }

    private suspend fun fetchProducts() {
        try {
            DataProvider.updateProducts()
            _adapter.notifyDataSetChanged()
        } catch (e: java.net.SocketTimeoutException) {
            Toast.makeText(
                requireContext(),
                "Произошла ошибка сети, попробуйте позже",
                Toast.LENGTH_SHORT
            ).show()
            e.printStackTrace()
        }
    }
}
