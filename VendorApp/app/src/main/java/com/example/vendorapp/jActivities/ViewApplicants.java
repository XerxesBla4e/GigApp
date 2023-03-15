package com.example.vendorapp.jActivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.vendorapp.Adapter.ApplicantAdapter;
import com.example.vendorapp.R;
import com.example.vendorapp.Models.ApplicationsModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ViewApplicants extends AppCompatActivity {
    RecyclerView recyclerView;
    ApplicantAdapter applicantAdapter;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    DatabaseReference xerRef;
    String Id1;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_applicants);

        hooks();

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        Id1 = user.getUid();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        FirebaseRecyclerOptions<ApplicationsModel> options =
                new FirebaseRecyclerOptions.Builder<ApplicationsModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("Users").child(Id1)
                                .child("Applications").orderByChild("experience").limitToLast(20), ApplicationsModel.class)
                        .build();

        applicantAdapter = new ApplicantAdapter(getApplicationContext(), options);
        recyclerView.setAdapter(applicantAdapter);
        applicantAdapter.notifyDataSetChanged();
    }

    private void hooks() {
        recyclerView = findViewById(R.id.applicantrecview);
    }

    @Override
    public void onStart() {
        super.onStart();
        applicantAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        applicantAdapter.stopListening();
    }

    public void backgo(View view) {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}