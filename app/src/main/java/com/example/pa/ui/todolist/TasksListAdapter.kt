package com.example.pa.ui.todolist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.pa.R
import com.example.pa.data.local.Tasks

class TasksListAdapter(): ListAdapter<Tasks, TasksListAdapter.ItemViewHolder>(CHECK_TASKS) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemViewHolder {
        return ItemViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bindTaskDescription(currentItem.taskInput)
        holder.bindTaskDueDate(currentItem.endDate.toString())
    }

    override fun getItemCount(): Int {
        return super.getItemCount()
    }

    class ItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        val descriptionItemView: TextView = itemView.findViewById(R.id.textViewTask)
        val dueDateItemView: TextView = itemView.findViewById(R.id.textViewDueDate)

        // Assign task's description to item's list
        fun bindTaskDescription (taskDescription: String?) {
            if (taskDescription != null) {
                descriptionItemView.text = taskDescription
            }
        }

        // Assign task's end date to item's list
        fun bindTaskDueDate (taskDueDate: String?) {
            if (taskDueDate != null) {
                dueDateItemView.text = taskDueDate
            }
        }

        companion object {
            fun create(parent: ViewGroup): ItemViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.text_tasks_list_item, parent, false)
                return ItemViewHolder(view)
            }
        }
    }

    companion object {
        private val CHECK_TASKS = object : DiffUtil.ItemCallback<Tasks>() {
            override fun areItemsTheSame(oldItem: Tasks, newItem: Tasks): Boolean {
                TODO("Not yet implemented")
            }

            override fun areContentsTheSame(oldItem: Tasks, newItem: Tasks): Boolean {
                TODO("Not yet implemented")
            }
        }
    }
}


