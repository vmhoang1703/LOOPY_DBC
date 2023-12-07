package com.example.vecom.Adapter;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.vecom.Activity.FollowOrderActivity;
import com.example.vecom.Model.CardItem;
import com.example.vecom.Model.Order;
import com.example.vecom.R;

import java.util.ArrayList;
import java.util.List;

public class OrderManagerAdapter extends RecyclerView.Adapter<OrderManagerAdapter.ViewHolder> {

    private List<Order> OrderList;
    private List<Order> filteredList;
    private Context context;

    public OrderManagerAdapter(Context context, List<Order> OrderList) {
        this.context = context;
        this.OrderList = OrderList;
        this.filteredList = new ArrayList<>(OrderList);
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_order_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Order product = OrderList.get(position);

        // Set data to views
        holder.productName.setText(product.getProductName());
        holder.productPrice.setText(String.valueOf(product.getPrice()));
        // Use Glide to load the image from the URL
        Glide.with(context).load(product.getImageUrl()).into(holder.productImage);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, FollowOrderActivity.class);
                intent.putExtra("productId", product.getProductId());
                intent.putExtra("productName", product.getProductName());
                intent.putExtra("productPrice", String.valueOf(product.getPrice()));
                intent.putExtra("productImg", product.getImageUrl());

                ObjectAnimator fadeOut = ObjectAnimator.ofFloat(holder.itemView, "alpha", 1f, 0.5f);
                fadeOut.setDuration(300); // Thời gian của hiệu ứng, có thể điều chỉnh
                fadeOut.start();

                context.startActivity(intent);
            }
        });

    }

    @Override

    public int getItemCount() {
        return OrderList != null ? OrderList.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView productName, productPrice;
        ImageView productImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.productName);
            productPrice = itemView.findViewById(R.id.productPrice);
            productImage = itemView.findViewById(R.id.productImg);

        }
    }

    public void filterByName(String text) {
        filteredList.clear(); // Clear the previously filtered list

        if (text.isEmpty()) {
            // If the search string is empty, display the entire product list
            filteredList.addAll(OrderList);
        } else {
            // If there is a search string, filter the product list based on the string
            String searchText = text.toLowerCase().trim();

            for (Order product : OrderList) {
                if (product.getProductName().toLowerCase().contains(searchText)) {
                    filteredList.add(product);
                }
            }
        }
        notifyDataSetChanged(); // Update the RecyclerView
    }
}