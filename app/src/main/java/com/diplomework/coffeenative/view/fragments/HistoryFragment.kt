package com.diplomework.coffeenative.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.diplomework.coffeenative.R
import com.diplomework.coffeenative.data.model.Order
import com.diplomework.coffeenative.data.repo.DataProvider
import com.diplomework.coffeenative.view.recycler.OrderListAdapter

class HistoryFragment : Fragment() {

    private lateinit var _adapter: OrderListAdapter
    private val _orderList: MutableList<Order> = DataProvider.getOrders()

    companion object {
        fun newInstance() = HistoryFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_history, container, false)

        val recycler: RecyclerView = view.findViewById(R.id.history_recycler)

        _adapter = OrderListAdapter(requireContext(), _orderList)
        recycler.adapter = _adapter
        recycler.layoutManager = LinearLayoutManager(requireContext())

        return view
    }
}
