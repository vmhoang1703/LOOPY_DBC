package com.example.vecom.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import java.util.List;
import com.example.vecom.Activity.ForumActivity;
import com.example.vecom.Model.Post;
import com.example.vecom.R;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {
    private Context context;
    private List<Post> postList;

    public PostAdapter(Context context, List<Post> postList) {
        this.context = context;
        this.postList = postList;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Post post = postList.get(position);

        // Hiển thị thông tin post trong ViewHolder
        holder.postContent.setText(post.getContent());

        holder.viewNumber.setText(String.valueOf(post.getViews()));
        holder.likeNumber.setText(String.valueOf(post.getLikes()));
        holder.cmtNumber.setText(String.valueOf(post.getComments()));

        // Load hình ảnh post (nếu có)
        // (Chú ý: Đây là ví dụ, bạn có thể thay thế đường dẫn hình ảnh thực tế của bạn)
//        Glide.with(context).load("https://example.com/image.jpg").into(holder.postImage);
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    static class PostViewHolder extends RecyclerView.ViewHolder {
        ImageView postImage;
        TextView postContent;
        TextView viewNumber;
        TextView likeNumber;
        TextView cmtNumber;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            postImage = itemView.findViewById(R.id.postImage);
            postContent = itemView.findViewById(R.id.postContent);
            viewNumber = itemView.findViewById(R.id.viewNumber);
            likeNumber = itemView.findViewById(R.id.likeNumber);
            cmtNumber = itemView.findViewById(R.id.cmtNumber);
        }
    }

}

