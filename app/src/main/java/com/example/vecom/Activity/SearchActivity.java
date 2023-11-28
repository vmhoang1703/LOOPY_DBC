package com.example.vecom.Activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vecom.Adapter.ProductAdapter;
import com.example.vecom.Model.Product;
import com.example.vecom.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private EditText searchEditText;
    private RecyclerView searchResultsRecyclerView;
    private ProductAdapter productAdapter;
    private List<Product> productList; // Danh sách sản phẩm từ Firebase

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchEditText = findViewById(R.id.searchBox);
        searchResultsRecyclerView = findViewById(R.id.searchResultsRecyclerView);
        productList = new ArrayList<>(); // Khởi tạo danh sách sản phẩm

        productAdapter = new ProductAdapter(productList);
        searchResultsRecyclerView.setAdapter(productAdapter);
        searchResultsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Triển khai chức năng tìm kiếm
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterProducts(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        getDataFromFirebase();
    }

    private void getDataFromFirebase() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("products");

        // Lắng nghe sự kiện thay đổi dữ liệu trên Firebase Realtime Database
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Xóa danh sách sản phẩm hiện tại để tránh việc lặp lại dữ liệu khi có sự thay đổi
                productList.clear();

                // Duyệt qua mỗi nút con trong "products"
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    // Parse dữ liệu từ dataSnapshot và thêm vào danh sách sản phẩm
                    Product product = snapshot.getValue(Product.class);
                    productList.add(product);
                }

                // Gọi hàm filter để hiển thị toàn bộ danh sách
                filterProducts("");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Xử lý khi có lỗi xảy ra trong quá trình đọc dữ liệu từ Firebase
                // Ví dụ: Log.d(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    private void filterProducts(String searchText) {
        productAdapter.filter(searchText);
    }
}
