package com.example.vecom.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.vecom.Model.Product;
import com.example.vecom.R;

import java.util.List;

public class ProductDescriptionAdapter extends BaseAdapter {

    private Context context;
    private List<Product> productList;

    public ProductDescriptionAdapter(Context context, List<Product> productList) {
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
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_product_description, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.textViewName = convertView.findViewById(R.id.productName);
            // Add other views for product details

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Product product = productList.get(position);

        // Set data to views
        viewHolder.textViewName.setText(product.getName());
        // Set other details

        return convertView;
    }

    private static class ViewHolder {
        TextView textViewName;
        // Add other views for product details
    }
}

