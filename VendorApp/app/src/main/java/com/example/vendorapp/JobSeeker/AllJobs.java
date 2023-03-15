package com.example.vendorapp.JobSeeker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.vendorapp.Adapter.JobAdapter;
import com.example.vendorapp.Adapter.Jobsadapter;
import com.example.vendorapp.Admin.AdminActivity;
import com.example.vendorapp.Login;
import com.example.vendorapp.Models.jobModel;
import com.example.vendorapp.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class AllJobs extends AppCompatActivity {
    DatabaseReference databaseReference;
    String adminID;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    String Id1;

    RecyclerView recyclerView;
    Jobsadapter jobsadapter;

    Toolbar toolbar;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_jobs);

        hooks();

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        Id1 = user.getUid();

        setSupportActionBar(toolbar);
        toolbar.setTitle("Job Postings");

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<jobModel> options =
                new FirebaseRecyclerOptions.Builder<jobModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("Jobs").orderByChild("dateposted")
                                .limitToLast(100), jobModel.class)
                        .build();

        jobsadapter = new Jobsadapter(getApplicationContext(), options);
        recyclerView.setAdapter(jobsadapter);

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
                    case R.id.logout2:
                        firebaseAuth.signOut();
                        makeOffline();
                        // checkUser();
                        startActivity(new Intent(AllJobs.this, Login.class));
                        break;
                    default:
                        break;
                }

            }
        });

    }


    private void makeOffline() {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("online", "false");

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        databaseReference.child(user.getUid()).updateChildren(hashMap);
    }

    private void hooks() {
        recyclerView = findViewById(R.id.jobs);
        toolbar = findViewById(R.id.toolBar);
        bottomNavigationView = findViewById(R.id.bottomNavView2);
    }


    @Override
    public void onStart() {
        super.onStart();
        jobsadapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        jobsadapter.stopListening();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);
        MenuItem item = menu.findItem(R.id.search2);
        SearchView searchView = (SearchView) item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                txtSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                txtSearch(query);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void txtSearch(String str) {
        FirebaseRecyclerOptions<jobModel> options =
                new FirebaseRecyclerOptions.Builder<jobModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("Jobs").orderByChild("jobtype")
                                .startAt(str).endAt(str + "~"), jobModel.class)
                        .build();

        jobsadapter = new Jobsadapter(getApplicationContext(), options);

        jobsadapter.startListening();
        recyclerView.setAdapter(jobsadapter);

    }

}