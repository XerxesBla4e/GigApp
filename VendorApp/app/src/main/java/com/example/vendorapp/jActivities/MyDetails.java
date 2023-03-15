package com.example.vendorapp.jActivities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vendorapp.Adapter.ApplicantAdapter;
import com.example.vendorapp.Admin.AdminActivity;
import com.example.vendorapp.R;
import com.example.vendorapp.Models.Amodel;
import com.example.vendorapp.Models.ApplicationsModel;

import com.example.vendorapp.Models.UserDetsModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;


public class MyDetails extends AppCompatActivity implements View.OnClickListener {
    RecyclerView recyclerView;
    ApplicantAdapter applicantAdapter;
    ImageView back;

    private static final String TAG = "My Details";

    TextView status, appid3, name2;
    TextView title, payment, experience, jtype, educlvl, trait, age;
    String applicationID;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;

    public String adminID;
    public String currentID;
    public String telno, name1, id, jseekrlatitude, jseekrlongitude, adminlongitude, adminlatitude;
    public int total, quantity;
    String appliedBy;

    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_details);

        hooks();

        back.setOnClickListener(this);

        firebaseAuth = FirebaseAuth.getInstance();

        if (getIntent() != null) {
            applicationID = getIntent().getStringExtra("applicationID");
            adminID = getIntent().getStringExtra("adminID");
        }

        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(adminID).child("Applications");
        databaseReference.child(applicationID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                ApplicationsModel applicationsModel = snapshot.getValue(ApplicationsModel.class);
                if (applicationsModel != null) {
                    appid3.setText(applicationsModel.getApplicationID());
                    //  clientID = String.valueOf(ordersModel.getOrderBy());
                    status.setText(applicationsModel.getStatus());

                    if (status.equals("pending")) {
                        status.setText(applicationsModel.getStatus());
                        status.setTextColor(getApplicationContext().getResources().getColor(R.color.green_pie));
                    } else if (status.equals("rejected")) {
                        status.setText(applicationsModel.getStatus());
                        status.setTextColor(getApplicationContext().getResources().getColor(R.color.red));
                    }


                    FirebaseDatabase.getInstance().getReference("Users").child(applicationsModel.getAppliedBy()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {
                            UserDetsModel userDetsModel = snapshot.getValue(UserDetsModel.class);
                            if (userDetsModel != null) {
                                name1 = userDetsModel.getName();
                                email = userDetsModel.getEmail();
                                name2.setText(name1);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError error) {
                            Toast.makeText(getApplicationContext(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });


                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(adminID).child("Applications");
        databaseReference.child(applicationID).child("Applicants").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Amodel amodel = snapshot.getValue(Amodel.class);
                title.setText(amodel.getTitle());
                payment.setText(amodel.getPayment());
                experience.setText(String.valueOf(amodel.getExperience()));
                jtype.setText(amodel.getJobtype());
                educlvl.setText(amodel.getEducationlevel());
                trait.setText(amodel.getSpecialskill());
                age.setText(amodel.getAge());

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void hooks() {
        title = findViewById(R.id.txtTitle);
        payment = findViewById(R.id.txtpayment);
        experience = findViewById(R.id.txtexperience);
        jtype = findViewById(R.id.txtjtype);
        educlvl = findViewById(R.id.txteduc);
        trait = findViewById(R.id.txttrait);
        age = findViewById(R.id.txtAge);
        back = findViewById(R.id.bk2);

        name2 = findViewById(R.id.name1);
        status = findViewById(R.id.status1);

        appid3 = findViewById(R.id.app1);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bk2:
                onBackPressed();
                break;
        }
    }
}