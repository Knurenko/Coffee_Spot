package com.diplomework.coffeenative.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.diplomework.coffeenative.R


class HistoryFragment : Fragment() {

//    private lateinit var _adapter: TransactionAdapter

    companion object {
        fun newInstance() = HistoryFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_history, container, false)

//        val recycler: RecyclerView = view.findViewById(R.id.transactions_recycler)
//
//        _adapter = TransactionAdapter(this.requireContext(), gay())
//        recycler.adapter = _adapter
//        recycler.layoutManager = LinearLayoutManager(this.requireContext())

        return view
    }

//    private fun gay(): MutableList<Transaction> {
//        val list: MutableList<Transaction> = ArrayList()
//
//        val date = Date()
//        val cat1 = Category("SHERIFF EBATB", Color.MAGENTA)
//        val cat2 = Category("So so far", Color.CYAN)
//        val cat3 = Category("3rd category", Color.GREEN)
//        list.add(Transaction(date, "pidoras, PIDORASINA !!!", 44.15f, cat1))
//        list.add(Transaction(date, "Долг от Дрюса", 1600.00f, cat2, TransactionType.PLUS))
//        list.add(Transaction(date, "рандом нахуй", 124.15f, cat3))
//        list.add(Transaction(date, "PISOS", 675.15f, cat3))
//        list.add(Transaction(date, "BRO SUCK ME OFF", 351.00f, cat1))
//
//        return list
//    }
}
