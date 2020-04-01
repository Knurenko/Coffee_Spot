package com.diplomework.coffeenative.activity

import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import com.diplomework.coffeenative.data.model.Product
import com.diplomework.coffeenative.view.fragments.CartFragment
import com.diplomework.coffeenative.view.fragments.MainFragment
import com.diplomework.coffeenative.view.fragments.ProductItemFragment

class SecondActivity : SingleFragmentActivity() {

    companion object {
        private fun newIntent(context: Context) =
            Intent(context, SecondActivity::class.java)

        fun showProductIntent(context: Context, product: Product) = newIntent(context).apply {
            putExtra(INTENT_TYPE, TYPE_PRODUCT)
            putExtra(PRODUCT_ID, product.id)
        }

        fun showCartIntent(context: Context) = newIntent(context).apply {
            putExtra(INTENT_TYPE, TYPE_CART)
        }

        const val PRODUCT_ID = "PRODUCT ID"
        private const val INTENT_TYPE = "CALL TYPE"
        private const val TYPE_CART = 0
        private const val TYPE_PRODUCT = 1
    }

    override fun createFragment(): Fragment {

        // here we get an info from intent about "which fragment should be displayed"
        return when (intent.getIntExtra(INTENT_TYPE, 0)) {
            TYPE_CART -> CartFragment.newInstance()
            TYPE_PRODUCT -> ProductItemFragment.newInstance()
            else -> MainFragment.newInstance()
        }
    }
}