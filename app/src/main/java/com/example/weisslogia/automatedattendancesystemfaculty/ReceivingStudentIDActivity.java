package com.example.weisslogia.automatedattendancesystemfaculty;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.os.Parcelable;

//packages needed for receiving data via nfc
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.widget.TextView;

public class ReceivingStudentIDActivity extends AppCompatActivity {

    TextView courseId;
    String receivedCourseId;
    String receivedCurrentDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiving_student_id);

        Bundle dataFromLoggedInFaculty = getIntent().getExtras();
        receivedCourseId = dataFromLoggedInFaculty.getString("keyOfCourseId");
        receivedCurrentDate = dataFromLoggedInFaculty.getString("keyOfCurrentDate");
        courseId = findViewById(R.id.courseId);
        courseId.setText(courseId.getText().toString() + " " + receivedCourseId);
    }

    @Override
    protected void onResume(){
        super.onResume();
        Intent intent = getIntent();
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction())) {
            Parcelable[] rawMessages = intent.getParcelableArrayExtra(
                    NfcAdapter.EXTRA_NDEF_MESSAGES);

            NdefMessage message = (NdefMessage) rawMessages[0]; // only one message transferred
            String receivedID = "";
            receivedID = receivedID + new String(message.getRecords()[0].getPayload()) + " ";
            Intent successScreenActivityIntent = new Intent(this, SuccessScreenActivity.class);
            successScreenActivityIntent.putExtra("keyOfReceivedData",receivedID);
            successScreenActivityIntent.putExtra("keyOfReceivedCourseId",receivedCourseId);
            successScreenActivityIntent.putExtra("keyOfReceivedCurrentData",receivedCurrentDate);
            startActivity(successScreenActivityIntent);
            /*TextView reminder = (TextView)findViewById(R.id.reminder);
            reminder.setText(receivedID);*/

        }

    }

}
