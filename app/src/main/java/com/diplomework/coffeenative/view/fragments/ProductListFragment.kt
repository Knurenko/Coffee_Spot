package com.diplomework.coffeenative.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.diplomework.coffeenative.R
import com.diplomework.coffeenative.data.Product
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
        _list = ArrayList()
        fillListByDummyContent()

        _adapter = ProductListAdapter(requireContext(), _list)
        recycler.adapter = _adapter
        recycler.layoutManager = GridLayoutManager(requireContext(), 3)

        return view
    }

    private fun fillListByDummyContent() {
        _list.add(Product(1, "huipizda", "40гривен", "kczxmlckma"))
        _list.add(Product(2, "r2d2", "1", "kczxmlckma"))
        _list.add(Product(3, "zxasdqwe", "4", "kczxmlckma"))
        _list.add(Product(4, "bitch plz", "5", "kczxmlckma"))
        _list.add(Product(5, "hey nigga", "6", "kczxmlckma"))
        _list.add(Product(6, "nice cock", "22", "kczxmlckma"))
        _list.add(Product(7, "zalupa", "14", "kczxmlckma"))
        _list.add(Product(8, "nikita soska", "15", "kczxmlckma"))
    }
}
