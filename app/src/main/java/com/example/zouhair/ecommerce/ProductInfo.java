package com.example.zouhair.ecommerce;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.example.zouhair.ecommerce.model.OrderLine;
import com.example.zouhair.ecommerce.model.Product;
import com.example.zouhair.ecommerce.model.TinyDB;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Set;

public class ProductInfo extends DialogFragment implements View.OnClickListener
{
    String id;
    String designation;
    float price;
    byte[] imageInByte;
    byte[] image;
    NumberPicker npicker;
    Context context;
    private FirebaseFirestore db;
    Button ok,cancel;
    FirebaseUser user ;

    public ProductInfo newInstance(Context context)
    {
        this.context = context;
        ProductInfo f = new ProductInfo();
        return f;

    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        user = FirebaseAuth.getInstance().getCurrentUser();
        super.onCreate(savedInstanceState);
        db = FirebaseFirestore.getInstance();
        id= getArguments().getString("id");
        designation = getArguments().getString("designation");
        price = getArguments().getFloat("price");
        imageInByte = getArguments().getByteArray("image");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.catalog_item_detail_layout, container, false);

        View tv0 = v.findViewById(R.id.idDB);
        ((TextView) tv0).setText(id);



        View tv2 = v.findViewById(R.id.designationDB);
        ((TextView) tv2).setText(designation);

        View tv3 = v.findViewById(R.id.priceDB);
        ((TextView) tv3).setText(String.valueOf(price));

        View tv4 = v.findViewById(R.id.imageDB);
        Bitmap bmp = BitmapFactory.decodeByteArray(imageInByte, 0, imageInByte.length);
        ((ImageView) tv4).setImageBitmap(bmp);
        image =imageInByte;

        npicker = (NumberPicker) v.findViewById(R.id.quantity);
        npicker.setMaxValue(10);
        npicker.setMinValue(0);
        ok = (Button) v.findViewById(R.id.ok);
        cancel=(Button) v.findViewById(R.id.cancel);
        ok.setOnClickListener(this);
        cancel.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View v)
    {
        System.out.println("1111111111111111111111");
        if (v.getId() == R.id.ok)
        {
            TinyDB tinyDB =new TinyDB(context);
            OrderLine orderLine = new OrderLine(designation,npicker.getValue(),price,id,image);
            ArrayList<Object> order=tinyDB.getListObject(user.getUid(),OrderLine.class);
            Log.v("jsooo",String.valueOf(order.size()));
            tinyDB.putListObject(user.getUid(),isExiste(order,orderLine));
            this.dismiss();
        }
        else if(v.getId() == R.id.cancel)
        {
            this.dismiss();
        }
    }
    public ArrayList<Object> isExiste(ArrayList<Object> order,OrderLine orderLine)
    {
        for (int i=0;i<order.size();i++)
        {
            OrderLine o=(OrderLine) order.get(i);
            if(o.getDesignation().equals(orderLine.getDesignation()))
            {
                ((OrderLine) order.get(i)).incrementQuantity(orderLine.getQuantity());
                return order;
            }
        }
        order.add(orderLine);
        return order;
    }
    public void setContext(Context context)
    {
        this.context = context;
    }
}

