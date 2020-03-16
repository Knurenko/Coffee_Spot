package com.diplomework.coffeenative.view.recycler

//
//import android.view.View
//import androidx.recyclerview.widget.RecyclerView
//import com.example.tasker.data.model.Task
//import com.example.tasker.data.viewmodel.TaskVM
//import com.example.tasker.databinding.ListItemBinding
//
//class TaskViewHolder
//constructor(private val mBinding: ListItemBinding, private val callback: Callback) : RecyclerView.ViewHolder(mBinding.root), View.OnClickListener {
//
//    interface Callback {
//        fun performActivityStart(addNew: Boolean, itemToUpdate: Task? = null)
//    }
//
//    init {
//        mBinding.task = TaskVM.newInstance()
//        mBinding.root.setOnClickListener(this)
//    }
//
//    fun bind(bindedTask: Task) {
//        mBinding.task?.taskModel = bindedTask
//        //exec pending bindings - for faster drawing (maybe like .invalidate() )
//        mBinding.executePendingBindings()
//    }
//
//    override fun onClick(view: View?) {
//        mBinding.task?.onTaskClicked(callback)
//    }
//}