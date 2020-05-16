package com.diplomework.coffeenative.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.diplomework.coffeenative.R
import com.diplomework.coffeenative.data.model.DailyReport
import com.diplomework.coffeenative.data.repo.Statistics
import com.diplomework.coffeenative.view.recycler.DailyReportListAdapter


class StatisticFragment : Fragment() {

    private lateinit var _adapter: DailyReportListAdapter
    private val list: MutableList<DailyReport> = ArrayList()
    private lateinit var itemsSoldMonth: TextView
    private lateinit var itemsSoldTotal: TextView
    private lateinit var moneyReceivedMonth: TextView
    private lateinit var moneyReceivedTotal: TextView

    companion object {
        fun newInstance() = StatisticFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_statistic, container, false)

        //init adapter & layout manager
        list.addAll(Statistics.dailyReportHistory)
        _adapter = DailyReportListAdapter(requireContext(), list)
        val layoutManager = LinearLayoutManager(requireContext())
        layoutManager.stackFromEnd = true
        layoutManager.reverseLayout = true

        // init views
        itemsSoldMonth = view.findViewById(R.id.items_sold_month)
        itemsSoldTotal = view.findViewById(R.id.items_sold_total)
        moneyReceivedMonth = view.findViewById(R.id.money_received_month)
        moneyReceivedTotal = view.findViewById(R.id.money_received_total)
        val recycler: RecyclerView = view.findViewById(R.id.statistics_adapter)

        updateUI()

        // fill recycler
        recycler.adapter = _adapter
        recycler.layoutManager = layoutManager

        return view
    }

    private fun updateUI() {
        // fill text
        itemsSoldMonth.text = Statistics.itemsSoldCurrentMonth.toString()
        itemsSoldTotal.text = Statistics.itemsSoldTotal.toString()
        moneyReceivedMonth.text = Statistics.receivedMoneyCurrentMonth.toString()
        moneyReceivedTotal.text = Statistics.receivedMoneyTotal.toString()
    }

    override fun onResume() {
        super.onResume()
        list.clear()
        list.addAll(Statistics.dailyReportHistory)
        _adapter.notifyDataSetChanged()

        updateUI()
    }
}
