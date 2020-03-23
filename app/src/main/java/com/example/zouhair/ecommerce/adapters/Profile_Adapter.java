package com.example.zouhair.ecommerce.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zouhair.ecommerce.R;
import com.example.zouhair.ecommerce.model.profile;

import java.util.LinkedList;

public class Profile_Adapter extends RecyclerView.Adapter<Profile_Adapter.MyViewHolder>{
    private LinkedList<profile> myProducts;
    static Context context;


    // Provide a suitable constructor (depends on the kind of dataset)
    public Profile_Adapter(LinkedList<profile> myProducts, Context context)
    {
        this.myProducts = new LinkedList<profile>() ;
        this.myProducts.addAll(myProducts);
        this.context=context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public Profile_Adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        // create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.profile_adapter, parent, false);
        Profile_Adapter.MyViewHolder vh = new Profile_Adapter.MyViewHolder(itemLayoutView);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(Profile_Adapter.MyViewHolder holder, int position)
    {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.fullname.setText(myProducts.get(position).getFullname());
        holder.job.setText(myProducts.get(position).getJob());
        holder.email.setText(myProducts.get(position).getEmail());
        holder.contact.setText(myProducts.get(position).getContact());
        holder.facebook.setText(myProducts.get(position).getFacebook());
        holder.twitter.setText(myProducts.get(position).getTwitter());
        // Reference to an image file in Cloud Storage
        //StorageReference storageReference = FirebaseStorage.getInstance().getReference(myProducts.get(position).getImage());
        // Download directly from StorageReference using Glide
        // (See MyAppGlideModule for Loader registration)
        //Glide.with(context /* context */).load(storageReference).into(holder.image);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount()
    {
        return myProducts.size();
    }


// Provide a reference to the views for each data item
// Complex data items may need more than one view per item, and
// you provide access to all the views for a data item in a view holder

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        // each data item is just a string in this case

        public TextView fullname;
        public TextView job;
        public ImageView image;
        public TextView email;
        public TextView contact;
        public TextView facebook;
        public TextView twitter;
        public MyViewHolder(View itemLayoutView)
        {
            super(itemLayoutView);
            fullname=itemLayoutView.findViewById(R.id.full_name);
            job=itemLayoutView.findViewById(R.id.job);
            email=itemLayoutView.findViewById(R.id.email);
            contact=itemLayoutView.findViewById(R.id.contact);
            facebook=itemLayoutView.findViewById(R.id.facebook);
            twitter=itemLayoutView.findViewById(R.id.twitter);
            itemLayoutView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v)
        {

        }
    }
}
