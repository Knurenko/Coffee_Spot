package com.diplomework.coffeenative.activity

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.diplomework.coffeenative.R

abstract class SingleFragmentActivity : AppCompatActivity() {

    /**
     * if root view of activity will be changed: override method below and insert fragment_container in xml of view
     */
    @LayoutRes
    private fun getLayoutResId(): Int {
        return R.layout.activity_fragment
    }

    protected abstract fun createFragment(): Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutResId())
        val fm = supportFragmentManager
        var fragment = fm.findFragmentById(R.id.fragment_container)
        if (fragment == null) {
            fragment = createFragment()
            fm.beginTransaction()
                .add(R.id.fragment_container, fragment)
                .commit()
        }
    }
}