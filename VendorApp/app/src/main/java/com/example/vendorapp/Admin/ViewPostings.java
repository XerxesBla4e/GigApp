package com.example.vendorapp.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vendorapp.Adapter.JobAdapter;
import com.example.vendorapp.Adapter.Jobsadapter;
import com.example.vendorapp.Adapter.ViewAdapter;
import com.example.vendorapp.Models.jobModel;
import com.example.vendorapp.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ViewPostings extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    String Id1;

    RecyclerView recyclerView;
    ViewAdapter viewAdapter;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_postings);

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
                        .setQuery(FirebaseDatabase.getInstance().getReference("Users")
                                .child(firebaseAuth.getUid()).child("Postings"), jobModel.class)
                        .build();

        viewAdapter = new ViewAdapter(getApplicationContext(), options);
        recyclerView.setAdapter(viewAdapter);
        viewAdapter.notifyDataSetChanged();
    }

    private void hooks() {
        recyclerView = findViewById(R.id.admin2);
        toolbar = findViewById(R.id.toolBar);
    }

    @Override
    public void onStart() {
        super.onStart();
        viewAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        viewAdapter.stopListening();
    }
}