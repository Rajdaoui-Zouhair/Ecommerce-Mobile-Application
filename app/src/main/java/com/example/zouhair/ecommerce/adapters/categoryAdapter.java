package com.example.zouhair.ecommerce.adapters;


        import android.content.Context;
        import android.support.v7.widget.RecyclerView;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ImageView;
        import android.widget.TextView;

        import com.bumptech.glide.Glide;
        import com.example.zouhair.ecommerce.R;
        import com.example.zouhair.ecommerce.model.category;

        import java.util.LinkedList;

public class categoryAdapter extends RecyclerView.Adapter<categoryAdapter.MyViewHolder>
{

    private LinkedList<category> myProducts;
    static Context context;
    // Provide a suitable constructor (depends on the kind of dataset)
    public categoryAdapter(LinkedList<category> myProducts, Context context)
    {
        this.myProducts = new LinkedList<category>() ;
        this.myProducts.addAll(myProducts);
        this.context=context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        // create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_adapter, parent, false);
        MyViewHolder vh = new MyViewHolder(itemLayoutView);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position)
    {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Glide.with(context /* context */).load(myProducts.get(position).getCategoryImage()).into(holder.categoryImage);
        holder.categoryTitle.setText(myProducts.get(position).getCategoryText());

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

        public ImageView categoryImage;
        public TextView categoryTitle;
        public MyViewHolder(View itemLayoutView)
        {
            super(itemLayoutView);
            categoryImage=  itemLayoutView.findViewById(R.id.CategoryImage);
            categoryTitle=  itemLayoutView.findViewById(R.id.CategoryTitle);
            itemLayoutView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v)
        {

        }
    }
}

