package com.example.zouhair.ecommerce.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.zouhair.ecommerce.R;
import com.example.zouhair.ecommerce.model.commande;

import java.util.LinkedList;

public class CommandeAdapter extends RecyclerView.Adapter<CommandeAdapter.MyViewHolder> {

    private LinkedList<commande> commandes;
    static Context context;
    public CommandeAdapter(LinkedList<commande> commandes,Context context) {
        this.commandes = new LinkedList<commande>();
        this.commandes.addAll(commandes);
        this.context=context;
    }
    public CommandeAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        // create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_commande_adapter, parent, false);
        CommandeAdapter.MyViewHolder vh = new CommandeAdapter.MyViewHolder(itemLayoutView);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder ViewHolder, int posistion) {
        ViewHolder.produts_number.setText(String.valueOf(commandes.get(posistion).getProduts().size()));
        ViewHolder.prix_total.setText(String.valueOf(commandes.get(posistion).getPrix_total()));
        if(commandes.get(posistion).isStatus()==true)
        {
            ViewHolder.status.setText("Paid");
            ViewHolder.status.setTextColor(context.getResources().getColor((R.color.green)));
        }
        else
        {
            ViewHolder.status.setText("Not Paid");
            ViewHolder.status.setTextColor(context.getResources().getColor((R.color.red)));
        }

    }

    // Replace the contents of a view (invoked by the layout manager)

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount()
    {
        return commandes.size();
    }


// Provide a reference to the views for each data item
// Complex data items may need more than one view per item, and
// you provide access to all the views for a data item in a view holder

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        // each data item is just a string in this case


        public TextView produts_number;
        public TextView prix_total;
        public TextView status;
        public MyViewHolder(View itemLayoutView)
        {
            super(itemLayoutView);
            produts_number=itemLayoutView.findViewById(R.id.nombre_de_produits);
            prix_total=itemLayoutView.findViewById(R.id.prix_total);
            status=itemLayoutView.findViewById(R.id.status);
        }

        @Override
        public void onClick(View v)
        {

        }
    }
}
