package com.example.vendorapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vendorapp.JobSeeker.jSeekerDetails;
import com.example.vendorapp.R;

import com.example.vendorapp.Models.ApplicationsModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class ApplicantAdapter extends FirebaseRecyclerAdapter<ApplicationsModel, ApplicantAdapter.ViewHolder> {

    Context context;
    //  FirebaseUser user;
    //  FirebaseAuth firebaseAuth;

    public ApplicantAdapter(Context context, FirebaseRecyclerOptions<ApplicationsModel> options) {
        super(options);
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.applicantrow, parent, false);
        return new ViewHolder(view);
    }


    @Override
    protected void onBindViewHolder(@NonNull ViewHolder viewHolder, int i, @NonNull ApplicationsModel applicationsModel) {

        viewHolder.tvname.setText(applicationsModel.getApplicationID());

        String status = applicationsModel.getStatus();

        if (status.equals("Pending")) {
            viewHolder.tvstatus.setText(status);
            viewHolder.tvstatus.setTextColor(context.getResources().getColor(R.color.greyish));
        } else if (status.equals("approved")) {
            viewHolder.tvstatus.setText(status);
            viewHolder.tvstatus.setTextColor(context.getResources().getColor(R.color.green_pie));
        }else{
            viewHolder.tvstatus.setText(status);
            viewHolder.tvstatus.setTextColor(context.getResources().getColor(R.color.red));
        }

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, jSeekerDetails.class);
                intent.putExtra("applicationID", applicationsModel.getApplicationID());
                intent.putExtra("adminID", applicationsModel.getAppliedTo());
                intent.putExtra("position", getRef(viewHolder.getAdapterPosition()).getKey());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvname, tvstatus;

        public ViewHolder(View itemView) {
            super(itemView);

            tvname = itemView.findViewById(R.id.name);
            tvstatus = itemView.findViewById(R.id.cltstatus);
        }

    }

}
