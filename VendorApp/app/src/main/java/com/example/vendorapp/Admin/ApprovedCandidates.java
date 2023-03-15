package com.example.vendorapp.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.vendorapp.Adapter.ApprovedAdapter;
import com.example.vendorapp.R;
import com.example.vendorapp.Models.ApprovedModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class ApprovedCandidates extends AppCompatActivity {
    RecyclerView recyclerView;
    ApprovedAdapter approvedAdapter;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    String Id1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approved_candidates);


        hooks();

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        Id1 = user.getUid();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        FirebaseRecyclerOptions<ApprovedModel> options =
                new FirebaseRecyclerOptions.Builder<ApprovedModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("Users").child(Id1)
                                .child("Applications").orderByChild("approvedstatus").
                                equalTo("approved"),ApprovedModel.class)
                        .build();

        approvedAdapter = new ApprovedAdapter(getApplicationContext(), options);
        recyclerView.setAdapter(approvedAdapter);
        approvedAdapter .notifyDataSetChanged();
    }

    private void hooks() {
        recyclerView = findViewById(R.id.candidates);
    }

    @Override
    public void onStart() {
        super.onStart();
        approvedAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        approvedAdapter.stopListening();
    }
}
