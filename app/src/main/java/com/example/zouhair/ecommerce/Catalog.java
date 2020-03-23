package com.example.zouhair.ecommerce;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.zouhair.ecommerce.adapters.CommandeAdapter;
import com.example.zouhair.ecommerce.adapters.MyCatalogAdapter;
import com.example.zouhair.ecommerce.adapters.MyPanierAdapter;
import com.example.zouhair.ecommerce.adapters.Profile_Adapter;
import com.example.zouhair.ecommerce.adapters.categoryAdapter;
import com.example.zouhair.ecommerce.model.OrderLine;
import com.example.zouhair.ecommerce.model.Product;
import com.example.zouhair.ecommerce.model.TinyDB;
import com.example.zouhair.ecommerce.model.category;
import com.example.zouhair.ecommerce.model.commande;
import com.example.zouhair.ecommerce.model.profile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Catalog extends FragmentActivity  implements View.OnClickListener{


    private static final String TAG = "Catalog";
    private RecyclerView myRecycler;
    private RecyclerView categoryRecycler;
    private categoryAdapter mAdapterr;
    private MyPanierAdapter myAdapterr;
    private RecyclerView.Adapter myAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private FirebaseFirestore db= FirebaseFirestore.getInstance();
    LinkedList<Product> products;
    ArrayList<OrderLine> products1;
    LinkedList<commande> commandes;
    LinkedList<profile> profiles;
    LinkedList<category> categorys;
    public ProgressDialog mProgressDialog;
    private FirebaseUser user ;
    public TextView id,title;
    public Button go_back;
    public Button commander,supprimer;
    Shader myShader ;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener()
    {


        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item)
        {
            Intent amine;
            switch (item.getItemId())
            {
                case R.id.navigation_catalogue:
                    title.setText("Cosmétics");
                    commander.setVisibility(View.GONE);
                    getProducts();
                    return true;
                case R.id.navigation_panier:
                    title.setText("CHART");
                    getData1();
                    if(products1.size()>0)
                    commander.setVisibility(View.VISIBLE);
                    return true;
                case R.id.navigation_commandes:
                    title.setText("Commandes");
                    getCommandes();
                    commander.setVisibility(View.GONE);
                    return true;
                case R.id.navigation_profile:
                    getProfile();
                    title.setText("Profile");
                    commander.setVisibility(View.GONE);
                    return true;
            }
            return false;
        }
    };

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.catalog);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        user = FirebaseAuth.getInstance().getCurrentUser();
        myRecycler= findViewById(R.id.productsList);
        id=(TextView) findViewById(R.id.id);
        commander=(Button)findViewById(R.id.commander);
        commander.setOnClickListener(this);
        supprimer=(Button)findViewById(R.id.commander);
        myShader = new LinearGradient(
                10, 0, 10, 100,
                this.getResources().getColor(R.color.white), getResources().getColor(R.color.yellow),
                Shader.TileMode.CLAMP );
        supprimer.setOnClickListener(this);
        getProducts();
        //view instantiation
        // RecyclerView must be initialized in this method because fireStore is asynchronous
    }

    private void getProducts()
    {
        products=new  LinkedList<Product>();
        categorys=new  LinkedList<category>();
        showProgressDialog();
        //connexion avec FireBase
        db.collection("produit").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>()
                {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task)
                    {
                        if (task.isSuccessful())
                        {
                            for (QueryDocumentSnapshot document : task.getResult())
                            {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                Product p= new Product(document.getId(),document.get("description").toString(),document.get("image").toString(),document.get("designation").toString(),Float.valueOf(document.get("price").toString()).floatValue());
                                System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^"+p);
                                products.add(p);
                                System.out.println(products);
                            }

                        } else
                            {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }

                        hideProgressDialog();
                        // use this setting to improve performance if you know that changes
                        // in content do not change the layout size of the RecyclerView
                        myRecycler.setHasFixedSize(true);
                        // use a linear layout manager
                        layoutManager = new GridLayoutManager(Catalog.this,2);
                        myRecycler.setLayoutManager(layoutManager);
                        // specify an adapter (see also next example)
                        myAdapter = new MyCatalogAdapter(products, Catalog.this);
                        myRecycler.setAdapter(myAdapter);
                        mAdapterr = new categoryAdapter(categorys,getApplicationContext());
                    }
                });
    }
    void getCommandes()
    {
        commandes=new  LinkedList<commande>();
        showProgressDialog();
        db.collection("commande").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>()
        {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task)
            {
                if (task.isSuccessful())
                {
                    for (QueryDocumentSnapshot document : task.getResult())
                    {
                        if (document.get("user_id").equals(user.getUid()))
                        {
                            double prix=(double)document.get("prix_total");
                            boolean status=false;
                            if((boolean)document.get("status")==false)
                            {
                                status=false;
                            }
                            else
                            {
                                status=true;
                            }

                            commande c= new commande((ArrayList<Product>) document.get("Produits_commandées"),prix,status);
                            Log.d(TAG, "droksler" + " => " + document.getData());
                            commandes.add(c);
                        }

                    }

                } else
                {
                    Log.w(TAG, "Error getting documents.", task.getException());
                }
                hideProgressDialog();

                // use this setting to improve performance if you know that changes
                // in content do not change the layout size of the RecyclerView
                myRecycler.setHasFixedSize(true);
                // use a linear layout manager
                layoutManager = new LinearLayoutManager(Catalog.this);
                myRecycler.setLayoutManager(layoutManager);
                // specify an adapter (see also next example)
                myAdapter = new CommandeAdapter(commandes, getApplicationContext());
                myRecycler.setAdapter(myAdapter);

            }
        });
    }

    void getData1()
    {
        products1=new ArrayList<>();
        showProgressDialog();
        user = FirebaseAuth.getInstance().getCurrentUser();
        TinyDB tinyDB =new TinyDB(getApplicationContext());
        ArrayList<Object> order=tinyDB.getListObject(user.getUid(),OrderLine.class);
        ArrayList<OrderLine> prod=new ArrayList<>();
        for(Object obj :order)
        {
            products1.add((OrderLine) obj);
        }
        hideProgressDialog();
        if(products1.size()==0)
            commander.setVisibility(View.GONE);
        myRecycler.setHasFixedSize(true);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(Catalog.this);
        myRecycler.setLayoutManager(layoutManager);
        myAdapterr = new MyPanierAdapter(products1, getApplicationContext());
        myRecycler.setAdapter(myAdapterr);
        myAdapterr.setOnItemClickListener(new MyPanierAdapter.onItemClickListener() {
            @Override
            public void onItemClick(int position) {
                getData1();
            }
        });

    }
    public void getProfile(){
        profiles=new LinkedList<>();
        showProgressDialog();
        profile f=new profile("RAJDAOUI Zouhair","Developer","lhapss.1996@gmail.com","0636451275","Ràjdaoui Zouhàir","Raj-zou");
        profiles.add(f);
        hideProgressDialog();
        myRecycler.setHasFixedSize(true);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(Catalog.this);
        myRecycler.setLayoutManager(layoutManager);
        // specify an adapter (see also next example)
        myAdapter = new Profile_Adapter(profiles, getApplicationContext());
        myRecycler.setAdapter(myAdapter);
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
    @Override
    public void onClick(View v) {
            if (v.getId()==R.id.commander)
            {
                ArrayList<OrderLine> order = getData2();
                if (order.size() == 0) {
                    Intent amine = new Intent(this, Catalog.class);
                    startActivity(amine);
                } else
                {
                    Map<String, Object> productInfo = new HashMap<>();
                    for (int i = 0; i < order.size(); i++)
                    {
                        order.get(i).setImage(null);
                    }
                    productInfo.put("Produits_commandées", order);
                    productInfo.put("user_id", user.getUid());
                    productInfo.put("status", false);
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
                    Intent amine = new Intent(this, Catalog.class);
                    startActivity(amine);
                }
            }
    }
}
