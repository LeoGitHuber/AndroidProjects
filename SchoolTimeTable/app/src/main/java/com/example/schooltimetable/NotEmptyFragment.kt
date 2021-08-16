package com.example.schooltimetable

import android.database.Cursor
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment

class NotEmptyFragment : Fragment() {
    private val listMAE = listOf<Int>(R.id.m, R.id.a, R.id.e)
    private val classList = listOf<Int>(
        R.id.morn_num_0,
        R.id.morn_class_0,
        R.id.morn_teacher_0,
        R.id.morn_time_0,
        R.id.morn_duration_0,
        R.id.morn_room_0,
        R.id.morn_num_1,
        R.id.morn_class_1,
        R.id.morn_teacher_1,
        R.id.morn_time_1,
        R.id.morn_duration_1,
        R.id.morn_room_1,
        R.id.after_num_0,
        R.id.after_class_0,
        R.id.after_teacher_0,
        R.id.after_time_0,
        R.id.after_duration_0,
        R.id.after_room_0,
        R.id.after_num_1,
        R.id.after_class_1,
        R.id.after_teacher_1,
        R.id.after_time_1,
        R.id.after_duration_1,
        R.id.after_room_1,
        R.id.even_num_0,
        R.id.even_class_0,
        R.id.even_teacher_0,
        R.id.even_time_0,
        R.id.even_duration_0,
        R.id.even_room_0,
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val dbHelper = TimeDatabaseHelper(context?.applicationContext, context?.filesDir.toString() + "/classDb.db", null, 1)
        val scheduleList = listOf<Int>(R.id.morn_schedule, R.id.after_schedule, R.id.even_schedule)
        val routineList = listOf<Int>(R.id.morn_0, R.id.after_0, R.id.morn_1, R.id.after_1, R.id.even)
        val readableDb = dbHelper.readableDatabase
        var cursor = readableDb.rawQuery(
            "select * from TimeTable where dayList like '%" + arguments?.getString("day"),
            null
        )
        val view = inflater.inflate(R.layout.fragment_page, container, false)
        if (cursor.count == 0) {
            for (index in listMAE) {
                view.findViewById<LinearLayout>(index).visibility = View.GONE
            }
            view.findViewById<ConstraintLayout>(R.id.scheduleOrNot).visibility = View.VISIBLE
        } else {
            var temp = 0
            for (index in listOf<String>("4", "8", "10")) {
                cursor = readableDb.rawQuery(
                    "select * from TimeTable where dayList like '%" + arguments?.getString("day") + " and timeTo <= " + index + " order by timeFrom ASC",
                    null
                )
                when (cursor.count - temp) {
                    0 -> {
                        if (index.toInt() % 4 == 0) {
                            view.findViewById<TextView>(scheduleList[index.toInt() / 4 - 1]).visibility =
                                View.VISIBLE
                            view.findViewById<ConstraintLayout>(routineList[index.toInt() / 4 - 1]).visibility = View.GONE
                            view.findViewById<ConstraintLayout>(routineList[index.toInt() / 4 + 1]).visibility = View.GONE
                        } else {
                            view.findViewById<TextView>(R.id.even_schedule).visibility = View.VISIBLE
                            view.findViewById<ConstraintLayout>(R.id.even).visibility = View.GONE
                        }
                    }
                    1 -> {
                        cursor.moveToFirst()
                        when(cursor.getInt(3)) {
                            in 4*(index.toInt()/8)+1..4*(index.toInt()/8)+2 -> {
                                repeatSetText(view, cursor, 2 * (index.toInt() / 8))
                                view.findViewById<ConstraintLayout>(routineList[index.toInt() / 8 + 2]).visibility = View.GONE
                            }
                            in 4*(index.toInt()/8)+3..4*(index.toInt()/8)+4 -> {
                                repeatSetText(view, cursor, 2 * (index.toInt() / 8) + 1)
                                view.findViewById<ConstraintLayout>(routineList[index.toInt() / 8]).visibility = View.GONE
                            }
                            else -> {
                                repeatSetText(view, cursor, 4)
                            }
                        }
                    }
                    else -> {
                        cursor.moveToFirst()
                        repeatSetText(view, cursor, index.toInt() / 8)
                        cursor.moveToNext()
                        repeatSetText(view, cursor, index.toInt() / 8 + 2)
                    }
                }
                temp = cursor.count
            }
        }
        return view
    }

    fun repeatSetText(view : View, cursor : Cursor, index : Int) {
        for (i in 0..2) {
            view.findViewById<TextView>(classList[i + index * 6]).setText(cursor.getString(i))
        }
        view.findViewById<TextView>(classList[3 + index * 6]).setText("Lesson " + cursor.getString(3) + " - " + cursor.getString(4))
        view.findViewById<TextView>(classList[4 + index * 6]).setText("Week" + cursor.getString(5) + " - " + cursor.getString(6))
        view.findViewById<TextView>(classList[5 + index * 6]).setText(cursor.getString(7))
    }
}