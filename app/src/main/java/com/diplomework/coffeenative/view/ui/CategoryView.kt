package com.diplomework.coffeenative.view.ui

// example of custoom UI class (to be used in XML as <com.diplomework.cofffenative.CategoryView .../>

//import android.content.Context
//import android.graphics.Color
//import android.util.AttributeSet
//import android.widget.TextView
//import androidx.cardview.widget.CardView
//import com.diplomework.coffeenative.R
//
//
//class CategoryView(context: Context, attrs: AttributeSet) : CardView(context, attrs) {
//    private val cardView: CardView
//    private val textView: TextView
//    private var initialized: Boolean = false
//
//    init {
//        inflate(context, R.layout.category_item_view, this)
//        cardView = findViewById(R.id.category_back)
//        textView = findViewById(R.id.category_title)
//
//        val attributes = context.theme.obtainStyledAttributes(attrs, R.styleable.CategoryView, 0, 0)
//        setColor(attributes.getColor(R.styleable.CategoryView_category_color, Color.TRANSPARENT))
//        setTitle(attributes.getString(R.styleable.CategoryView_category_title) ?: "")
//
//        attributes.recycle()
//        initialized = true
//    }
//
//    fun setTitle(value: String) {
//        textView.text = value
//        if (initialized) update()
//    }
//
//    fun setColor(value: Int) {
//        cardView.setCardBackgroundColor(value)
//        if (initialized) update()
//    }
//
//    private fun update() {
//        invalidate()
//        requestLayout()
//    }
//}