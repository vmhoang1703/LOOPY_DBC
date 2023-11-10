package com.example.vecom.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vecom.Model.Order;
import com.example.vecom.R;

import java.util.List;

public class OrderManagementAdapter extends BaseAdapter {
    private Context context;
    private List<Order> orderList;

    public OrderManagementAdapter(Context context, List<Order> orderList) {
        this.context = context;
        this.orderList = orderList;
    }

    @Override
    public int getCount() {
        return orderList.size();
    }

    @Override
    public Object getItem(int position) {
        return orderList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_order_items, parent, false);
        }

        Order order = orderList.get(position);

        ImageView productImage = convertView.findViewById(R.id.productImg);
        TextView productName = convertView.findViewById(R.id.productName);
        TextView productPrice = convertView.findViewById(R.id.productPrice);
        TextView productQuantity = convertView.findViewById(R.id.quantityNumber);
        TextView orderStatus = convertView.findViewById(R.id.orderStatus);

        productImage.setImageResource(order.getProductImgRsc());
        productName.setText(order.getProductName());
        productPrice.setText(order.getFormattedTotalPrice());
        productQuantity.setText(order.getQuantity());
        orderStatus.setText(order.getStatus());

        return convertView;
    }
}
