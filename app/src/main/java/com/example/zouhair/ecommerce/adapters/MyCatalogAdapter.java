package com.example.zouhair.ecommerce.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.zouhair.ecommerce.ProductInfo;
import com.example.zouhair.ecommerce.R;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.example.zouhair.ecommerce.model.Product;

import java.io.ByteArrayOutputStream;
import java.util.LinkedList;

public class MyCatalogAdapter extends RecyclerView.Adapter<MyCatalogAdapter.MyViewHolder>
{

    private LinkedList<Product> myProducts;
    static Context context;


    // Provide a suitable constructor (depends on the kind of dataset)
    public MyCatalogAdapter(LinkedList<Product> myProducts, Context context)
    {
        this.myProducts = new LinkedList<Product>() ;
        this.myProducts.addAll(myProducts);
        this.context=context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        // create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.catalogue_item_layout, parent, false);
        MyViewHolder vh = new MyViewHolder(itemLayoutView);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position)
    {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.id.setText(myProducts.get(position).getId());
        holder.designation.setText(myProducts.get(position).getDesignation());
        holder.description.setText(myProducts.get(position).getDescription());
        holder.price.setText(String.valueOf(myProducts.get(position).getPrice()));
        // Reference to an image file in Cloud Storage
        StorageReference storageReference = FirebaseStorage.getInstance().getReference(myProducts.get(position).getImage());
        // Download directly from StorageReference using Glide
        // (See MyAppGlideModule for Loader registration)

        Glide.with(context /* context */).load(storageReference).into(holder.image);
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

        public TextView designation;
        public TextView description;
        public ImageView image;
        public TextView price;
        public TextView id;
        public MyViewHolder(View itemLayoutView)
        {
            super(itemLayoutView);
            id=itemLayoutView.findViewById(R.id.id);
            designation=itemLayoutView.findViewById(R.id.Designation);
            description=itemLayoutView.findViewById(R.id.Description);
            price=itemLayoutView.findViewById(R.id.price);
            image=  itemLayoutView.findViewById(R.id.image);
            itemLayoutView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v)
        {
            Activity activity = (Activity)context;
            FragmentActivity myContext=(FragmentActivity) activity;
            ProductInfo pi= new ProductInfo();
            pi.setContext(context);
            Bundle args=new Bundle();
            args.putString("id",id.getText().toString());
            args.putString("description",description.getText().toString());
            args.putString("designation",designation.getText().toString());
            args.putFloat("price",Float.valueOf(price.getText().toString()));
            Bitmap myimage=((BitmapDrawable)image.getDrawable()).getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            myimage.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] imageInByte = stream.toByteArray();
            args.putByteArray("image",imageInByte);
            pi.setArguments(args);
            pi.show(myContext.getSupportFragmentManager(),"hhh");

        }
    }
}
