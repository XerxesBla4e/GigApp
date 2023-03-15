package com.example.vendorapp.jActivities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.vendorapp.R;
import com.flutterwave.raveandroid.RaveConstants;
import com.flutterwave.raveandroid.RavePayActivity;
import com.flutterwave.raveandroid.RavePayManager;

public class PayActivity extends AppCompatActivity implements View.OnClickListener {

    EditText amount;
    String jobseekerID, adminID;
    Button pay;
    private String samount;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);

        hooks();

        if (getIntent() != null) {
            jobseekerID = getIntent().getStringExtra("jobseekerID");
            adminID = getIntent().getStringExtra("adminID");
        }
        pay.setOnClickListener(this);
        back.setOnClickListener(this);


    }

    private void hooks() {
        pay = findViewById(R.id.btnpay);
        amount = findViewById(R.id.editpay1);
        back = findViewById(R.id.bk2);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnpay:
                samount = amount.getText().toString();
                if (TextUtils.isEmpty(samount)) {
                    amount.requestFocus();
                    amount.setError("Fill Amount Field First");
                } else {
                    processPayment(samount);
                }
                break;
            case R.id.bk2:
                onBackPressed();
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void processPayment(String samount) {
        new RavePayManager(this)
                .setAmount(Double.parseDouble(samount))
                .setEmail("Mugashab@gmail.com")
                .setCountry("Ug")
                .setCurrency("Ugx")
                .setfName("xerxes")
                .setlName("blaise")
                .setNarration("Labor Payment")
                .setPublicKey("")
                .setEncryptionKey("")
                .setTxRef(System.currentTimeMillis() + "Ref")
                .acceptAccountPayments(true)
                .acceptBankTransferPayments(true)
                .acceptUgMobileMoneyPayments(true)
                .acceptCardPayments(true)
                .onStagingEnv(true)
                .shouldDisplayFee(true)
                .showStagingLabel(true)
                .initialize();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RaveConstants.RAVE_REQUEST_CODE && data != null) {
            String message = data.getStringExtra("response");
            if (resultCode == RavePayActivity.RESULT_SUCCESS) {
                Toast.makeText(getApplicationContext(), "SUCCESS" + message, Toast.LENGTH_SHORT).show();
            } else if (resultCode == RavePayActivity.RESULT_ERROR) {
                Toast.makeText(getApplicationContext(), "ERROR" + message, Toast.LENGTH_SHORT).show();
            } else if (resultCode == RavePayActivity.RESULT_CANCELLED) {
                Toast.makeText(getApplicationContext(), "CANCELLED" + message, Toast.LENGTH_SHORT).show();
            }
        }
    }
}