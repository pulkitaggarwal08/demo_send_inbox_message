package com.demo_send_sms.pulkit;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {

    private EditText idTextPhone, idTextMsg;
    private Button idBtnStart;

    private static final int PERMISSIONS_REQUEST_SEND_SMS = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findIds();
        checkPermissions();
        init();

    }

    private void findIds() {

        idTextPhone = (EditText) findViewById(R.id.idTextPhone);
        idTextMsg = (EditText) findViewById(R.id.idTextMsg);
        idBtnStart = (Button) findViewById(R.id.idBtnStart);
    }

    private void checkPermissions() {

        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS);

        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            MyMessage();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS},
                    PERMISSIONS_REQUEST_SEND_SMS);
        }

    }

    private void init() {

        idBtnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String myNumber = idTextPhone.getText().toString();
                String myMsg = idTextMsg.getText().toString();

                if (myNumber == null || myNumber.equals("") || myNumber == null || myNumber.equals(null) ||
                        myMsg == null || myMsg.equals("") || myMsg == null || myMsg.equals(null)) {
                    Toast.makeText(getApplicationContext(), "Field cannot be empty", Toast.LENGTH_SHORT).show();
                } else {
                    if (TextUtils.isDigitsOnly(myNumber)) {
                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage(myNumber, null, myMsg, null, null);

                        Toast.makeText(getApplicationContext(), "Message has been sent", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Please enter your correct number", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }


    private void MyMessage() {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == PERMISSIONS_REQUEST_SEND_SMS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults.length >= 0) {
                MyMessage();
            } else {
                Toast.makeText(this, "Until you grant the permission, we cannot send the message", Toast.LENGTH_SHORT).show();
            }

        }

    }


}
