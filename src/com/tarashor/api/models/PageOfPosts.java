package com.tarashor.api.models;

import com.google.gson.annotations.SerializedName;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by Taras on 02.01.2017.
 */
public class PageOfPosts {
    @SerializedName("data")
    private List<Post> posts;
    private Paging paging;

    public List<Post> getPosts() {
        return posts;
    }

    public URL getNextPageUrl(){
        URL nextPageUrl = null;
        try {
            if (paging != null){
                nextPageUrl = new URL(paging.getNext());
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return nextPageUrl;
    }
}
