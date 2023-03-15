package com.example.vendorapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vendorapp.JobSeeker.ViewJobPostings;
import com.example.vendorapp.R;
import com.example.vendorapp.Models.ShopModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;

public class AdminAdapter extends FirebaseRecyclerAdapter<ShopModel, AdminAdapter.ViewHolder> {
    String imageUri;
    String open;
    Context context;

    public AdminAdapter(@NonNull FirebaseRecyclerOptions<ShopModel> options) {
        super(options);
    }

    public AdminAdapter(Context context, FirebaseRecyclerOptions<ShopModel> options) {
        super(options);
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adminrow, parent, false);
        return new ViewHolder(view);
    }


    @Override
    protected void onBindViewHolder(ViewHolder viewHolder, int i, ShopModel shopModel) {

        viewHolder.tvname.setText(shopModel.getName());
        viewHolder.tvlocation.setText(shopModel.getEnterprise());
        open = shopModel.getOnline();
        if (open.equals("true")) {
            viewHolder.tvstatus.setVisibility(View.INVISIBLE);
            viewHolder.imgonline.setVisibility(View.VISIBLE);
        } else {
            viewHolder.tvstatus.setVisibility(View.VISIBLE);
            viewHolder.imgonline.setVisibility(View.INVISIBLE);
        }

        imageUri = null;
        imageUri = shopModel.getImage();
        try {
            //Picasso.with().load(imageUri).into(viewHolder.imageView);
            Picasso.get().load(imageUri).into(viewHolder.imageView);
        } catch (Exception e) {
            viewHolder.imageView.setImageResource(R.drawable.profile);
        }

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ViewJobPostings.class);
                intent.putExtra("Adminid", shopModel.getUid());
                intent.putExtra("Online", shopModel.getOnline());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView, imgonline;
        TextView tvname, tvlocation, tvstatus;

        public ViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.adminimg);
            imgonline = itemView.findViewById(R.id.imglocation);
            tvname = itemView.findViewById(R.id.orgname);
            tvlocation = itemView.findViewById(R.id.location);
            tvstatus = itemView.findViewById(R.id.openclose);
        }

    }

}
