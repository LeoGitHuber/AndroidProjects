package com.example.schooltimetable;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class TimeDatabaseHelper extends SQLiteOpenHelper {
    public TimeDatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE TimeTable(_id TEXT PRIMARY KEY, className TEXT, teacherName TEXT, timeFrom INTEGER, timeTo INTEGER, periodFrom INTEGER, periodTo INTEGER, classRoom TEXT, dayList TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS TimeTable");
        onCreate(db);
    }
}
