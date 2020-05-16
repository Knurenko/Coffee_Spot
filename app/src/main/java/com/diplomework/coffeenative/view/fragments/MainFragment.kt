package com.diplomework.coffeenative.view.fragments

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.diplomework.coffeenative.R
import com.diplomework.coffeenative.activity.SecondActivity
import com.diplomework.coffeenative.data.repo.DataProvider
import com.diplomework.coffeenative.view.pager.TabAdapter
import com.google.android.material.tabs.TabLayout

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        retainInstance = true
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_main, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pagerAdapter = TabAdapter(childFragmentManager)

        val products = ProductListFragment.newInstance()
        val statistics = StatisticFragment.newInstance()

        val dataUpdateCallback = object : HistoryFragment.IDataUpdatedCallback {
            override fun onUpdateFinish() = statistics.onResume()
        }

        val history = HistoryFragment.newInstance(dataUpdateCallback)

        pagerAdapter.addItem(products, getString(R.string.products_tab_title))
        pagerAdapter.addItem(history, getString(R.string.history_tab_title))
        pagerAdapter.addItem(statistics, getString(R.string.statistics_tab_title))

        val viewPager: ViewPager = view.findViewById(R.id.view_pager)
        viewPager.adapter = pagerAdapter

        val tabLayout: TabLayout = view.findViewById(R.id.tabs)
        tabLayout.setupWithViewPager(viewPager)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.cart) {
            if (DataProvider.itemsInCart.size != 0) {
                startActivity(SecondActivity.showCartIntent(requireContext()))
            } else {
                Toast.makeText(requireContext(), "Корзина пуста", Toast.LENGTH_SHORT).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
