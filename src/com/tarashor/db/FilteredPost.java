package com.tarashor.db;

import com.tarashor.api.models.Post;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by Taras on 03.01.2017.
 */
public class FilteredPost {
    private String facebookId;
    private String message;
    private Date creationDate;

    public String getFacebookId() {
        return facebookId;
    }

    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public static FilteredPost createPost(String id, String message, Date creationDate) {
        FilteredPost filteredPost = new FilteredPost();
        filteredPost.setMessage(message);
        filteredPost.setFacebookId(id);
        filteredPost.setCreationDate(creationDate);
        return filteredPost;
    }
}
