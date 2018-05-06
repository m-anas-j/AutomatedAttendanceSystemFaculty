package com.example.weisslogia.automatedattendancesystemfaculty;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

public class Courses extends AppCompatActivity {

    private TextView currentDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);

        Date currentTime = Calendar.getInstance().getTime();
        String date = currentTime.toString();
        date = date.substring(0, Math.min(date.length(), 10));  //Math.min returns the lesser value of the two.
        currentDate = (TextView)findViewById(R.id.currentDate);
        currentDate.setText(date);
    }

    public void onCoursesButtonClicked(View view)
    {
        Intent i = new Intent(this,ReceivingStudentIDActivity.class);
        startActivity(i);
    }

}
