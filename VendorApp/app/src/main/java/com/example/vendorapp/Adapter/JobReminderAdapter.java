package com.example.vendorapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vendorapp.Models.ApplicationsModel;
import com.example.vendorapp.R;
import com.example.vendorapp.jActivities.MyDetails;

import java.util.List;

public class JobReminderAdapter extends RecyclerView.Adapter<JobReminderAdapter.ViewHolder> {

    private static final String TAG = "AdapterClass";
    Context context;
    List<ApplicationsModel> applicationsModelsList;

    public JobReminderAdapter(Context context, List<ApplicationsModel> applicationsModels) {
        this.context = context;
        this.applicationsModelsList = applicationsModels;
    }

    @NonNull
    @Override

    public JobReminderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.jobremrow, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JobReminderAdapter.ViewHolder holder, int position) {
        ApplicationsModel applicationsModel = applicationsModelsList.get(position);

        String status = applicationsModel.getStatus();

        if (status.equals("approved")) {
            holder.tvstatus.setText(status);
            holder.tvstatus.setTextColor(context.getResources().getColor(R.color.green_pie));
        } else if (status.equals("cancelled")) {
            holder.tvstatus.setText(status);
            holder.tvstatus.setTextColor(context.getResources().getColor(R.color.red));
        }
        // Log.d(TAG,""+applicationsModel.getStatus());
        holder.tvstatus.setText(applicationsModel.getStatus());
        holder.tvid.setText(applicationsModel.getApplicationID());
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MyDetails.class);
                intent.putExtra("applicationID", applicationsModel.getApplicationID());
                intent.putExtra("adminID", applicationsModel.getAppliedTo());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return applicationsModelsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvid, tvstatus;
        CardView parent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvid = itemView.findViewById(R.id.name1);
            tvstatus = itemView.findViewById(R.id.cltstatus);
            parent = itemView.findViewById(R.id.parent);
        }
    }
}
