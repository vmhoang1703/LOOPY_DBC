package com.example.vecom.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.vecom.Model.CardItem;
import com.example.vecom.Model.Product;
import com.example.vecom.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MyProductAdapter extends RecyclerView.Adapter<MyProductAdapter.ViewHolder> {

    private List<Product> productList;
    private List<Product> filteredList;
    private Context context;

    public MyProductAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
        this.filteredList = new ArrayList<>(productList);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_addtocart_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = productList.get(position);

        // Set data to views
        holder.productName.setText(product.getName());
        holder.productPrice.setText(String.valueOf(product.getPrice()));
        // Use Glide to load the image from the URL
        Glide.with(context).load(product.getImageUrl()).into(holder.productImage);
        // Set click listener for delete icon
        holder.deleteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Use holder.getAdapterPosition() to get the current adapter position
                int adapterPosition = holder.getAdapterPosition();

                // Get the item to be deleted
                Product itemToDelete = productList.get(adapterPosition);

                // Remove the item from the list
                productList.remove(adapterPosition);

                // Notify the adapter about the item removal
                notifyItemRemoved(adapterPosition);

                // Call a method to delete the item from Firebase
                deleteItemFromFirebase(itemToDelete);
            }
        });

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView productName, productPrice;
        ImageView productImage, deleteIcon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.productName);
            productPrice = itemView.findViewById(R.id.productPrice);
            productImage = itemView.findViewById(R.id.productImg);
            deleteIcon = itemView.findViewById(R.id.deleteIcon);
        }
    }

    public void filterByName(String text) {
        filteredList.clear(); // Clear the previously filtered list

        if (text.isEmpty()) {
            // If the search string is empty, display the entire product list
            filteredList.addAll(productList);
        } else {
            // If there is a search string, filter the product list based on the string
            String searchText = text.toLowerCase().trim();

            for (Product product : productList) {
                if (product.getName().toLowerCase().contains(searchText)) {
                    filteredList.add(product);
                }
            }
        }
        notifyDataSetChanged(); // Update the RecyclerView
    }
    private void deleteItemFromFirebase(Product itemToDelete) {
        DatabaseReference products = FirebaseDatabase.getInstance().getReference("products");

        // Use the unique key of the item to be deleted
        String itemKey = itemToDelete.getProductId();

        // Remove the item from Firebase with completion listener
        products.orderByChild("ProductId").equalTo(itemKey).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot cardItemSnapshot : dataSnapshot.getChildren()) {
                    // Get the unique key of the CardItem to be deleted
                    String productKey = cardItemSnapshot.getKey();

                    // Remove the CardItem from Firebase
                    products.child(productKey).removeValue();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Firebase", "Failed to read value.", error.toException());
            }
        });
    }
}