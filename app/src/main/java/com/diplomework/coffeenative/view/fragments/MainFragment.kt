package com.diplomework.coffeenative.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.diplomework.coffeenative.R
import com.diplomework.coffeenative.view.pager.TabAdapter
import com.google.android.material.tabs.TabLayout

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        retainInstance = true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_main, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pagerAdapter = TabAdapter(childFragmentManager)

        pagerAdapter.addItem(ProductListFragment.newInstance(), getString(R.string.products_tab_title))
        pagerAdapter.addItem(StatisticFragment.newInstance(), getString(R.string.statistics_tab_title))
        pagerAdapter.addItem(HistoryFragment.newInstance(), getString(R.string.history_tab_title))

        val viewPager: ViewPager = view.findViewById(R.id.view_pager)
        viewPager.adapter = pagerAdapter

        val tabLayout: TabLayout = view.findViewById(R.id.tabs)
        tabLayout.setupWithViewPager(viewPager)
    }
}