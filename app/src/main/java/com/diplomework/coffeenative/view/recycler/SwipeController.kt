package com.diplomework.coffeenative.view.recycler

import android.graphics.Canvas
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.*
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.abs

class SwipeController(private val callback: OnSwipeListener) : ItemTouchHelper.Callback() {

    interface OnSwipeListener {
        fun onSwiped(position: Int)
    }

    private var swipeBack: Boolean = false

    override fun convertToAbsoluteDirection(flags: Int, layoutDirection: Int): Int {
        var direction = 0

        if (swipeBack) {
            swipeBack = false
        } else {
            direction = super.convertToAbsoluteDirection(flags, layoutDirection)
        }

        return direction
    }

    override fun onChildDrawOver(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder?,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        if (actionState == ACTION_STATE_SWIPE) {
            val itemWidth = viewHolder?.itemView?.width ?: 0
            val swipedCondition = abs(dX) >= (itemWidth / 3)

            swipeBack = !swipedCondition
        }

        super.onChildDrawOver(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        return makeMovementFlags(
            0,
            LEFT or RIGHT
        )
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean = false

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        callback.onSwiped(viewHolder.adapterPosition)
    }
}