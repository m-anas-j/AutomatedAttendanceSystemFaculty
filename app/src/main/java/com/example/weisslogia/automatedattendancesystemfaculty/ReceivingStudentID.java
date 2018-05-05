package com.example.weisslogia.automatedattendancesystemfaculty;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.os.Parcelable;

//packages needed for receiving data via nfc
import android.app.Activity;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class ReceivingStudentID extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiving_student_id);
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
            Intent i2 = new Intent(this, SuccessScreen.class);
            i2.putExtra("receivedData",receivedID);
            startActivity(i2);
            /*TextView reminder = (TextView)findViewById(R.id.reminder);
            reminder.setText(receivedID);*/

        }

    }

}
