package com.example.vecom.Model;

public class Post {
    private String content;
    private String userId;
    private int views;
    private int likes;
    private int comments;

    // Constructor mặc định để đọc dữ liệu từ Firebase
    public Post() {}

    public Post(String content, String userId) {
        this.content = content;
        this.userId = userId;
        this.views = 0;
        this.likes = 0;
        this.comments = 0;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }
}
