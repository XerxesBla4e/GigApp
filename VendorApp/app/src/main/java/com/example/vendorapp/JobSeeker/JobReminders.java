package com.example.vendorapp.JobSeeker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.vendorapp.Adapter.JobReminderAdapter;
import com.example.vendorapp.Models.ApplicationsModel;
import com.example.vendorapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class JobReminders extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "JobReminders Activity";
    List<ApplicationsModel> applicationsModelList;
    FirebaseAuth firebaseAuth;
    RecyclerView recyclerView;
    ImageView back;
    public JobReminderAdapter jobReminderAdapter;
    private String approvedstatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_reminders);


        applicationsModelList = new ArrayList<>();
        recyclerView = findViewById(R.id.remrecview);
        firebaseAuth = FirebaseAuth.getInstance();
        back = findViewById(R.id.bk2);
        back.setOnClickListener(this);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                applicationsModelList.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    String uid = "" + snapshot1.getRef().getKey();

                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users")
                            .child(uid).child("Applications");
                    ref.orderByChild("appliedBy").equalTo(firebaseAuth.getUid())
                            .addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if (snapshot.exists()) {
                                        for (DataSnapshot ds : snapshot.getChildren()) {
                                            ApplicationsModel applicationsModel = ds.getValue(ApplicationsModel.class);

                                            Log.d(TAG, "" + applicationsModel.getApprovedstatus());
                                            applicationsModelList.add(applicationsModel);
                                        }
                                        jobReminderAdapter = new JobReminderAdapter(getApplicationContext(), applicationsModelList);
                                        recyclerView.setAdapter(jobReminderAdapter);
                                        jobReminderAdapter.notifyDataSetChanged();

                                    }
                                }

                                @Override

                                public void onCancelled(@NonNull DatabaseError error) {
                                    Toast.makeText(getApplicationContext(), "" + error.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
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
}