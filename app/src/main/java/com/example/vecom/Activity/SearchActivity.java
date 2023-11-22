package com.example.vecom.Activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.vecom.R;


public class SearchActivity extends AppCompatActivity {

    private EditText searchEditText;
    private ListView searchResultsListView;
//    private CustomAdapter customAdapter; // Tạo adapter tùy chỉnh của bạn

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

//        searchEditText = findViewById(R.id.searchBox);
//        searchResultsListView = findViewById(R.id.searchResultsListView);
//
//        // Khởi tạo adapter tùy chỉnh của bạn và đặt nó cho ListView
//        customAdapter = new CustomAdapter(this);
//        searchResultsListView.setAdapter(customAdapter);
//
//        // Triển khai chức năng tìm kiếm
//        searchEditText.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                customAdapter.getFilter().filter(s);
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//            }
//        });
//
//        // Kiểm tra và hiển thị hoặc ẩn thông báo "Không tìm thấy sản phẩm"
//        searchResultsListView.setEmptyView(findViewById(R.id.noResultsLayout));
    }
}
