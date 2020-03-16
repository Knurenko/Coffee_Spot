package com.diplomework.coffeenative.view.pager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class TabAdapter(fm: FragmentManager?) : FragmentPagerAdapter(fm!!) {

    private val fragmentList: MutableList<Fragment> = ArrayList()
    private val pageTitleList: MutableList<String> = ArrayList()

    fun addItem(fragment: Fragment, title: String) {
        fragmentList.add(fragment)
        pageTitleList.add(title)
    }

    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return pageTitleList[position]
    }

    override fun getCount(): Int {
        return fragmentList.count()
    }
}