package com.example.weisslogia.automatedattendancesystemfaculty;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class DisplayDatabasesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_databases);
    }

    public void facultyButtonClicked(View view)
    {
        Intent attendanceDatabaseIntent = new Intent(this,ViewFacultyDatabase.class);
        int mode = 1;
        attendanceDatabaseIntent.putExtra("keyOfMode",mode);
        startActivity(attendanceDatabaseIntent);
    }

    public void courseButtonClicked(View view)
    {
        Intent attendanceDatabaseIntent = new Intent(this,ViewFacultyDatabase.class);
        int mode = 2;
        attendanceDatabaseIntent.putExtra("keyOfMode",mode);
        startActivity(attendanceDatabaseIntent);
    }

    public void attendanceButtonClicked(View view)
    {
        Intent attendanceDatabaseIntent = new Intent(this,ViewFacultyDatabase.class);
        int mode = 3;
        attendanceDatabaseIntent.putExtra("keyOfMode",mode);
        startActivity(attendanceDatabaseIntent);
    }

    public void deleteAllDatabasesButtonClicked(View view)
    {
        FacultyDBHandler facultyDBHandler = new FacultyDBHandler(this,null,null,1);
        facultyDBHandler.clearAllDatabases();
    }
}
