package com.tarashor.api.models;

import javax.print.DocFlavor;

/**
 * Created by Taras on 02.01.2017.
 */
public class Post {
    private String message;
    private String created_time;
    private String id;

    public String getMessage() {
        return message;
    }

    public String getCreatedTime() {
        return created_time;
    }

    public String getId() {
        return id;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setCreated_time(String created_time) {
        this.created_time = created_time;
    }

    public void setId(String id) {
        this.id = id;
    }

    public static Post createPost(String id, String m, String created_time){
        Post post = new Post();
        post.setCreated_time(created_time);
        post.setMessage(m);
        post.setId(id);
        return post;
    }
}
