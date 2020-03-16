package com.diplomework.coffeenative.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.diplomework.coffeenative.R


class StatisticFragment : Fragment() {

    companion object {
        fun newInstance() = StatisticFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_statistic, container, false)

        return view
    }
}
