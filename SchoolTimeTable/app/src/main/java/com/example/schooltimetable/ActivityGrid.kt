package com.example.schooltimetable

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar

class ActivityGrid : AppCompatActivity() {
    private lateinit var recyclerContainer: RecyclerView;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_grid)
        recyclerContainer = this.findViewById<RecyclerView>(R.id.recycler)
        recyclerContainer.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        recyclerContainer.adapter = HorizontalAdapter(this, intent.getStringArrayExtra("day")!!)
        findViewById<MaterialToolbar>(R.id.topAppBar).setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.list -> {
                    finish()
                    true
                }
                else -> false
            }
        }
    }

    override fun onResume() {
        super.onResume()
        recyclerContainer.adapter?.notifyDataSetChanged()
    }
}