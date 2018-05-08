package com.example.weisslogia.automatedattendancesystemfaculty;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ViewFacultyDatabase extends AppCompatActivity {

    FacultyDBHandler facultyDBHandler = new FacultyDBHandler(this,null,null,1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_faculty_database);

        Bundle bundle = getIntent().getExtras();
        int mode = bundle.getInt("keyOfMode");
        List<String> faculties = new ArrayList<>();
        String faculty;


        if (mode==1)
        {
            Cursor ret = facultyDBHandler.viewAllFaculty();
            ret.moveToFirst();

            while(!ret.isAfterLast())
            {
                faculty="";

                if (ret.getString(ret.getColumnIndex("faculty_id")) != null) {
                    faculty += ret.getString(ret.getColumnIndex("faculty_id"));
                    faculty += " ";
                    faculty += ret.getString(ret.getColumnIndex("faculty_name"));
                    faculty += " ";
                    faculty += ret.getString(ret.getColumnIndex("faculty_password"));
                }


                faculties.add(faculty);
                ret.moveToNext();
            }
        }

        else if (mode==2)
        {
            Cursor ret = facultyDBHandler.viewAllCourse();
            ret.moveToFirst();

            while(!ret.isAfterLast())
            {
                faculty="";

                if (ret.getString(ret.getColumnIndex("_id")) != null) {
                    faculty += ret.getString(ret.getColumnIndex("_id"));
                    faculty += " ";
                    faculty += ret.getString(ret.getColumnIndex("course_name"));
                    faculty += " ";
                    faculty += ret.getString(ret.getColumnIndex("faculty_id"));
                }


                faculties.add(faculty);
                ret.moveToNext();
            }
        }
        else if (mode == 3)
        {
            Cursor ret = facultyDBHandler.viewAttendanceDatabase();
            ret.moveToFirst();

            while(!ret.isAfterLast())
            {
                faculty="";

                    if (ret.getString(ret.getColumnIndex("_id")) != null) {
                        faculty += ret.getString(ret.getColumnIndex("_id"));
                        faculty += " ";
                        faculty += ret.getString(ret.getColumnIndex("date"));
                        faculty += " ";
                        faculty += ret.getString(ret.getColumnIndex("student_id"));
                        faculty += " ";
                        faculty += ret.getString(ret.getColumnIndex("status"));
                    }

                    faculties.add(faculty);
                    ret.moveToNext();
                }
                ret.close();
        }

        ListAdapter myAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,faculties);
        ListView facultyListView = findViewById(R.id.facultyListView);
        facultyListView.setAdapter(myAdapter);

    }


}
