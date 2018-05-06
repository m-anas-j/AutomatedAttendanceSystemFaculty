package com.example.weisslogia.automatedattendancesystemfaculty;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class FacultySignupActivity extends AppCompatActivity {

    private EditText fullNameText;
    private EditText userNameText;
    private EditText userIdText;
    private EditText passWordText;
    private Faculty newFaculty;
    private FacultyDBHandler facultyDBHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_signup);


    }

    public void registerButtonClicked(View view)
    {
        facultyDBHandler = new FacultyDBHandler(this,null,null,1);

        fullNameText = findViewById(R.id.fullNameText);
        userNameText = findViewById(R.id.userNameText);
        userIdText = findViewById(R.id.userIdText);
        passWordText = findViewById(R.id.passWordText);

        String fullname = fullNameText.getText().toString();
        String username = userNameText.getText().toString();
        int userid = Integer.parseInt(userIdText.getText().toString());
        String password = passWordText.getText().toString();

        if (fullname.isEmpty()||username.isEmpty()||password.isEmpty())
        {
            Toast.makeText(this, "All fields must be valid", Toast.LENGTH_SHORT).show();
            return;
        }

        newFaculty = new Faculty(fullname,username,userid,password);

        facultyDBHandler.addNewFaculty(newFaculty);
        String name = facultyDBHandler.getNewlyCreatedFacultyInfo(newFaculty.getFacultyId());
        Toast.makeText(this,"Successfully added new faculty "+name,Toast.LENGTH_SHORT).show();
    }

    public void coursesButtonClicked(View view)
    {
        String [] listItems;
        boolean [] checkedItems;
        final ArrayList<Integer> userItems = new ArrayList<>();
        AlertDialog.Builder coursesAlertDialog = new AlertDialog.Builder(this);
        coursesAlertDialog.setTitle("Courses available");

        listItems = getResources().getStringArray(R.array.available_courses);
        checkedItems = new boolean[listItems.length];
        coursesAlertDialog.setMultiChoiceItems(listItems, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if (isChecked)
                {
                    if (!userItems.contains(which))
                    {
                        userItems.add(which);
                    }
                    else
                    {
                        userItems.remove(which);
                    }
                }
            }
        });

    }

}
