package com.example.vecom.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.vecom.Model.Product;
import com.example.vecom.R;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private List<Product> productList;
    private List<Product > filteredList;

    public ProductAdapter(List<Product> productList) {
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
        holder.productName.setText(product.getName());
        holder.productPrice.setText(String.valueOf(product.getPrice()));
        holder.productDescription.setText(product.getDesc());
        holder.productImage.setImageResource(product.getImageResourceId());
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView productName, productPrice, productDescription;
        ImageView productImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.productName);
            productPrice = itemView.findViewById(R.id.productPrice);
            productDescription = itemView.findViewById(R.id.descriptionText);
            productImage = itemView.findViewById(R.id.productImg);
        }
    }
    public void filter(String text) {
        filteredList.clear(); // Xóa danh sách đã lọc trước đó

        if (text.isEmpty()) {
            // Nếu chuỗi tìm kiếm trống, hiển thị toàn bộ danh sách sản phẩm
            filteredList.addAll(productList);
        } else {
            // Nếu có chuỗi tìm kiếm, lọc danh sách sản phẩm dựa trên chuỗi
            String searchText = text.toLowerCase().trim();

            for (Product product : productList) {
                if (product.getName().toLowerCase().contains(searchText) ||
                        product.getDesc().toLowerCase().contains(searchText)) {
                    filteredList.add(product);
                }
            }
        }

        notifyDataSetChanged(); // Cập nhật RecyclerView
    }

}