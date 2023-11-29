package com.example.vecom.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.vecom.Model.Product;
import com.example.vecom.R;

import java.util.List;

public class OrderItemsAdapter extends BaseAdapter {
    private Context context;
    private List<Product> productList;

    public OrderItemsAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @Override
    public int getCount() {
        return productList.size();
    }

    @Override
    public Object getItem(int position) {
        return productList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_addtocart_items, parent, false);
        }

        Product product = productList.get(position);

        ImageView productImage = convertView.findViewById(R.id.productImg);
        TextView productName = convertView.findViewById(R.id.productName);
        TextView productPrice = convertView.findViewById(R.id.productPrice);


        Glide.with(context).load(product.getImageUrl()).into(productImage);
        productName.setText(product.getName());
        productPrice.setText(product.getFormattedPrice());

        return convertView;
    }

}
