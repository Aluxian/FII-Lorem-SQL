package com.aluxian.loremsql

import android.app.AlertDialog
import android.os.Bundle
import android.support.v7.app.ActionBarActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import butterknife.bindView
import com.aluxian.loremsql.models.ToDoItem
import com.getbase.floatingactionbutton.AddFloatingActionButton
import io.realm.Realm
import io.realm.RealmObject
import io.realm.exceptions.RealmException
import java.util.ArrayList

public class MainActivity : ActionBarActivity() {

    val recyclerView: RecyclerView by bindView(R.id.list)
    val addButton: AddFloatingActionButton by bindView(R.id.add_btn)

    val items = ArrayList<ToDoItem>()
    val adapter = ToDoItemAdapter(items)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Load items from the db
        val realm = Realm.getInstance(this)
        items.addAll(realm.where(javaClass<ToDoItem>()).findAll().toArrayList().reverse())
        adapter.notifyItemRangeInserted(0, items.size())

        // Add Note button callback
        addButton.setOnClickListener {
            val dialogView = getLayoutInflater().inflate(R.layout.dialog_add, null)

            AlertDialog.Builder(this, R.style.AppTheme_Dialog)
                    .setView(dialogView)
                    .setPositiveButton(R.string.btn_add, { dialog, which ->
                        run {
                            fun textOf(id: Int) = (dialogView.findViewById(id) as EditText).getText().toString()

                            val title = textOf(R.id.title).trim()
                            val description = textOf(R.id.description).trim()

                            try {
                                // Build object, save into db
                                val toDo = realm.create<ToDoItem> {
                                    it.setTitle(title)
                                    it.setDescription(description)
                                }

                                // Add the item to the adapter
                                items.add(0, toDo)
                                adapter.notifyItemInserted(0)

                                // Scroll and recalculate margins
                                recyclerView.smoothScrollToPosition(0)
                                recyclerView.invalidateItemDecorations()
                            } catch (e: RealmException) {
                                Log.e(javaClass<MainActivity>().getSimpleName(), "Realm Error", e)
                                Toast.makeText(this@MainActivity, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show()
                            }
                        }
                    })
                    .setNegativeButton(R.string.btn_cancel, { dialog, which -> })
                    .show()
        }

        // Set up the list
        recyclerView.setHasFixedSize(true)
        recyclerView.setLayoutManager(LinearLayoutManager(this))
        recyclerView.addItemDecoration(ItemSpacingDecoration())
        recyclerView.setItemAnimator(DefaultItemAnimator())
        recyclerView.setAdapter(adapter)
    }

    inline fun <reified T : RealmObject> Realm.create(update: (T) -> Unit): T {
        this.beginTransaction()

        val obj = this.createObject(javaClass<T>())
        update(obj)

        this.commitTransaction()
        return obj
    }

}
