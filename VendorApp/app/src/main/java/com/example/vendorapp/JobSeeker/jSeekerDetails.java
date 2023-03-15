package com.example.vendorapp.JobSeeker;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
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
//new feature
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.util.Properties;

public class jSeekerDetails extends AppCompatActivity implements View.OnClickListener {
    RecyclerView recyclerView;
    ApplicantAdapter applicantAdapter;

    private static final String TAG = "Job Seeker Details";

    TextView status, appid3, name2;
    TextView title, payment, experience, jtype, educlvl, trait, age;
    String applicationID;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;

    public String adminID;
    public String currentID;
    public String telno, name1, id, jseekrlatitude, jseekrlongitude, adminlongitude, adminlatitude;

    ImageView find2, phone2, bk2, approve, delete;
    public String email;
    public String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jseeker_details);

        hooks();

        firebaseAuth = FirebaseAuth.getInstance();
        phone2.setOnClickListener(this);
        bk2.setOnClickListener(this);
        find2.setOnClickListener(this);
        approve.setOnClickListener(this);
        delete.setOnClickListener(this);
        /*
        firebaseUser = firebaseAuth.getCurrentUser();
        shopID = firebaseUser.getUid();
*/
        if (getIntent() != null) {
            applicationID = getIntent().getStringExtra("applicationID");
            adminID = getIntent().getStringExtra("adminID");
            currentID = getIntent().getStringExtra("position");
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
                                telno = userDetsModel.getPhone();
                                jseekrlatitude = userDetsModel.getLatitude();
                                jseekrlongitude = userDetsModel.getLongitude();
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

        FirebaseDatabase.getInstance().getReference("Users").child(adminID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                UserDetsModel userDetsModel = snapshot.getValue(UserDetsModel.class);
                if (userDetsModel != null) {
                    adminlongitude = userDetsModel.getLongitude();
                    adminlatitude = userDetsModel.getLatitude();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Error:" + error.getMessage(), Toast.LENGTH_SHORT).show();
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

        name2 = findViewById(R.id.name1);
        status = findViewById(R.id.status1);

        appid3 = findViewById(R.id.app1);

        find2 = findViewById(R.id.find2);
        phone2 = findViewById(R.id.phone2);
        bk2 = findViewById(R.id.bk2);

        approve = findViewById(R.id.approve);
        delete = findViewById(R.id.delete);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.find2:
                locateUser();
                break;
            case R.id.phone2:
                makeCall();
                break;
            case R.id.bk2:
                onBackPressed();
                break;
            case R.id.approve:
                updateStatus();
                break;
            case R.id.delete:
                rejectApplicant();
                break;
            default:
                break;
        }
    }

    private void rejectApplicant() {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("approvedstatus", "rejected");
        hashMap.put("Status", "rejected");

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        databaseReference.child(adminID).child("Applications").child(applicationID).updateChildren(hashMap);

        message = "your application has been approved";
        sendMail(message, email);

        FirebaseDatabase.getInstance().getReference("Users")
                .child(currentID).removeValue();
        Intent i = new Intent(getApplicationContext(), AdminActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }

    private void sendMail(String message, String email) {
        final String myemail = "Mugashab@gmail.com";
        final String mypass = "Xerxescodes54#";

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        Session session = Session.getInstance(properties,
                new javax.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(myemail, mypass);
                    }
                });
        try {
            Message message1 = new MimeMessage(session);
            message1.setFrom(new InternetAddress(myemail));
            message1.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message1.setSubject("Approval Status Update");
            message1.setText(message);
            Transport.send(message1);
            Toast.makeText(getApplicationContext(), "Email Sent Successfully", Toast.LENGTH_SHORT).show();
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }
    
    private void updateStatus() {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("approvedstatus", "approved");
        hashMap.put("Status", "approved");

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        databaseReference.child(adminID).child("Applications").child(applicationID).updateChildren(hashMap);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void makeCall() {
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + Uri.encode(telno))));
        Toast.makeText(getApplicationContext(), "" + telno, Toast.LENGTH_SHORT).show();
    }

    private void locateUser() {
        String address2 = "https://maps.google.com/maps?saddr" + jseekrlatitude + "," + jseekrlongitude + "&saddr=" + adminlatitude + "," + adminlongitude;
        Intent xerx = new Intent(Intent.ACTION_VIEW, Uri.parse(address2));
        startActivity(xerx);
    }
}