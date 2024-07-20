package com.example.motodoui


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.TimePicker
import androidx.appcompat.app.AppCompatActivity


import com.example.motodoui.Utils.Utils.generateUniqueId

class TodoActivity : AppCompatActivity() {

    private lateinit var todoNameEditText: EditText
    private lateinit var todoMessageEditText: EditText
    private lateinit var todoDateDatePicker: DatePicker
    private lateinit var todoTimePicker: TimePicker
    private lateinit var todoPriorityGroup: RadioGroup
    private var position: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo)

        todoNameEditText = findViewById(R.id.todo_name)
        todoMessageEditText = findViewById(R.id.todo_message)
        todoDateDatePicker = findViewById(R.id.todo_date)
        todoTimePicker = findViewById(R.id.todo_time)
        todoPriorityGroup = findViewById(R.id.todo_priority_group)
        todoTimePicker.setIs24HourView(true)

        val todo = intent.getParcelableExtra<Todo>("todo")
        if (todo != null) {
            todoNameEditText.setText(todo.name)
            todoMessageEditText.setText(todo.message)
            val dateParts = todo.date.split("/")
            todoDateDatePicker.updateDate(dateParts[2].toInt(), dateParts[1].toInt() - 1, dateParts[0].toInt())
            val timeParts = todo.time.split(":")
            todoTimePicker.hour = timeParts[0].toInt()
            todoTimePicker.minute = timeParts[1].toInt()
            when (todo.priority) {
                1 -> todoPriorityGroup.check(R.id.priority_low)
                2 -> todoPriorityGroup.check(R.id.priority_medium)
                3 -> todoPriorityGroup.check(R.id.priority_high)
                4 -> todoPriorityGroup.check(R.id.priority_very_high)
                5 -> todoPriorityGroup.check(R.id.priority_urgent)
            }
            position = intent.getIntExtra("position", -1)
        }

        findViewById<View>(R.id.todo_button).setOnClickListener {
            val todoName = todoNameEditText.text.toString()
            val todoMessage = todoMessageEditText.text.toString()
            val day = todoDateDatePicker.dayOfMonth
            val month = todoDateDatePicker.month + 1
            val year = todoDateDatePicker.year
            val todoDate = "$day/$month/$year"

            val hour = todoTimePicker.hour
            val minute = todoTimePicker.minute
            val todoTime = String.format("%02d:%02d", hour, minute)

            val selectedPriorityButtonId = todoPriorityGroup.checkedRadioButtonId
            val priority = findViewById<View>(selectedPriorityButtonId).tag.toString().toInt()

            val newTodo = Todo(
                id = if (todo == null) generateUniqueId() else todo.id, // Use UUID if creating new Todo
                name = todoName,
                message = todoMessage,
                date = todoDate,
                time = todoTime,
                priority = priority
            )
            val resultIntent = Intent().apply {
                putExtra("todo", newTodo)
                putExtra("position", position)
            }
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
    }
}
