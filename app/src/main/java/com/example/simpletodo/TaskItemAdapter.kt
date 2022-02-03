package com.example.simpletodo

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * Bridge that tells recyclerview how to display the data given
 */
class TaskItemAdapter(val listOfItems: List<String>,
                      val longClickListener: OnLongClickListener) : RecyclerView.Adapter<TaskItemAdapter.ViewHolder>() {

    interface OnLongClickListener {
        fun onItemLongClicked(position: Int)
    }

    // Usually involves inflating a layout from XML and returning the holder
    // lays out items in the view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val context = parent.context
        val inflater = LayoutInflater.from(context)

        // Inflate the custom layout
        val contactView = inflater.inflate(android.R.layout.simple_list_item_1, parent, false)

        // Return a new holder instance
        return ViewHolder(contactView)
    }

    // Involves populating data into the item through holder
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        // Get data model based on position
        val item: String = listOfItems.get(position)
        holder.textView.text = item
    }

    // Returns the total count of items in the list
    override fun getItemCount(): Int {
        return listOfItems.size
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    // * Grabs references to views we need to populate the data*
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        // Your holder should contain and initialize a member variable
        // for any view that will be set as you render a row

        // Store references to elements in our layout view
        val textView: TextView = itemView.findViewById(android.R.id.text1)
        init {
            itemView.setOnLongClickListener {
                longClickListener.onItemLongClicked(adapterPosition)
                true
            }
        }

//        val nameTextView = itemView.findViewById<TextView>(R.id.contact_name)
//        val messageButton = itemView.findViewById<Button>(R.id.message_button)
    }
}