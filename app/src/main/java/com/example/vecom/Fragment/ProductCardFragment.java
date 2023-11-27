package com.example.vecom.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.vecom.Model.CardItem;
import com.example.vecom.R;

public class ProductCardFragment extends Fragment {

    public ProductCardFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_card, container, false);

        // Lấy dữ liệu từ Bundle
        Bundle data = getArguments();
        if (data != null) {
            CardItem cardItem = (CardItem) data.getSerializable("cardItem");

            // Sử dụng dữ liệu trong cardItem để hiển thị trên fragment
            if (cardItem != null) {
                ImageView productImg = view.findViewById(R.id.product_img);
                TextView productName = view.findViewById(R.id.product_name);
                TextView productDescription = view.findViewById(R.id.descriptionText);
                TextView productPrice = view.findViewById(R.id.price);

                productImg.setImageResource(cardItem.getImageUrl());
                productName.setText(cardItem.getName());
                productDescription.setText(cardItem.getDescription());
                productPrice.setText((int) cardItem.getPrice());
            }
        }

        return view;
    }
}
