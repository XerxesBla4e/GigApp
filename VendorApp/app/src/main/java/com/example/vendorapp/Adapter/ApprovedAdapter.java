package com.example.vendorapp.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vendorapp.Admin.AdminActivity;
import com.example.vendorapp.R;
import com.example.vendorapp.jActivities.PayActivity;
import com.example.vendorapp.Models.ApprovedModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ApprovedAdapter extends FirebaseRecyclerAdapter<ApprovedModel, ApprovedAdapter.ViewHolder> {

    Context context;
    FirebaseUser user;
    FirebaseAuth firebaseAuth;

    public ApprovedAdapter(@NonNull FirebaseRecyclerOptions<ApprovedModel> options) {
        super(options);
    }

    public ApprovedAdapter(Context context, FirebaseRecyclerOptions<ApprovedModel> options) {
        super(options);
        this.context = context;
    }


    @Override
    protected void onBindViewHolder(@NonNull ViewHolder viewHolder, int i, @NonNull ApprovedModel approvedModel) {
        viewHolder.tvname.setText(approvedModel.getApplicationID());

        String status = approvedModel.getApprovedstatus();

        if (status.equals("approved")) {
            viewHolder.tvstatus.setText(status);
            viewHolder.tvstatus.setTextColor(context.getResources().getColor(R.color.green_pie));
        } else if (status.equals("cancelled")) {
            viewHolder.tvstatus.setText(status);
            viewHolder.tvstatus.setTextColor(context.getResources().getColor(R.color.red));
        }
        viewHolder.imgpay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, PayActivity.class);
                i.putExtra("adminID", approvedModel.getAppliedTo());
                i.putExtra("jobseekerID", approvedModel.getAppliedBy());
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });
        viewHolder.imgdel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(viewHolder.tvname.getContext());
                builder.setTitle("Are You Sure");
                builder.setMessage("No Return After this");
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        /*

                         DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");
                databaseReference.child("Applications").child("appliedBy").child(getRef(i).getKey()).removeValue();

                Intent i = new Intent(context, AdminActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
                         */
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(viewHolder.tvname.getContext(), "Deletion Canceled", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.approvedrow, parent, false);
        return new ApprovedAdapter.ViewHolder(view);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvname, tvstatus;
        ImageView imgdel, imgpay;

        public ViewHolder(View itemView) {
            super(itemView);

            tvname = itemView.findViewById(R.id.name1);
            tvstatus = itemView.findViewById(R.id.cltstatus1);
            imgdel = itemView.findViewById(R.id.delete1);
            imgpay = itemView.findViewById(R.id.pay);
        }
    }
}
