package com.example.vendorapp.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vendorapp.Login;
import com.example.vendorapp.R;
import com.example.vendorapp.UpdateProfile;
import com.example.vendorapp.jActivities.ViewApplicants;
import com.example.vendorapp.jActivities.jobposting;
import com.example.vendorapp.Models.UserDetsModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Locale;

public class AdminActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView signout, editprof, lingual;
    TextView myprof;
    private FirebaseAuth firebaseAuth;
    String username, accttype;
    FirebaseUser user;
    ImageView create, approved, applicants, payments;
    // BottomNavigationView bottomNavigationView;
    private static final String TAG = "Job Seeker Signup";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        changeLanguage();
        setContentView(R.layout.activity_main_admin);

        hooks();

        signout.setOnClickListener(this);
        editprof.setOnClickListener(this);
        lingual.setOnClickListener(this);
        create.setOnClickListener(this);
        approved.setOnClickListener(this);
        payments.setOnClickListener(this);
        applicants.setOnClickListener(this);

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        checkUser();

        /*
        getSupportFragmentManager().beginTransaction().replace(R.id.main_container1, new Home1Fragment()).commit();

        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_container1, new HomeFragment()).addToBackStack(null).commit();
                        break;
                    case R.id.viewo:
                        break;
                }

            }
        });*/

    }

    private void checkUser() {
        if (user == null) {
            startActivity(new Intent(AdminActivity.this, Login.class));
        } else {
            loadmyProfile();
        }
    }

    private void makeOffline() {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("online", "false");

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        databaseReference.child(user.getUid()).updateChildren(hashMap);
    }

    private void loadmyProfile() {
        FirebaseDatabase.getInstance().getReference("Users").child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserDetsModel userDetsModel = snapshot.getValue(UserDetsModel.class);

                username = userDetsModel.getName();
                accttype = userDetsModel.getAccounttype();

                myprof.setText(username + "(" + accttype + ")");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void hooks() {
        signout = findViewById(R.id.signout);
        myprof = findViewById(R.id.prfle);
        editprof = findViewById(R.id.edit);
        lingual = findViewById(R.id.lingual);
        create = findViewById(R.id.create);
        approved = findViewById(R.id.approved);
        applicants = findViewById(R.id.applicants);
        payments = findViewById(R.id.payments);
        //   bottomNavigationView = findViewById(R.id.bottomNavigationView1);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signout:
                firebaseAuth.signOut();
                makeOffline();
                checkUser();
                startActivity(new Intent(AdminActivity.this, Login.class));
                break;
            case R.id.edit:
                startActivity(new Intent(AdminActivity.this, UpdateProfile.class));
                break;
            case R.id.lingual:
                showLanguages();
                break;
            case R.id.approved:
                Intent xer3 = new Intent(this, ApprovedCandidates.class);
                startActivity(xer3);
                break;
            case R.id.applicants:
                Intent xer1 = new Intent(this, ViewApplicants.class);
                startActivity(xer1);
                break;
            case R.id.payments:
                Intent xer4 = new Intent(this, ViewPostings.class);
                startActivity(xer4);
                break;
            case R.id.create:
                Intent xer = new Intent(this, jobposting.class);
                startActivity(xer);
                break;
        }
    }

    private void showLanguages() {
        final String[] langs = {"Swahili", "Nyankole", "English"};
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(AdminActivity.this);
        mBuilder.setTitle("Select Language");
        mBuilder.setSingleChoiceItems(langs, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int xer) {
                if (xer == 0) {
                    setLanguage("nyn");
                    recreate();
                } else if (xer == 1) {
                    setLanguage("sw");
                    recreate();
                } else if (xer == 2) {
                    setLanguage("en");
                    recreate();
                }
                dialog.dismiss();
            }
        });
        AlertDialog mDialog = mBuilder.create();
        mBuilder.show();
    }

    public void setLanguage(String language) {
        Locale locale = new Locale(language);
        locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.locale = locale;
        getBaseContext().getResources().updateConfiguration(configuration, getBaseContext().getResources().getDisplayMetrics());

        SharedPreferences.Editor preferences = getSharedPreferences("xerx", Context.MODE_PRIVATE).edit();
        preferences.putString("language", language);
        preferences.apply();
    }

    public void changeLanguage() {
        SharedPreferences sharedPreferences = getSharedPreferences("xerx", Context.MODE_PRIVATE);
        String lang = sharedPreferences.getString("language", "");
        setLanguage(lang);
    }
}
