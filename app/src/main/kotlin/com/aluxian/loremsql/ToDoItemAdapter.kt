package com.aluxian.loremsql

import android.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.bindView
import com.aluxian.loremsql.models.ToDoItem
import java.util.ArrayList

public class ToDoItemAdapter(val items: ArrayList<ToDoItem>) : RecyclerView.Adapter<ToDoItemAdapter.Companion.ToDoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoViewHolder =
            ToDoViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_item, parent, false))

    override fun onBindViewHolder(viewHolder: ToDoViewHolder, position: Int) {
        val item = items[position]

        viewHolder.title.setText(item.getTitle())
        viewHolder.description.setText(item.getDescription())

        viewHolder.itemView.setOnClickListener {
            AlertDialog.Builder(viewHolder.itemView.getContext())
                    .setItems(array("Done", "Remove"), { dialog, which ->
                        run {
                            items.remove(position)
                            notifyItemRemoved(position)
                        }
                    })
                    .show()
        }
    }

    override fun getItemCount(): Int = items.size()

    companion object {
        class ToDoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val title: TextView by bindView(R.id.title)
            val description: TextView by bindView(R.id.description)
        }

        public trait RemoveListener {
            public fun removeItemAt(position: Int)
        }
    }

}
