package com.example.weisslogia.automatedattendancesystemfaculty;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

//packages required for running nfc

import android.nfc.NfcAdapter;
import android.widget.EditText;
import android.widget.Toast;

public class WelcomeScreenActivity extends AppCompatActivity {

    FacultyDBHandler facultyDBHandler = new FacultyDBHandler(this, null, null,1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);

        NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (nfcAdapter==null)
        {
            Toast.makeText(this,"Sorry, your device doesn't support NFC",Toast.LENGTH_LONG).show();
            return;
        }
        if (!nfcAdapter.isEnabled())
        {
            Toast.makeText(this,"Please enable NFC from your device settings",Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(this,"Congratulations! You are ready to run this app.",Toast.LENGTH_LONG).show();
        }
    }

    public void loginButtonClicked(View view)
    {
        EditText facultyIdEditText = (EditText) findViewById(R.id.facultyID);
        String facultyId = facultyIdEditText.getText().toString();

        EditText facultyPasswordEditText = (EditText) findViewById(R.id.facultyPassword);
        String facultyPassword = facultyPasswordEditText.getText().toString();

        String password = facultyDBHandler.searchPassword(facultyId);

        if(facultyPassword.equals(password)) {
            Intent facultyLoginIntent = new Intent(this, FacultyCourses.class);
            facultyLoginIntent.putExtra("keyOfFacultyId",facultyId);
            startActivity(facultyLoginIntent);
        }
        else {
            Toast.makeText(WelcomeScreenActivity.this, "Wrong Credentials!", Toast.LENGTH_LONG).show();
        }
    }

    public void signupButtonClicked(View view)
    {
        Intent signupIntent = new Intent(this,FacultySignupActivity.class);
        startActivity(signupIntent);
    }

    public void adminModeClicked(View view)
    {
        Intent adminLoginIntent = new Intent(this,ViewFacultyDatabase.class);
        startActivity(adminLoginIntent);
    }
}
