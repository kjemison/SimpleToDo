package com.example.simpletodo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.apache.commons.io.FileUtils
import java.io.File
import java.io.IOException
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {

    var listOfTasks = mutableListOf<String>()
    lateinit var adapter: TaskItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val onLongClickListener = object : TaskItemAdapter.OnLongClickListener {

            // Long click will remove item from list
            override fun onItemLongClicked(position: Int) {
                listOfTasks.removeAt(position)
                adapter.notifyDataSetChanged()
                saveItems()
            }
        }

        // 1. Detect when user clicks add button
//        findViewById<Button>(R.id.button).setOnClickListener {
//            // Code executed when button is clicked
//            Log.i("Caren", "User clicked on button")

        loadItems()

        // Lookup the recyclerview in activity layout
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        // Create adapter passing in the sample user data
        adapter = TaskItemAdapter(listOfTasks, onLongClickListener)

        // Attach the adapter to the recyclerview to populate items
        recyclerView.adapter = adapter

        // Set layout manager to position the items
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Reference to text field
        val inputTextField = findViewById<EditText>(R.id.addTaskField)

        // Set up button and input field so user can enter a task
        // - Get reference to button and set on click listener to it
        findViewById<Button>(R.id.button).setOnClickListener {

            // 1. Grab text user inputted into text field
            val userInputtedTask = inputTextField.text.toString()

            // 2. Add the string to our list of tasks
            listOfTasks.add(userInputtedTask)

            // Notify adapter that data was updated
            adapter.notifyItemInserted(listOfTasks.size -1)

            // 3. Reset text field
            inputTextField.setText("")

            saveItems()
        }
    }

    // Save data that user has inputted by writing and reading from file
    fun getDataFile() : File {
        // Every line represents a specific task in list of tasks
        return File(filesDir, "data.txt")
    }

    fun loadItems() {
        try {
            listOfTasks = FileUtils.readLines(getDataFile(), Charset.defaultCharset())
        } catch (ioException: IOException) {
            ioException.printStackTrace()
        }
    }

    fun saveItems() {
        try {
            FileUtils.writeLines(getDataFile(), listOfTasks)
        } catch (ioException: IOException) {
            ioException.printStackTrace()
        }

    }
}
