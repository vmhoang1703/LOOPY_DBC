package com.example.vecom.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.vecom.Model.CardItem;
import com.example.vecom.Activity.ProductDescriptionActivity;
import com.example.vecom.Model.Product;
import com.example.vecom.R;

import java.util.List;
import java.util.Locale;

public class CardSliderAdapter extends PagerAdapter {
    private Context context;
    private List<Product> products;

    public CardSliderAdapter(Context context, List<Product> products) {
        this.context = context;
        this.products = products;
    }

    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.product_card, null);

        ImageView productImage = view.findViewById(R.id.product_img);
        TextView productName = view.findViewById(R.id.product_name);
        TextView descriptionText = view.findViewById(R.id.descriptionText);
        TextView priceText = view.findViewById(R.id.priceText);
        TextView price = view.findViewById(R.id.price);

        // Update the views with data from the Product
        Product product = products.get(position);

        // Sử dụng Glide để load hình từ URL
        Glide.with(context).load(product.getImageUrl()).into(productImage);

        productName.setText(product.getName());
        descriptionText.setText(product.getDesc());

        // Format và set giá
        priceText.setText("Giá:");
        price.setText(String.format(Locale.getDefault(), "%,.0f", product.getPrice()) + " VNĐ");

        container.addView(view);
        return view;
    }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}


