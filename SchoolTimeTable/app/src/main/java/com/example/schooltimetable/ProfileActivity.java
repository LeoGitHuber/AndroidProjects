package com.example.schooltimetable;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.Arrays;
import java.util.List;


public class ProfileActivity extends AppCompatActivity {
    List<Integer> list = Arrays.asList(R.id.codeEdit, R.id.classEdit, R.id.teacherEdit, R.id.timeFrom, R.id.timeTo, R.id.from, R.id.to, R.id.room_num);
    List<Integer> checkList = Arrays.asList(R.id.Mon, R.id.Tues, R.id.Wed, R.id.Thur, R.id.Fri, R.id.Sat, R.id.Sun);

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        TimeDatabaseHelper dbHelper = new TimeDatabaseHelper(this, this.getFilesDir().toString() + "/classDb.db", null, 1);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_profile);
        ConstraintLayout constraintLayout = (ConstraintLayout) this.findViewById(R.id.profile);
        ViewGroup.LayoutParams params = constraintLayout.getLayoutParams();
        params.height = ViewGroup.LayoutParams.MATCH_PARENT;
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        constraintLayout.setLayoutParams(params);
        this.findViewById(R.id.add).setVisibility(View.GONE);
        this.findViewById(R.id.delete).setVisibility(View.VISIBLE);
        SQLiteDatabase readableDb = dbHelper.getReadableDatabase();
        Cursor cursor = readableDb.rawQuery("select * from TimeTable where _id='" + getIntent().getStringExtra("classNum") + "'", null);
        cursor.moveToFirst();
        for (int i = 0; i < list.size(); i++) {
            EditText editText = (EditText) this.findViewById(list.get(i));
            editText.setText(cursor.getString(i));
            editText.setEnabled(false);
        }
        for (int i = 0; i < checkList.size(); i++) {
            CheckBox checkBox = (CheckBox) this.findViewById(checkList.get(i));
            if (cursor.getString(8).contains(checkBox.getText().toString())) {
                checkBox.setChecked(true);
            }
            checkBox.setEnabled(false);
        }
        ((Button) this.findViewById(R.id.delete)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase writeableDb = dbHelper.getWritableDatabase();
                writeableDb.delete("TimeTable", "_id = ?", new String[]{((EditText) findViewById(R.id.codeEdit)).getText().toString()});
                writeableDb.close();
                readableDb.close();
                finish();
            }
        });
        readableDb.close();
    }
}