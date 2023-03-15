package com.example.vendorapp.jActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.vendorapp.JobSeeker.AllJobs;
import com.example.vendorapp.JobSeeker.JobSeeker;
import com.example.vendorapp.R;
import com.example.vendorapp.Models.UserDetsModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class jApplyActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "jApplyActivity";
    Button btnapply;
    public String adminID, timestamp1;

    public FirebaseAuth firebaseAuth;
    public ProgressDialog progressDialog;
    HashMap<String, Object> hashMap1;
    public String applicationId, name, email, phone;

    public EditText experience, educlvl, age, editprev;
    public String stitle, spayment, sexperience, sjtype, seduclvl, strait, seditprev;
    public String stitle1, spayment1, sexperience1, sjtype1, seduclvl1, strait1, sage1,sdepartment1;
    private DatabaseReference databaseReference;
    private FirebaseUser user;
    public int experience1;
    private String sage;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_japply);

        progressDialog = new ProgressDialog(getApplicationContext());
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        hooks();

        btnapply.setOnClickListener(this);
        back.setOnClickListener(this);

        if (getIntent() != null) {
            adminID = getIntent().getStringExtra("Adminid");
            timestamp1 = getIntent().getStringExtra("jSeekerid");
            stitle1 = getIntent().getStringExtra("title");
            spayment1 = getIntent().getStringExtra("payment");
            sexperience1 = getIntent().getStringExtra("experience");
            sjtype1 = getIntent().getStringExtra("jtype");
            seduclvl1 = getIntent().getStringExtra("dutystation");
            sdepartment1 = getIntent().getStringExtra("department");
            strait1 = getIntent().getStringExtra("traits");
            sage1 = getIntent().getStringExtra("deadline");

        } else {
            Log.d(TAG, "Timestamp | Job creator Id missing");
        }

        loadProfile();
    }

    private void loadProfile() {
        FirebaseDatabase.getInstance().getReference("Users").child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserDetsModel userDetsModel = snapshot.getValue(UserDetsModel.class);

                name = userDetsModel.getName();
                email = userDetsModel.getEmail();
                phone = userDetsModel.getPhone();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void hooks() {
        btnapply = findViewById(R.id.btnapply);
        experience = findViewById(R.id.editexperience);
        educlvl = findViewById(R.id.editeduc);
        editprev = findViewById(R.id.editprev);
        age = findViewById(R.id.editage);
        back = findViewById(R.id.bk3);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnapply:
                sexperience = experience.getText().toString();
                seduclvl = educlvl.getText().toString();
                seditprev = editprev.getText().toString();
                sage = age.getText().toString();

                if (!validateFields()) {
                    return;
                }
                japply();
                break;
            case R.id.bk3:
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

    private void japply() {
        final String timestamp = "" + System.currentTimeMillis();
        int exp2 = Integer.parseInt(sexperience);
        int prevexp2 = Integer.parseInt(seditprev);

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("applicationID", "" + timestamp);
        hashMap.put("applicationTime", "" + timestamp);
        hashMap.put("Status", "Pending");
        hashMap.put("experience", exp2);
        hashMap.put("prevexperience", prevexp2);
        hashMap.put("age", sage);
        hashMap.put("appliedBy", "" + firebaseAuth.getUid());
        hashMap.put("appliedTo", "" + adminID);


        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(adminID).child("Applications");
        databaseReference.child(timestamp).setValue(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    int experience1 = Integer.parseInt(sexperience1);

                    @Override
                    public void onSuccess(Void unused) {
                        hashMap1 = new HashMap<>();
                        hashMap1.put("title", stitle1);
                        hashMap1.put("payment", spayment1);
                        hashMap1.put("experience", experience1);
                        hashMap1.put("jobtype", sjtype1);
                        hashMap1.put("educationlevel", seduclvl1);
                        hashMap1.put("specialskill", strait1);
                        hashMap1.put("age", sage1);
                        hashMap1.put("timestamp", timestamp);
                        hashMap1.put("uid", firebaseAuth.getUid());
                        databaseReference.child(timestamp).child("Applicants").setValue(hashMap1);
                        Toast.makeText(getApplicationContext(), "Successfully Applied", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), AllJobs.class));
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), e.getMessage() + "", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private boolean validateFields() {
        sexperience = experience.getText().toString();
        seduclvl = educlvl.getText().toString();
        seditprev = editprev.getText().toString();
        sage = age.getText().toString();

        if (TextUtils.isEmpty(sage)) {
            age.setError("Fill Age Field");
            age.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(sexperience)) {
            experience.setError("Fill Experience Field");
            experience.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(seduclvl)) {
            educlvl.setError("Fill Education level Field");
            educlvl.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(seditprev)) {
            editprev.setError("Fill Previous Job Duration");
            editprev.requestFocus();
            return false;
        }
        return true;
    }
}