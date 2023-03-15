package com.example.vendorapp.Adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vendorapp.Models.jobModel;
import com.example.vendorapp.R;
import com.example.vendorapp.jActivities.jApplyActivity;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Calendar;
import java.util.List;

public class Jobsadapter extends FirebaseRecyclerAdapter<jobModel, Jobsadapter.ViewHolder> {
    Context context;
    List<jobModel> jobModelList;
    FirebaseAuth mAuth;

    public Jobsadapter(Context context, FirebaseRecyclerOptions<jobModel> options) {
        super(options);
        this.context = context;
    }


    @NonNull
    @Override
    public Jobsadapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.alljobrow, parent, false);
        return new Jobsadapter.ViewHolder(v);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder viewHolder, int i, @NonNull jobModel jobModel) {
        viewHolder.tvtitle.setText(jobModel.getTitle());

        viewHolder.tvtitle.setTextColor(context.getResources().getColor(R.color.lightblue));
        viewHolder.tvtitle.setText(jobModel.getTitle());
        viewHolder.tvdepartment.setText(jobModel.getDepartment());
        viewHolder.tvjtype.setText(jobModel.getJobtype());
        viewHolder.tveducationlvl.setText(jobModel.getDutystation());
        viewHolder.tvage.setText(jobModel.getDeadline());

        //convert timestamp to proper date format
        String datePosted = jobModel.getTimestamp();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.parseLong(datePosted));
        String date = DateFormat.format("dd/MM/yyyy hh:mm a", calendar).toString();

        viewHolder.tvdateposted.setText(date);

        viewHolder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, jApplyActivity.class);
                intent.putExtra("Adminid", jobModel.getUid());
                intent.putExtra("jSeekerid", jobModel.getTimestamp());
                intent.putExtra("title", jobModel.getTitle());
                intent.putExtra("department", jobModel.getDepartment());
                intent.putExtra("payment", jobModel.getPayment());
                intent.putExtra("experience", String.valueOf(jobModel.getExperience()));
                intent.putExtra("jtype", jobModel.getJobtype());
                intent.putExtra("dutystation", jobModel.getDutystation());
                intent.putExtra("traits", jobModel.getSpecialskill());
                intent.putExtra("dateposted", date);
                intent.putExtra("deadline", jobModel.getDeadline());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvtitle, tvpayment, tvexperience, tvjtype, tveducationlvl, tvtraits, tvage, tvdepartment, tvdateposted;
        Button applybtn;
        CardView parent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvtitle = itemView.findViewById(R.id.txtTitle);
            tvpayment = itemView.findViewById(R.id.txtpayment);
            tvexperience = itemView.findViewById(R.id.txtexperience);
            tvjtype = itemView.findViewById(R.id.txtjtype);
            tveducationlvl = itemView.findViewById(R.id.txteduc);
            tvtraits = itemView.findViewById(R.id.txttrait);
            tvage = itemView.findViewById(R.id.txtAge);
            tvdateposted = itemView.findViewById(R.id.txtpostdate);
            tvdepartment = itemView.findViewById(R.id.txtDepartment);
            applybtn = itemView.findViewById(R.id.applybtn);
            parent = itemView.findViewById(R.id.parent2);
        }
    }
}
