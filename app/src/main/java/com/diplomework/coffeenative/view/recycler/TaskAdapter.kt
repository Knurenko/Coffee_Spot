package com.diplomework.coffeenative.view.recycler

//
//import android.content.Context
//import android.view.LayoutInflater
//import android.view.ViewGroup
//import androidx.databinding.DataBindingUtil
//import androidx.recyclerview.widget.RecyclerView
//import com.example.tasker.R
//import com.example.tasker.data.model.Task
//import com.example.tasker.databinding.ListItemBinding
//
//class TaskAdapter(private val context: Context, private val tasks: MutableList<Task>, private val callback: TaskViewHolder.Callback) : RecyclerView.Adapter<TaskViewHolder>(){
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
//        val inflater: LayoutInflater = LayoutInflater.from(context)
//
//        val binding: ListItemBinding = DataBindingUtil.inflate(inflater, R.layout.list_item, parent, false)
//
//        return TaskViewHolder(binding, callback)
//    }
//
//    override fun getItemCount(): Int {
//        return tasks.size
//    }
//
//    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
//        holder.bind(tasks[position])
//    }
//
//}