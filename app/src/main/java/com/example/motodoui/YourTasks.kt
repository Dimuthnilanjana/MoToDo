package com.example.motodoui


import android.annotation.TargetApi
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.motodoui.databinding.ActivityYourTasksBinding

class YourTasks : AppCompatActivity() {
    private lateinit var binding: ActivityYourTasksBinding
    private lateinit var todoAdapter: TodoAdapter
    private lateinit var todoList: MutableList<Todo>

    @TargetApi(33)
    private val startTodoActivity =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val todo = result.data?.getParcelableExtra<Todo>("todo")
                val position = result.data?.getIntExtra("position", -1)
                if (todo != null) {
                    if (position == -1) {
                        todoList.add(todo)
                    } else if (position != null) {
                        todoList[position] = todo
                    }
                    todoAdapter.notifyDataSetChanged()
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityYourTasksBinding.inflate(layoutInflater)
        setContentView(binding.root)

        todoList = mutableListOf()
        todoAdapter = TodoAdapter(this, todoList, ::editTodo, ::deleteTodo)
        binding.todoList.layoutManager = LinearLayoutManager(this)
        binding.todoList.adapter = todoAdapter

        binding.fabAddTodo.setOnClickListener {
            val intent = Intent(this, TodoActivity::class.java)
            startTodoActivity.launch(intent)
        }
    }

    private fun editTodo(todo: Todo, position: Int) {
        val intent = Intent(this, TodoActivity::class.java).apply {
            putExtra("todo", todo)
            putExtra("position", position)
        }
        startTodoActivity.launch(intent)
    }

    private fun deleteTodo(position: Int) {
        todoList.removeAt(position)
        todoAdapter.notifyItemRemoved(position)
    }
}
