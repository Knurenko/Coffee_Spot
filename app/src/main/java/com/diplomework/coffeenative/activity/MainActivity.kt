package com.diplomework.coffeenative.activity

import androidx.fragment.app.Fragment
import com.diplomework.coffeenative.view.fragments.MainFragment

class MainActivity : SingleFragmentActivity() {
    override fun createFragment(): Fragment {
        return MainFragment.newInstance()
    }
}
