package com.example.vendorapp.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.vendorapp.Messaging.FcmNotificationsSender;
import com.example.vendorapp.R;
import com.google.firebase.messaging.FirebaseMessaging;

public class notifications extends AppCompatActivity implements View.OnClickListener {

    Button onedevice, multipledevices;
    EditText tokenedt, edttitle, edtmessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificationtest);

        hooks();

        onedevice.setOnClickListener(this);
        multipledevices.setOnClickListener(this);
        FirebaseMessaging.getInstance().subscribeToTopic("all");
    }

    private void hooks() {
        onedevice = findViewById(R.id.onedevice);
        multipledevices = findViewById(R.id.all);
        tokenedt = findViewById(R.id.edttoken);
        edttitle = findViewById(R.id.edttitle);
        edtmessage = findViewById(R.id.edtmessage);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.onedevice:

                break;
            case R.id.all:
                if (!edttitle.getText().toString().isEmpty() && !edtmessage.getText().toString().isEmpty()) {
                    FcmNotificationsSender notificationsSender = new FcmNotificationsSender("/topics/all",
                            edttitle.getText().toString(),
                            edtmessage.getText().toString(), getApplicationContext(),
                            notifications.this);
                    notificationsSender.SendNotifications();
                } else {
                    Toast.makeText(getApplicationContext(), "Fill Missing Fields", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }
}