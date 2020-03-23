package com.example.zouhair.ecommerce;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.zouhair.ecommerce.adapters.MyPanierAdapter;
import com.example.zouhair.ecommerce.model.OrderLine;
import com.example.zouhair.ecommerce.model.TinyDB;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Panier extends FragmentActivity implements View.OnClickListener
{
    private RecyclerView mRecyclerView;
    public Button button;
    private RecyclerView.Adapter myAdapter;
    private RecyclerView.LayoutManager layoutManager;
    ArrayList<OrderLine> products=new ArrayList<>();
    public ProgressDialog mProgressDialog;
    private FirebaseFirestore db;
    private  FirebaseUser user ;
    public TextView id;
    public Button supprimer;
    private static Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panier);
        user = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseFirestore.getInstance();
        id=(TextView) findViewById(R.id.id);

        button=(Button)findViewById(R.id.commander);
        button.setOnClickListener(this);
        mRecyclerView= (RecyclerView)findViewById(R.id.panierRecycler);
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(Panier.this);
        mRecyclerView.setLayoutManager(layoutManager);
        // specify an adapter (see also next example)
        getData1();
    }
    void getData1()
    {
        TinyDB tinyDB =new TinyDB(getApplicationContext());
        ArrayList<Object> order=tinyDB.getListObject(user.getUid(),OrderLine.class);
        ArrayList<OrderLine> prod=new ArrayList<>();
        for(Object obj :order)
        {
            products.add((OrderLine) obj);
        }
        myAdapter = new MyPanierAdapter(products, Panier.this);
        mRecyclerView.setAdapter(myAdapter);
    }
    ArrayList<OrderLine> getData2()
    {
        TinyDB tinyDB =new TinyDB(getApplicationContext());
        ArrayList<Object> order=tinyDB.getListObject(user.getUid(),OrderLine.class);
        ArrayList<OrderLine> prod=new ArrayList<>();
        for(Object obj :order)
        {
            prod.add((OrderLine) obj);
        }
        return prod;
    }
    public void showProgressDialog()
    {
        if (mProgressDialog == null)
        {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    public void hideProgressDialog()
    {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }
    public static Context getContext() {
        return mContext;
    }
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.commander)
        {

            ArrayList<OrderLine> order = getData2();
            if (order.size() == 0) {
                Intent amine = new Intent(this, Catalog.class);
                startActivity(amine);
            } else
                {
                Map<String, Object> productInfo = new HashMap<>();
                productInfo.put("Produits_commandées", order);
                productInfo.put("user_id", user.getUid());
                productInfo.put("status", 0);
                float prix_total = 0;
                for (int i = 0; i < order.size(); i++)
                {
                    prix_total = prix_total + (order.get(i).getPrice() * order.get(i).getQuantity());
                }
                productInfo.put("prix_total", prix_total);
                db.collection("commande")
                        .add(productInfo)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Log.v("jsooo", "succeful");
                                TinyDB tinyDB = new TinyDB(getApplicationContext());
                                tinyDB.remove(user.getUid());
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.v("jsooo", "failed");
                            }
                        });
                    OrderLine p=new OrderLine("1",1,1,"1");
                    p.name="Zouhir";
                Intent amine = new Intent(this, Catalog.class);
                startActivity(amine);
            }

        }
    }




}
