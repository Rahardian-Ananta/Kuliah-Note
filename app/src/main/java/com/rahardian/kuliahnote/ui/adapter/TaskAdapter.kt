package com.rahardian.kuliahnote.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rahardian.kuliahnote.R
import com.rahardian.kuliahnote.data.db.entity.Task

class TaskAdapter(
    private val tasks: List<Task>,
    private val onToggle: (Task) -> Unit
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    class TaskViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val checkbox: CheckBox = view.findViewById(R.id.task_checkbox)
        val title: TextView = view.findViewById(R.id.task_title)
        val deadline: TextView = view.findViewById(R.id.task_deadline)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_task, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        holder.title.text = task.title
        holder.checkbox.isChecked = task.isCompleted
        holder.deadline.text = task.deadline ?: ""
        holder.checkbox.setOnClickListener { onToggle(task) }
    }

    override fun getItemCount() = tasks.size
}
