package com.example.vendorapp.JobSeeker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.example.vendorapp.Adapter.AdminAdapter;
import com.example.vendorapp.Login;
import com.example.vendorapp.R;
import com.example.vendorapp.UpdateProfile;
import com.example.vendorapp.Models.ShopModel;
import com.example.vendorapp.Models.UserDetsModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Locale;

public class JobSeeker extends AppCompatActivity implements View.OnClickListener {

    ImageView signout, editprof, lingual1;
    TextView myprof;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    String username, accttype;
    BottomNavigationView bottomNavigationView;
    RecyclerView recyclerView;
    AdminAdapter adminAdapter;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        changeLanguage();
        setContentView(R.layout.activity_main_jobseeker);

        hooks();

        setSupportActionBar(toolbar);
        toolbar.setTitle("Jobs");

        signout.setOnClickListener(this);
        //   back.setOnClickListener(this);
        editprof.setOnClickListener(this);
        lingual1.setOnClickListener(this);

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        checkUser();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        FirebaseRecyclerOptions<ShopModel> options =
                new FirebaseRecyclerOptions.Builder<ShopModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("Users").orderByChild("accounttype")
                                .equalTo("Admin"), ShopModel.class)
                        .build();

        adminAdapter = new AdminAdapter(getApplicationContext(), options);
        recyclerView.setAdapter(adminAdapter);
        adminAdapter.notifyDataSetChanged();

        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        Intent x = new Intent(getApplicationContext(), AllJobs.class);
                        x.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(x);
                        break;
                    case R.id.org:
                        Intent x1 = new Intent(getApplicationContext(), JobSeeker.class);
                        x1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(x1);
                        break;
                    case R.id.reminders:
                        Intent x2 = new Intent(getApplicationContext(), JobReminders.class);
                        x2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(x2);
                        break;
                    default:
                        break;
                }

            }
        });

    }

    private void checkUser() {
        if (user == null) {
            startActivity(new Intent(JobSeeker.this, Login.class));
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
        recyclerView = findViewById(R.id.admins);
        //    back = findViewById(R.id.bk);
        editprof = findViewById(R.id.edit);
        toolbar = findViewById(R.id.toolBar);
        bottomNavigationView = findViewById(R.id.bottomNavView);
        lingual1 = findViewById(R.id.lingual);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signout:
                firebaseAuth.signOut();
                makeOffline();
                checkUser();
                startActivity(new Intent(JobSeeker.this, Login.class));
                break;
            case R.id.edit:
                startActivity(new Intent(JobSeeker.this, UpdateProfile.class));
                break;
            case R.id.lingual:
                SelectLanguage();
                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        adminAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adminAdapter.stopListening();
    }

    //dialog box to enable user select prefered language
    private void SelectLanguage() {
        final String[] langs = {"Swahili", "Nyankole", "English"};
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(JobSeeker.this);
        mBuilder.setTitle("Select Language");
        mBuilder.setSingleChoiceItems(langs, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int xer) {
                if (xer == 0) {
                    //based on string files
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

    //set language method
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

    //change language method
    public void changeLanguage() {
        SharedPreferences sharedPreferences = getSharedPreferences("xerx", Context.MODE_PRIVATE);
        String lang = sharedPreferences.getString("language", "");
        setLanguage(lang);
    }
}