package com.example.motodoui

import Todo
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TodoAdapter(private val todoList: MutableList<Todo>) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val todoNameTextView: TextView = itemView.findViewById(R.id.todo_name_textview)
        val todoMessageTextView: TextView = itemView.findViewById(R.id.todo_message_textview)
        val todoDateTextView: TextView = itemView.findViewById(R.id.todo_date_textview)
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
    }

    override fun getItemCount(): Int = todoList.size
}
