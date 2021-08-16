package com.example.schooltimetable

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView

class AdapterItem(private val dataSet: MutableList<MutableList<String>>) :
    RecyclerView.Adapter<AdapterItem.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val innerLayout = view.findViewById<ConstraintLayout>(R.id.inner_constraintLayout)
        val schedule = view.findViewById<TextView>(R.id.schedule)
        val className = view.findViewById<TextView>(R.id.name)
        val num = view.findViewById<TextView>(R.id.num)
        val teacherName = view.findViewById<TextView>(R.id.teachername)
        val classroom = view.findViewById<TextView>(R.id.classroom)
        val weeks = view.findViewById<TextView>(R.id.weeks)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val innerSet = dataSet[position]
        with(holder) {
            if (innerSet.size == 1) {
                innerLayout.visibility = View.GONE
                schedule.visibility = View.VISIBLE
            } else {
                className.text = innerSet[0]
                num.text = innerSet[1]
                teacherName.text = innerSet[2]
                classroom.text = innerSet[3]
                weeks.text = innerSet[4]
            }
        }
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }
}