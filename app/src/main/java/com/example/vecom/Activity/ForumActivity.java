package com.example.vecom.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.vecom.Adapter.PostAdapter;
import com.example.vecom.Model.Post;
import com.example.vecom.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ForumActivity extends AppCompatActivity {
    private DatabaseReference postsReference;
    private List<Post> postList;
    private PostAdapter postAdapter;
    private FirebaseAuth mAuth;
    private DatabaseReference userReference;
    public  String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            String userEmail = currentUser.getEmail();

            // Assuming you have a node in your database named "users" where emails are stored
            userReference = FirebaseDatabase.getInstance().getReference().child("users");

            // Query the database based on email
            Query query = userReference.orderByChild("userEmail").equalTo(userEmail);

            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                        userId = userSnapshot.getKey();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle errors
                }
            });
        }

        ImageView backArrow = findViewById(R.id.back);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForumActivity.this, HomeActivity.class);
                // Tạo hiệu ứng làm mờ nút
                ObjectAnimator fadeOut = ObjectAnimator.ofFloat(backArrow, "alpha", 1f, 0.5f);
                fadeOut.setDuration(300); // Thời gian của hiệu ứng, có thể điều chỉnh
                fadeOut.start();

                startActivity(intent);
                finish();
            }
        });

        postsReference = FirebaseDatabase.getInstance().getReference().child("posts");
        postList = new ArrayList<>();
        postAdapter = new PostAdapter(this, postList);

        RecyclerView postRecyclerView = findViewById(R.id.postRecyclerView);
        postRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        postRecyclerView.setAdapter(postAdapter);

        displayLatestPosts();

        ImageView postButton = findViewById(R.id.postButton);
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadPost();
            }
        });
    }

    // Hàm đăng post
    private void uploadPost() {
        // Lấy nội dung post từ EditText
        EditText postEditText = findViewById(R.id.postEditText);
        String postContent = postEditText.getText().toString().trim();

        if (!TextUtils.isEmpty(postContent)) {
            Post post = new Post(postContent, userId);

            String postId = postsReference.push().getKey();
            postsReference.child(postId).setValue(post);

            // Xóa nội dung trong EditText sau khi đăng
            postEditText.setText("");

            // Hiển thị thông báo hoặc cập nhật UI nếu cần
            Toast.makeText(this, "Bài viết đã được đăng thành công", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Nội dung bài viết không được để trống", Toast.LENGTH_SHORT).show();
        }
    }

    private void displayLatestPosts() {
        postsReference.orderByKey().limitToLast(3).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                postList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Post post = postSnapshot.getValue(Post.class);
                    // Add new posts at the beginning of the list (reverse order)
                    postList.add(0, post);
                }
                postAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Firebase", "Error: " + databaseError.getMessage());
            }
        });
    }

}