package com.example.schooltimetable

import android.content.ContentValues
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    private val dayList =
        listOf<String>("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")
    private val idList =
        listOf<Int>(R.id.morn_0, R.id.morn_1, R.id.after_0, R.id.after_1, R.id.even)
    private val classNumList = listOf<Int>(
        R.id.morn_num_0,
        R.id.morn_num_1,
        R.id.after_num_0,
        R.id.after_num_1,
        R.id.even_num_0
    )
    private lateinit var viewPager: ViewPager2
    private lateinit var readableDb: SQLiteDatabase
    private lateinit var cursor: Cursor
    override fun onCreate(savedInstanceState: Bundle?) {
        val dbHelper = TimeDatabaseHelper(this, this.filesDir.toString() + "/classDb.db", null, 1)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewPager = findViewById(R.id.viewPager)
        val tabLayout = findViewById<TabLayout>(R.id.tap)
        readableDb = dbHelper.readableDatabase
        cursor = readableDb.rawQuery("select * from TimeTable", null)
        val pageAdapter = PageAdapter(this)
        viewPager.adapter = pageAdapter
        TabLayoutMediator(
            tabLayout,
            viewPager,
            TabLayoutMediator.TabConfigurationStrategy { tab, position -> tab.setText(dayList[position]) }).attach()
        findViewById<MaterialToolbar>(R.id.topAppBar).setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.grid -> {
                    val intent = Intent(this, ActivityGrid::class.java)
                    intent.putExtra("day", dayList.toTypedArray())
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewPager.adapter = PageAdapter(this)
    }

    fun popUpWindow(view: View) {
        val dbHelper = TimeDatabaseHelper(this, this.filesDir.toString() + "/classDb.db", null, 1)
        val profileWindows = PopupWindow(
            LayoutInflater.from(this).inflate(R.layout.popup_profile, null),
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            true
        )
        profileWindows.setBackgroundDrawable(ColorDrawable(Color.WHITE))
        profileWindows.setOnDismissListener {
            val temp: WindowManager.LayoutParams = this.window.attributes
            temp.alpha = 1f
            window.attributes = temp
        }
        val dayList =
            listOf<Int>(R.id.Mon, R.id.Tues, R.id.Wed, R.id.Thur, R.id.Fri, R.id.Sat, R.id.Sun)
        val dayCheckedList = mutableListOf<String>()
        for (index in dayList) {
            profileWindows.contentView.findViewById<CheckBox>(index)
                .setOnCheckedChangeListener { buttonView, isChecked ->
                    if (isChecked) {
                        dayCheckedList.add(buttonView.text.toString())
                    } else {
                        dayCheckedList.remove(buttonView.text.toString())
                    }
                }
        }
        val profile: WindowManager.LayoutParams = this.window.attributes
        profile.alpha = 0.8f
        this.window.attributes = profile
        profileWindows.contentView.findViewById<Button>(R.id.add).setOnClickListener {
            val listID = listOf<Int>(
                R.id.classEdit,
                R.id.codeEdit,
                R.id.room_num,
                R.id.teacherEdit,
                R.id.timeFrom,
                R.id.timeTo,
                R.id.from,
                R.id.to
            )
            val listInput = listOf<String>(
                "_id",
                "className",
                "classRoom",
                "teacherName",
                "timeFrom",
                "timeTo",
                "periodFrom",
                "periodTo"
            )
            val view = profileWindows.contentView
            val classEdit = view.findViewById<EditText>(R.id.classEdit)
            val codeEdit = view.findViewById<EditText>(R.id.codeEdit)
            val roomEdit = view.findViewById<EditText>(R.id.room_num)
            val teacherEdit = view.findViewById<EditText>(R.id.teacherEdit)
            val timeFrom = view.findViewById<EditText>(R.id.timeFrom)
            val timeTo = view.findViewById<EditText>(R.id.timeTo)
            val from = view.findViewById<EditText>(R.id.from)
            val to = view.findViewById<EditText>(R.id.to)
            if (roomEdit.text.toString().equals("") || classEdit.text.toString()
                    .equals("") || codeEdit.text.toString()
                    .equals("") || teacherEdit.text.toString().equals("") || from.text.toString()
                    .equals("") || to.text.toString().equals("") || timeFrom.text.toString()
                    .equals("") || timeTo.text.toString().equals("")
            ) {
                Toast.makeText(this, "Empty free!!!", Toast.LENGTH_SHORT).show()
            } else if (Integer.parseInt(timeTo.text.toString()) <= Integer.parseInt(timeFrom.text.toString()) || Integer.parseInt(
                    from.text.toString()
                ) >= Integer.parseInt(to.text.toString())
            ) {
                Toast.makeText(this, "Wrong Input!!!", Toast.LENGTH_SHORT).show()
            } else {
                val writeDb = dbHelper.writableDatabase
                val values = ContentValues().apply {
                    put("_id", codeEdit.text.toString())
                    put("className", classEdit.text.toString())
                    put("teacherName", teacherEdit.text.toString())
                    put("timeFrom", Integer.parseInt(timeFrom.text.toString()))
                    put("timeTo", Integer.parseInt(timeTo.text.toString()))
                    put("periodFrom", Integer.parseInt(from.text.toString()))
                    put("periodTo", Integer.parseInt(to.text.toString()))
                    put("classRoom", roomEdit.text.toString())
                    put("dayList", dayCheckedList.toString())
                }
                for (index in listID) {
                    view.findViewById<EditText>(index).setText("")
                }
                for (index in dayList) {
                    view.findViewById<CheckBox>(index).isChecked = false
                }
                writeDb.insert("TimeTable", null, values)
                writeDb.close()
            }
            viewPager.adapter = PageAdapter(this);
        }
        profileWindows.contentView.alpha = 1f
        profileWindows.showAtLocation(this.window.decorView, Gravity.CENTER, 0, 0)
    }

    fun viewClick(view: View) {
        val intent = Intent(this, ProfileActivity::class.java)
        for (index in 0..4) {
            if (view.id == idList[index]) {
                intent.putExtra(
                    "classNum",
                    view.findViewById<TextView>(classNumList[index]).text.toString()
                )
            }
        }
        startActivity(intent)
    }
}