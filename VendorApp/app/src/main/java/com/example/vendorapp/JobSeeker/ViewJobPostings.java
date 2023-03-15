package com.example.vendorapp.JobSeeker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vendorapp.Adapter.JobAdapter;
import com.example.vendorapp.R;
import com.example.vendorapp.Models.ShopModel;
import com.example.vendorapp.Models.UserDetsModel;
import com.example.vendorapp.Models.jobModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ViewJobPostings extends AppCompatActivity implements View.OnClickListener {

    DatabaseReference databaseReference;
    String adminID;
    TextView name, email, opclse;
    ImageView call, find;
    String adminPhone, adminlongitude, adminlatitude, clientlongitude, clientlatitude;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    String Id1;

    RecyclerView recyclerView;
    JobAdapter jobAdapter;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_job_postings);

        hooks();

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        Id1 = user.getUid();

        call.setOnClickListener(this);
        find.setOnClickListener(this);

        setSupportActionBar(toolbar);
        toolbar.setTitle("Job Postings");

        if (getIntent() != null) {
            adminID = getIntent().getStringExtra("Adminid");
        }

        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        databaseReference.child(adminID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                ShopModel shopModel = snapshot.getValue(ShopModel.class);

                if (shopModel != null) {
                    name.setText(shopModel.getName());
                    email.setText(shopModel.getEmail());
                    adminPhone = shopModel.getPhone();
                    adminlongitude = shopModel.getLongitude();
                    adminlatitude = shopModel.getLatitude();

                    if (shopModel.getOnline().equals("true")) {
                        opclse.setVisibility(View.INVISIBLE);
                    } else {

                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<jobModel> options =
                new FirebaseRecyclerOptions.Builder<jobModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference("Users").child(adminID).child("Postings"), jobModel.class)
                        .build();

        jobAdapter = new JobAdapter(getApplicationContext(), options);
        recyclerView.setAdapter(jobAdapter);
        jobAdapter.notifyDataSetChanged();

        FirebaseDatabase.getInstance().getReference("Users").child(Id1).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                UserDetsModel userDetsModel = snapshot.getValue(UserDetsModel.class);
                if (userDetsModel != null) {
                    clientlongitude = userDetsModel.getLongitude();
                    clientlatitude = userDetsModel.getLatitude();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Error:" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void hooks() {
        name = findViewById(R.id.name1);
        email = findViewById(R.id.email1);
        opclse = findViewById(R.id.openclse);
        recyclerView = findViewById(R.id.admins);
        call = findViewById(R.id.phone2);
        find = findViewById(R.id.find2);
        toolbar = findViewById(R.id.toolBar);
    }


    @Override
    public void onStart() {
        super.onStart();
        jobAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        jobAdapter.stopListening();
    }

   /* public boolean onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search, menu);
        MenuItem item = menu.findItem(R.id.search2);

        return super.onCreateOptionsMenu(menu);
    }*/

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.phone2:
                dialPhone();
                break;
            case R.id.find2:
                openMap();
                break;
        }
    }

    private void openMap() {
        String address2 = "https://maps.google.com/maps?saddr" + clientlatitude + "," + clientlongitude + "&saddr=" + adminlatitude + "," + adminlongitude;
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(address2));
        startActivity(intent);
    }

    private void dialPhone() {
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + Uri.encode(adminPhone))));
        Toast.makeText(getApplicationContext(), "" + adminPhone, Toast.LENGTH_SHORT).show();
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
                        .setQuery(FirebaseDatabase.getInstance().getReference("Users").child(adminID).child("Postings").orderByChild("jobtype")
                                .startAt(str).endAt(str + "~"), jobModel.class)
                        .build();

        jobAdapter = new JobAdapter(getApplicationContext(), options);

        jobAdapter.startListening();
        recyclerView.setAdapter(jobAdapter);

    }

    public void backgo(View view) {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}