package com.example.weisslogia.automatedattendancesystemfaculty;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;


import java.util.ArrayList;

public class FacultyCourses extends AppCompatActivity {

    FacultyDBHandler facultyDBHandler;
    ArrayList<String> courses;
    TextView currentDate;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_courses);

        facultyDBHandler = new FacultyDBHandler(this,null,null,1);
        courses = new ArrayList<>();
        linearLayout = findViewById(R.id.linearLayout);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        Bundle dataFromLogin = getIntent().getExtras();
        int facultyId = Integer.parseInt(dataFromLogin.getString("keyOfFacultyId"));

        String test = "";
        int i=0;
        Cursor facultysCoursesCursor = facultyDBHandler.getFacultysCourses(facultyId);
        facultysCoursesCursor.moveToFirst();
        while (!facultysCoursesCursor.isAfterLast())
        {
            courses.add(facultysCoursesCursor.getString(0));
            test += courses.get(i);
            test += " ";
            facultysCoursesCursor.moveToNext();
            i++;
        }

        currentDate = new TextView(this);
        Date currentTime = Calendar.getInstance().getTime();
        String date = currentTime.toString();
        date = date.substring(0, Math.min(date.length(), 10));  //Math.min returns the lesser value of the two.
        currentDate.setText(date);
        int textSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,15, getResources().getDisplayMetrics());
        currentDate.setTextSize(textSize);
        currentDate.setTextColor(getResources().getColor(R.color.colorMyShadeOfBlue));
        LinearLayout.LayoutParams textParams = getTextParams();
        linearLayout.addView(currentDate,textParams);

        for (int j=0; j<courses.size(); j++)
        {
            final Button courseButton = new Button(this);
            courseButton.setText(courses.get(j));
            courseButton.setTextColor(Color.WHITE);
            courseButton.setBackgroundResource(R.drawable.ripple_courses);
            LinearLayout.LayoutParams buttonParams = getButtonParams();
            linearLayout.addView(courseButton,buttonParams);

            //set button click listener
            courseButton.setOnClickListener(

                    new Button.OnClickListener()
                    {
                        public void onClick(View view)
                        {
                            Toast.makeText(FacultyCourses.this, courseButton.getText().toString() + " selected", Toast.LENGTH_SHORT).show();
                        }
                    }

            );
        }

    }

    public LinearLayout.LayoutParams getButtonParams()
    {
        LinearLayout.LayoutParams buttonParams =  new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        buttonParams.gravity = Gravity.CENTER;

        //convert dip value to corresponding pixel value for each device
        int widthPixel = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,250, getResources().getDisplayMetrics());
        int heightPixel = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,50, getResources().getDisplayMetrics());
        int topMarginPixel = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,25, getResources().getDisplayMetrics());
        int bottomMarginPixel = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,25, getResources().getDisplayMetrics());

        buttonParams.width = widthPixel;
        buttonParams.height = heightPixel;

        buttonParams.topMargin = topMarginPixel;


        return buttonParams;
    }

    public LinearLayout.LayoutParams getTextParams()
    {
        LinearLayout.LayoutParams textParams =  new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        textParams.gravity = Gravity.CENTER;
        textParams.gravity = Gravity.CENTER_HORIZONTAL;

        //convert dip value to corresponding pixel value for each device
        int widthPixel = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,250, getResources().getDisplayMetrics());
        int heightPixel = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,170, getResources().getDisplayMetrics());
        int topMarginPixel = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,25, getResources().getDisplayMetrics());
        int bottomMarginPixel = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,25, getResources().getDisplayMetrics());


        textParams.height = heightPixel;

        textParams.bottomMargin = bottomMarginPixel;

        return textParams;
    }
}
