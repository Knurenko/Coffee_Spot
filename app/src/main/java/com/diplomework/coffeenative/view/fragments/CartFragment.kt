package com.diplomework.coffeenative.view.fragments

import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.diplomework.coffeenative.R
import com.diplomework.coffeenative.data.model.Product
import com.diplomework.coffeenative.data.repo.DataProvider
import com.diplomework.coffeenative.view.recycler.ProductsOfOrderAdapter
import com.diplomework.coffeenative.view.recycler.SwipeController

class CartFragment : Fragment() {
    companion object {
        fun newInstance() = CartFragment()
    }

    private val _items: MutableList<Product> = DataProvider.itemsInCart
    private lateinit var _adapter: ProductsOfOrderAdapter

    private lateinit var totalPrice: TextView
    private lateinit var recycler: RecyclerView
    private lateinit var menuItem: MenuItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        retainInstance = true
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_cart, container, false)

        activity?.title = "Корзина"

        // init UI variables
        recycler = view.findViewById(R.id.cart_recycler)
        totalPrice = view.findViewById(R.id.cart_total_cost)
        val proceedButton: Button = view.findViewById(R.id.proceed_order_btn)
        proceedButton.setOnClickListener { finishOrder() }

        setupRecycler()
        refreshUI()

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_cart, menu)
        menuItem = menu.getItem(0)
        menuItem.isVisible = false
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.undo) undo()
        return super.onOptionsItemSelected(item)
    }

    /**
     * re-calc total cost
     */
    private fun refreshTotalCost() {
        var total = 0
        _items.forEach { total += it.getStockPrice() }
        totalPrice.text = total.toString()
    }

    /**
     * refresh all UI (adapter + total cost)
     */
    private fun refreshUI() {
        refreshTotalCost()
        _adapter.notifyDataSetChanged()
    }

    private fun removeItem(position: Int) {
        val removedItem = _items.removeAt(position)
        _adapter.notifyItemRemoved(position)
        refreshTotalCost()

        val removedItemInfo = RemovedItemInfo(removedItem, position)
        appendTimeLine(removedItemInfo)
        menuItem.isVisible = true
    }

    private fun changeCount(item: Product, delta: Int) {
        val position = _items.indexOf(item)
        _items[position].count += delta
        _adapter.notifyItemChanged(position)
        refreshTotalCost()
    }

    /**
     * sends current items to complete the order and exit back to main screens
     */
    private fun finishOrder() {
        if (_items.size != 0) {
            DataProvider.completeOrder(_items.toList())
            Toast.makeText(requireContext(), "Заказ успешно оформлен!", Toast.LENGTH_SHORT).show()
            activity?.finish()
        } else {
            Toast.makeText(
                requireContext(),
                "Корзина пуста, добавьте элементов для оформления заказа",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    /**
     * setup adapter, layoutManager. Also attaches ItemTouchHelper (for horizontal swipes)
     */
    private fun setupRecycler() {

        val swipeCallback = object : SwipeController.OnSwipeListener {
            override fun onSwiped(position: Int) = removeItem(position)
        }

        val itemCountChangerCallback = object : ProductsOfOrderAdapter.IStockCountChanger {
            override fun increase(item: Product) = changeCount(item, 1)
            override fun decrease(item: Product) = changeCount(item, -1)
            override fun showDeletingHint() {
                Toast.makeText(
                    requireContext(),
                    "Для удаления сдвиньте элемент в сторону",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        _adapter = ProductsOfOrderAdapter(requireContext(), _items, itemCountChangerCallback)

        recycler.adapter = _adapter
        recycler.layoutManager = LinearLayoutManager(requireContext())

        val swipeController = SwipeController(swipeCallback)
        val touchHelper = ItemTouchHelper(swipeController)
        touchHelper.attachToRecyclerView(recycler)
    }

    /**
     * class for holding up already deleted item
     */
    private data class RemovedItemInfo(val item: Product, val position: Int)

    private val timeLineMap: MutableMap<Int, RemovedItemInfo?> = HashMap()
    private var timeLineIndex = 0

    /**
     * checks if there is some "future" action-spots and erases it to recreate
     */
    private fun appendTimeLine(actionDone: RemovedItemInfo) {
        timeLineIndex++

        // find max index
        var max = 0
        timeLineMap.keys.forEach { if (it > max) max = it }

        // cut timeLine branch from this spot
        for (i in 0..max) {
            if (i >= timeLineIndex) timeLineMap.remove(i)
        }

        // re-init current spot
        timeLineMap[timeLineIndex] = actionDone
    }

    /**
     * cancel last action (insert back removed item)
     */
    private fun undo() {
        val itemInfo: RemovedItemInfo? = timeLineMap[timeLineIndex]

        if (itemInfo != null) {
            _items.add(itemInfo.position, itemInfo.item)
            _adapter.notifyItemInserted(itemInfo.position)
            timeLineIndex--
            refreshTotalCost()
        }

        if (timeLineIndex == 0) menuItem.isVisible = false
    }
}