package com.example.motodoui

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView

class TodoAdapter(
    private val context: Context,
    private val todoList: MutableList<Todo>,
    private val editTodo: (Todo, Int) -> Unit,
    private val deleteTodo: (Int) -> Unit
) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val todoNameTextView: TextView = itemView.findViewById(R.id.todo_name_textview)
        val todoMessageTextView: TextView = itemView.findViewById(R.id.todo_message_textview)
        val todoDateTextView: TextView = itemView.findViewById(R.id.todo_date_textview)
        val todoTimeTextView: TextView = itemView.findViewById(R.id.todo_time_textview)
        val btnEdit: Button = itemView.findViewById(R.id.btn_edit)
        val btnDelete: Button = itemView.findViewById(R.id.btn_delete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.todo_item, parent, false)
        return TodoViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val todo = todoList[position]
        holder.todoNameTextView.text = todo.name
        holder.todoMessageTextView.text = todo.message
        holder.todoDateTextView.text = todo.date
        holder.todoTimeTextView.text = todo.time

        // Set gradient border based on priority
        val gradientDrawable = GradientDrawable()
        gradientDrawable.setStroke(4, getColorForPriority(todo.priority))
        holder.itemView.background = gradientDrawable

        holder.btnEdit.setOnClickListener {
            editTodo.invoke(todo, position)
        }

        holder.btnDelete.setOnClickListener {
            AlertDialog.Builder(context)
                .setTitle("Delete Task")
                .setMessage("Are you sure you want to delete this task?")
                .setPositiveButton("Yes") { _, _ ->
                    deleteTodo.invoke(position)
                }
                .setNegativeButton("No", null)
                .show()
        }
    }

    override fun getItemCount(): Int = todoList.size

    // Function to get color based on priority
    private fun getColorForPriority(priority: Int): Int {
        return when (priority) {
            1 -> context.getColor(R.color.priority_low)
            2 -> context.getColor(R.color.priority_medium)
            3 -> context.getColor(R.color.priority_high)
            4 -> context.getColor(R.color.priority_very_high)
            5 -> context.getColor(R.color.priority_urgent)
            else -> context.getColor(R.color.priority_low)
        }
    }

    // Function to sort the todoList by priority
    fun sortTodoList() {
        todoList.sortByDescending { it.priority }
        notifyDataSetChanged()
    }
}
