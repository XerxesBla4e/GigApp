package com.example.vendorapp.jActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.vendorapp.Admin.AdminActivity;
import com.example.vendorapp.JobSeeker.JobSeeker;
import com.example.vendorapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class jobposting extends AppCompatActivity implements View.OnClickListener {

    ImageView back;
    EditText title, payment, experience, jtype, dutystation, specialskill, deadline, department1;
    String stitle, spayment, sexperience, sjtype, sdutystation, sspecialskill, sdeadline, sdepartment;
    private ProgressDialog progressDialog;
    FirebaseStorage mStorage;
    private FirebaseAuth firebaseAuth;

    Button btnpost, selectDate;
    private Calendar initcalendar = Calendar.getInstance();

    private DatePickerDialog.OnDateSetListener initDate =
            new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                    initcalendar.set(Calendar.YEAR, i);
                    initcalendar.set(Calendar.MONTH, i1);
                    initcalendar.set(Calendar.DAY_OF_MONTH, i2);
                    deadline.setText(new SimpleDateFormat("yyyy-MM-dd").format(initcalendar.getTime()));

                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobposting);

        initViews();

        back.setOnClickListener(this);

        btnpost.setOnClickListener(this);
        selectDate.setOnClickListener(this);
        selectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(jobposting.this,
                        initDate, initcalendar.get(Calendar.YEAR), initcalendar.get(Calendar.MONTH),
                        initcalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();
    }

    private void initViews() {
        back = findViewById(R.id.bk3);
        title = findViewById(R.id.jobtitle1);
        payment = findViewById(R.id.editpay1);
        experience = findViewById(R.id.editexperience1);
        jtype = findViewById(R.id.editjobtype1);
        dutystation = findViewById(R.id.editeduc1);
        specialskill = findViewById(R.id.editskills1);
        deadline = findViewById(R.id.editage1);
        department1 = findViewById(R.id.department1);
        btnpost = findViewById(R.id.btnpost);
        selectDate = findViewById(R.id.pickDate1);
        progressDialog = new ProgressDialog(getApplicationContext());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bk3:
                onBackPressed();
                break;
            case R.id.btnpost:
                stitle = title.getText().toString().trim();
                spayment = payment.getText().toString().trim();
                sexperience = experience.getText().toString().trim();
                sjtype = jtype.getText().toString().trim();
                sdutystation = dutystation.getText().toString().trim();
                sspecialskill = specialskill.getText().toString().trim();
                sdepartment = department1.getText().toString().trim();
                sdeadline = deadline.getText().toString().trim();

                if (!validatefields()) {
                    return;
                }
                addPosting();
                break;
            default:
                break;
        }
    }

    private void addPosting() {

        final String timestamp = "" + System.currentTimeMillis();

        int experience = Integer.parseInt(sexperience);

        String datePosted = timestamp;
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.parseLong(datePosted));
        String date = DateFormat.format("dd/MM/yyyy hh:mm a", calendar).toString();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("title", stitle);
        hashMap.put("department", sdepartment);
        hashMap.put("payment", spayment);
        hashMap.put("experience", experience);
        hashMap.put("jobtype", sjtype);
        hashMap.put("dutystation", sdutystation);
        hashMap.put("specialskill", sspecialskill);
        hashMap.put("deadline", sdeadline);
        hashMap.put("dateposted", date);
        hashMap.put("timestamp", timestamp);
        hashMap.put("uid", firebaseAuth.getUid());

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child(firebaseAuth.getUid()).child("Postings").child(timestamp).setValue(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        progressDialog.dismiss();
                        DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("Jobs");
                        reference1.child(timestamp).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(jobposting.this, "Job Advert Posted Successfully...", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), AdminActivity.class));
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(jobposting.this, e.getMessage() + "", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(jobposting.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private boolean validatefields() {
        stitle = title.getText().toString().trim();
        spayment = payment.getText().toString().trim();
        sexperience = experience.getText().toString().trim();
        sjtype = jtype.getText().toString().trim();
        sdutystation = dutystation.getText().toString().trim();
        sdepartment = department1.getText().toString().trim();
        sspecialskill = specialskill.getText().toString().trim();
        sdeadline = deadline.getText().toString().trim();


        if (TextUtils.isEmpty(stitle)) {
            title.setError("Title Field Cant Be Empty");
            title.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(sdepartment)) {
            department1.setError("Department Field Cant Be Empty");
            department1.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(spayment)) {
            payment.setError("Payment Field Cant Be Empty");
            payment.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(sdutystation)) {
            dutystation.setError("Duty Statiton Field Cant Be Empty");
            dutystation.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(sexperience)) {
            experience.setError("Experience Field Cant Be Empty");
            experience.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(sdeadline)) {
            deadline.setError("Age Field Cant Be Empty");
            deadline.requestFocus();
            return false;
        }

        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}