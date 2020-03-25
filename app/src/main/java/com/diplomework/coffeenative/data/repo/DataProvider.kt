package com.diplomework.coffeenative.data.repo

import android.util.Log
import com.diplomework.coffeenative.data.model.Product
import com.diplomework.coffeenative.data.model.ProductType

/**
 * singleton to hold data of an app
 */
object DataProvider {

    private const val TAG = "check"

    // variables for data saving and restoring
    data class DataBundle(val products: List<Product>)

    private const val PREFS_TAG = "DataBundle_tag"

    private var productList: MutableList<Product> = ArrayList()

    fun setProducts(list: List<Product>) {
        productList.clear()
        productList.addAll(list)
    }

    fun getProducts(): MutableList<Product> {
//        Log.d(TAG, "get products func, list:")
//
//        for (item: Product in productList) {
//            Log.d(TAG, "item[${item.id}] : $item")
//        }

        return productList
    }

    /**
     * method to save product list values into shared preferences
     */
    fun saveDataToSharedPrefs() {
        val bundle = DataBundle(productList)
        PreferenceHelper.save(bundle, PREFS_TAG)
    }

    /**
     * method to load product list values from shared preferences
     */
    fun loadDataFromSharedPrefs() {
        val bundle = PreferenceHelper.load<DataBundle>(PREFS_TAG)

        if (bundle != null && bundle.products.isNotEmpty()) {
            productList.clear()
            productList.addAll(bundle.products)
        } else {
            // TODO: Delete it later
            fillProductListByDummyContent()
        }
    }

    private fun fillProductListByDummyContent() {

        Log.i("check", "FILL LIST BY DUMMY CONTENT!")

        productList.add(
            Product(
                1, "Американо", 20,
                "https://upload.wikimedia.org/wikipedia/commons/thumb/4/45/A_small_cup_of_coffee.JPG/1200px-A_small_cup_of_coffee.JPG",
                "desc", ProductType.DRINK
            )
        )
        productList.add(
            Product(
                2, "Капучино", 15,
                "https://upload.wikimedia.org/wikipedia/commons/1/16/Classic_Cappuccino.jpg",
                "desc", ProductType.DRINK
            )
        )
        productList.add(
            Product(
                3, "Латте", 10,
                "https://gogol-mogol.su/uploads/posts/2017-02/1485941008_kofe.jpg",
                "desc", ProductType.DRINK
            )
        )
        productList.add(
            Product(
                4, "Эспрессо", 10,
                "https://cs12.pikabu.ru/post_img/big/2019/05/15/7/1557915500180338047.jpg",
                "desc", ProductType.DRINK
            )
        )
        productList.add(
            Product(
                5, "Лонг Блэк", 20,
                "https://www.coffeebeanshop.com.au/uploads/4/1/4/4/4144860/7703776_orig.jpg",
                "desc", ProductType.DRINK
            )
        )
        productList.add(
            Product(
                6, "Айриш Кофе", 25,
                "https://coffeefan.info/wp-content/uploads/2018/09/kofe-latte-makiato-v-domashnikh-usloviyakh.jpg",
                "desc", ProductType.DRINK
            )
        )
        productList.add(
            Product(
                7, "Мокка", 15,
                "http://kivahan.ru/wp-content/uploads/2015/05/coffee-325-e1431189401330.jpg",
                "desc", ProductType.DRINK
            )
        )
        productList.add(
            Product(
                8, "nikita", 999,
                "https://sun9-40.userapi.com/c857720/v857720670/1326e5/Q7i7ucO4C58.jpg",
                "desc", ProductType.DRINK
            )
        )
        productList.add(
            Product(
                9, "Сендвич с курицей", 25,
                "http://v.img.com.ua/nxs140/b/600x500/0/c0/0623c1332e823f2445a69c53574bcc00.jpg",
                "desc", ProductType.FOOD
            )
        )
        productList.add(
            Product(
                10, "Сендвич с ветчиной", 20,
                "http://v.img.com.ua/nxs140/b/600x500/0/c0/0623c1332e823f2445a69c53574bcc00.jpg",
                "desc", ProductType.FOOD
            )
        )
        productList.add(
            Product(
                11, "Гамбургер", 30,
                "https://lh6.googleusercontent.com/proxy/Hoa9mDyUK0b-JG9GxvtTgrMJjKTX0CIo6dKANFA-YqIqxHMfxw5xDLBJcUGuvFzu6x7kZ9NyBCEpzPa7x8xr--0b6bL8VecjoM00jFUsC2Ijak9bRSZ0V7d0",
                "desc", ProductType.FOOD
            )
        )
        productList.add(
            Product(
                12, "Хоп-Дог", 20,
                "https://www.menslife.com/upload/iblock/a2a/4_retsepta_khot_dogov_v_domashnikh_usloviyakh.jpg",
                "desc", ProductType.FOOD
            )
        )
    }
}
