package com.example.schooltimetable

import android.content.Context
import android.database.Cursor
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HorizontalAdapter(private val context: Context, private val dataSet: Array<String>) :
    RecyclerView.Adapter<HorizontalAdapter.ViewHolder>() {
    private val listSet = arrayOf("Mon.", "Tue.", "Wed.", "Thur.", "Fri.", "Sat.", "Sun.")

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.mtrl_calendar_days_of_week)
        val recyclerView = itemView.findViewById<RecyclerView>(R.id.innerRecycler)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_box, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemSet: MutableList<MutableList<String>> = mutableListOf<MutableList<String>>()
        repeat(5) {
            itemSet.add(mutableListOf<String>("null"))
        }
        holder.textView.text = dataSet[position]
        val dbHelper =
            TimeDatabaseHelper(context, context.filesDir.toString() + "/classDb.db", null, 1)
        val readableDb = dbHelper.readableDatabase
        var cursor: Cursor
        repeat(5) {
            cursor = readableDb.query(
                "TimeTable",
                null,
                "dayList like ? and timeFrom >= ? and timeFrom <= ?",
                arrayOf(
                    "%" + listSet[position] + "%",
                    (1 + it * 2).toString(),
                    (2 + it * 2).toString()
                ),
                null,
                null,
                null
            )
            if (cursor.count == 1) {
                var tempList = mutableListOf<String>()
                cursor.moveToNext()
                tempList.apply {
                    with(cursor) {
                        add(getString(1))
                        add(getString(0))
                        add(getString(2))
                        add(getString(7))
                        add(getString(8))
                    }
                }
                itemSet.set(it, tempList)
            }
        }
        holder.recyclerView.layoutManager = LinearLayoutManager(null, RecyclerView.VERTICAL, false)
        holder.recyclerView.adapter = AdapterItem(itemSet)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }
}