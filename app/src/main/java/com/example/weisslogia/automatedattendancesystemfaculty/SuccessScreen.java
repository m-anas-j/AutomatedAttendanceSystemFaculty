package com.example.weisslogia.automatedattendancesystemfaculty;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class SuccessScreen extends AppCompatActivity {

    private String receivedID;
    TextView statusText, studentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_screen);

        Bundle receivedData = getIntent().getExtras();
        receivedID = receivedData.getString("receivedData");
        Toast.makeText(this,"Attendance taken for student id "+receivedID,Toast.LENGTH_LONG).show();

        statusText=findViewById(R.id.statusText);
        studentId=findViewById(R.id.studentId);
        studentId.setText(studentId.getText().toString()+receivedID);
    }
}
