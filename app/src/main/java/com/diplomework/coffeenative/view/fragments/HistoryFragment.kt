package com.diplomework.coffeenative.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.diplomework.coffeenative.R
import com.diplomework.coffeenative.data.model.Order
import com.diplomework.coffeenative.data.repo.DataProvider
import com.diplomework.coffeenative.data.repo.Statistics
import com.diplomework.coffeenative.view.recycler.OrderListAdapter
import kotlinx.coroutines.launch

class HistoryFragment : Fragment() {

    private lateinit var _adapter: OrderListAdapter
    private lateinit var _recycler: RecyclerView
    private lateinit var swiper: SwipeRefreshLayout
    private lateinit var _emptyListHint: TextView
    private val _orderList: MutableList<Order> = DataProvider.orders

    companion object {
        fun newInstance() = HistoryFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_history, container, false)

        swiper = view.findViewById(R.id.history_swipe_container)
        swiper.setOnRefreshListener {
            swiper.isRefreshing = true
            onRefreshStart()
        }

        _emptyListHint = view.findViewById(R.id.history_empty_list_hint)
        _emptyListHint.text = getString(R.string.refresh_hint_text, "заказов")

        _recycler = view.findViewById(R.id.history_recycler)

        val layoutManager = LinearLayoutManager(requireContext())
        layoutManager.stackFromEnd = true
        layoutManager.reverseLayout = true

        val deleteListener = object : OrderListAdapter.IItemDeleteListener {
            override fun onDelete(item: Order, position: Int) {
                DataProvider.removeOrder(item)
                _adapter.notifyItemRemoved(position)
                Toast.makeText(
                    requireContext(),
                    "Заказ ${item.id} успешно удалён",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        _adapter = OrderListAdapter(requireContext(), _orderList, deleteListener)
        _recycler.adapter = _adapter
        _recycler.layoutManager = layoutManager

        updateUi()
        return view
    }

    private fun updateUi() {
        _adapter.notifyDataSetChanged()
        if (_adapter.itemCount == 0) {
            // show hint and hide recycler
            _recycler.visibility = View.INVISIBLE
            _emptyListHint.visibility = View.VISIBLE
        } else {
            // show recycler and hide hint
            _emptyListHint.visibility = View.INVISIBLE
            _recycler.visibility = View.VISIBLE
            _recycler.scrollToPosition(_orderList.size - 1)
        }
    }

    override fun onResume() {
        super.onResume()
        updateUi()
    }

    private fun onRefreshStart() {
        if (DataProvider.isNetworkOnline) {
            lifecycleScope.launch {
                synchronizeHistory()
            }
        } else {
            val c = requireContext()
            Toast.makeText(c, c.getString(R.string.no_internet_message), Toast.LENGTH_SHORT).show()
        }
        onRefreshStop()
    }

    private fun onRefreshStop() {
        swiper.isRefreshing = false
        updateUi()
    }

    private suspend fun synchronizeHistory() {
        try {
            DataProvider.updateOrders()
            _adapter.notifyDataSetChanged()
            Statistics.calc()
        } catch (e: Exception) {
            Toast.makeText(
                requireContext(),
                "Произошла ошибка сети, попробуйте позже",
                Toast.LENGTH_SHORT
            ).show()
            e.printStackTrace()
        }
    }
}
