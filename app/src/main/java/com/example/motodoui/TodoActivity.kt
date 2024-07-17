package com.example.motodoui

import Todo
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class TodoActivity : AppCompatActivity() {

    private lateinit var todoNameEditText: EditText
    private lateinit var todoMessageEditText: EditText
    private lateinit var todoDateDatePicker: DatePicker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo)

        todoNameEditText = findViewById(R.id.todo_name)
        todoMessageEditText = findViewById(R.id.todo_message)
        todoDateDatePicker = findViewById(R.id.todo_date)

        findViewById<View>(R.id.todo_button).setOnClickListener {
            val todoName = todoNameEditText.text.toString()
            val todoMessage = todoMessageEditText.text.toString()
            val day = todoDateDatePicker.dayOfMonth
            val month = todoDateDatePicker.month + 1
            val year = todoDateDatePicker.year
            val todoDate = "$day/$month/$year"

            val todo = Todo(0, todoName, todoMessage, todoDate)
            val intent = Intent().apply {
                putExtra("todo", todo)
            }
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }
}
