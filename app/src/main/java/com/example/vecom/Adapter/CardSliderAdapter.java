package com.example.vecom.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.viewpager.widget.PagerAdapter;

import com.example.vecom.Model.CardItem;
import com.example.vecom.Activity.ProductDescriptionActivity;
import com.example.vecom.R;

import java.util.List;

public class CardSliderAdapter extends PagerAdapter {

    private List<CardItem> cardItems;
    private LayoutInflater layoutInflater;
    private Context context;

    public CardSliderAdapter(Context context, List<CardItem> cardItems) {
        this.context = context;
        this.cardItems = cardItems;
    }

    @Override
    public int getCount() {
        return cardItems.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.fragment_product_card, container, false);
        // Thêm sự kiện onClick cho cardView
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển sang trang chi tiết sản phẩm khi cardView được nhấn
                Intent intent = new Intent(context, ProductDescriptionActivity.class);
                context.startActivity(intent);
            }
        });
        // Customize the view with data from cardItems[position]
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}

