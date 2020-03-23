package com.example.zouhair.ecommerce.adapters;


import android.content.Context;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.zouhair.ecommerce.R;
import com.example.zouhair.ecommerce.model.OrderLine;
import com.example.zouhair.ecommerce.model.TinyDB;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


import java.util.ArrayList;

import static android.view.View.GONE;

public class MyPanierAdapter extends RecyclerView.Adapter<MyPanierAdapter.MyViewHolder>
{


    private ArrayList<OrderLine> myProducts;
    static Context context;


    // Provide a suitable constructor (depends on the kind of dataset)
    public MyPanierAdapter(ArrayList<OrderLine> myP, Context context)
    {
        this.myProducts =new ArrayList<OrderLine>();
        this.myProducts.addAll(myP);
        this.context=context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyPanierAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        // create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.panier_adapter, parent, false);
        MyPanierAdapter.MyViewHolder vh = new MyPanierAdapter.MyViewHolder(itemLayoutView,mListener);
        return vh;
    }


    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyPanierAdapter.MyViewHolder holder, int position)
    {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Bitmap bmp = BitmapFactory.decodeByteArray(myProducts.get(position).getImage(), 0, myProducts.get(position).getImage().length);
        ((ImageView) holder.image).setImageBitmap(bmp);

        holder.id.setText(myProducts.get(position).getId());
        holder.designation.setText(myProducts.get(position).getDesignation());
        holder.Quantity.setText(String.valueOf(myProducts.get(position).getQuantity())+" Produits");
        holder.price.setText(String.valueOf(myProducts.get(position).getPrice()*myProducts.get(position).getQuantity())+" MAD");
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
    private onItemClickListener mListener;
    public interface onItemClickListener {
          void onItemClick(int position);
    }
    public void setOnItemClickListener(onItemClickListener listener)
    {
        mListener=listener;
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        // each data item is just a string in this case

        public TextView id;
        public TextView designation;
        public TextView Quantity;
        public TextView price;
        public ImageView image;
        public Button supprimer;
        LinearLayout layout;

        public MyViewHolder(View itemLayoutView, final onItemClickListener listener)
        {
            super(itemLayoutView);
            id=itemLayoutView.findViewById(R.id.id);
            price=itemLayoutView.findViewById(R.id.price);
            Quantity=  itemLayoutView.findViewById(R.id.quantity);
            designation=itemLayoutView.findViewById(R.id.designation);
            image=itemLayoutView.findViewById(R.id.PAimage);
            supprimer=itemLayoutView.findViewById(R.id.supprimer);
            supprimer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null)
                    {
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION)
                        {
                            Log.v("jsooo","clicked");
                            TinyDB tinyDB =new TinyDB(context);
                            FirebaseUser user  = FirebaseAuth.getInstance().getCurrentUser();
                            ArrayList<Object> order=tinyDB.getListObject(user.getUid(),OrderLine.class);
                            for (int i=0;i<order.size();i++)
                            {
                                OrderLine o=(OrderLine)order.get(i);
                                String s=id.getText().toString();
                                int jsooo = Log.v("jsooo", o.getId() + " and " + s);
                                if(o.getId().compareTo(s)==0)
                                {
                                    order.remove(i);
                                }
                            }
                            tinyDB.putListObject(user.getUid(),order);
                            listener.onItemClick(position);
                        }
                    }
                }
            });
            layout =(LinearLayout)itemLayoutView.findViewById(R.id.panierAdapter);
        }
        public void onClick(View v)
        {
            if(v.getId()==R.id.supprimer)
            {
                Log.v("jsooo","clicked");
                TinyDB tinyDB =new TinyDB(context);
                FirebaseUser user  = FirebaseAuth.getInstance().getCurrentUser();
                ArrayList<Object> order=tinyDB.getListObject(user.getUid(),OrderLine.class);
                for (int i=0;i<order.size();i++)
                {
                    OrderLine o=(OrderLine)order.get(i);
                    String s=id.getText().toString();
                    int jsooo = Log.v("jsooo", o.getId() + " and " + s);
                    if(o.getId().compareTo(s)==0)
                    {
                        order.remove(i);
                    }
                }
                tinyDB.putListObject(user.getUid(),order);
                layout.setVisibility(GONE);
            }

        }
    }
}
