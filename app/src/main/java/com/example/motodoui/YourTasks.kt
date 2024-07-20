package com.example.motodoui


import android.annotation.TargetApi
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.motodoui.databinding.ActivityYourTasksBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.firestoreSettings
import com.google.firebase.ktx.Firebase


class YourTasks : AppCompatActivity() {
    private lateinit var binding: ActivityYourTasksBinding
    private lateinit var todoAdapter: TodoAdapter
    private lateinit var todoList: MutableList<Todo>
    private lateinit var firestore: FirebaseFirestore
    private lateinit var listenerRegistration: ListenerRegistration

    @TargetApi(33)
    private val startTodoActivity =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val todo = result.data?.getParcelableExtra<Todo>("todo")
                val position = result.data?.getIntExtra("position", -1)
                if (todo != null) {
                    if (position == -1) {
                        // Add new Todo to Firestore
                        addTodoToFirestore(todo)
                    } else if (position != null) {
                        // Update existing Todo in Firestore
                        updateTodoInFirestore(todo)
                    }
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

        // Initialize Firestore with offline persistence
        firestore = Firebase.firestore
        val settings = firestoreSettings {
            isPersistenceEnabled = true
        }
        firestore.firestoreSettings = settings

        // Read Todos from Firestore
        listenerRegistration = firestore.collection("todos")
            .addSnapshotListener { snapshots, e ->
                if (e != null) {
                    Log.w("YourTasks", "Listen failed.", e)
                    showSnackbar("Failed to load tasks. Please check your connection.")
                    return@addSnapshotListener
                }

                if (snapshots != null) {
                    todoList.clear()
                    for (doc in snapshots) {
                        val todo = doc.toObject(Todo::class.java).apply { id = doc.id }
                        todoList.add(todo)
                    }
                    sortTodoList()  // Sort the list whenever data changes
                    todoAdapter.notifyDataSetChanged()
                }
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        listenerRegistration.remove()
    }

    private fun editTodo(todo: Todo, position: Int) {
        val intent = Intent(this, TodoActivity::class.java).apply {
            putExtra("todo", todo)
            putExtra("position", position)
        }
        startTodoActivity.launch(intent)
    }

    private fun deleteTodo(position: Int) {
        val todo = todoList[position]
        firestore.collection("todos").document(todo.id).delete()
            .addOnSuccessListener {
                todoList.removeAt(position)
                sortTodoList()  // Sort the list whenever an item is removed
                todoAdapter.notifyItemRemoved(position)
            }
            .addOnFailureListener { e ->
                Log.w("YourTasks", "Error deleting document", e)
                showSnackbar("Error deleting task. Please try again.")
            }
    }

    private fun addTodoToFirestore(todo: Todo) {
        val newTodoRef = firestore.collection("todos").document()
        todo.id = newTodoRef.id
        newTodoRef.set(todo)
            .addOnSuccessListener {
                todoList.add(todo)
                sortTodoList()  // Sort the list whenever data changes
                todoAdapter.notifyDataSetChanged()
            }
            .addOnFailureListener { e ->
                Log.w("YourTasks", "Error adding document", e)
                showSnackbar("Error adding task. Please try again.")
            }
    }

    private fun updateTodoInFirestore(todo: Todo) {
        firestore.collection("todos").document(todo.id).set(todo)
            .addOnSuccessListener {
                val index = todoList.indexOfFirst { it.id == todo.id }
                if (index != -1) {
                    todoList[index] = todo
                    sortTodoList()  // Sort the list whenever data changes
                    todoAdapter.notifyItemChanged(index)
                }
            }
            .addOnFailureListener { e ->
                Log.w("YourTasks", "Error updating document", e)
                showSnackbar("Error updating task. Please try again.")
            }
    }

    private fun sortTodoList() {
        todoList.sortByDescending { it.priority }
    }

    private fun showSnackbar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
    }
}