package com.example.weisslogia.automatedattendancesystemfaculty;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

public class FacultyCourses extends AppCompatActivity {

    FacultyDBHandler facultyDBHandler;
    ArrayList<String> courses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_courses);

        facultyDBHandler = new FacultyDBHandler(this,null,null,1);

    }
}
