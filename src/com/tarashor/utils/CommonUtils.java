package com.tarashor.utils;

import com.tarashor.api.models.Post;
import com.tarashor.db.FilteredPost;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Taras on 04.01.2017.
 */
public class CommonUtils {

    public static final String FACEBOOK_DATETIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";

    public static FilteredPost convertPostToFilteredPost(Post post){
        FilteredPost filteredPost = new FilteredPost();
        filteredPost.setFacebookId(post.getId());
        filteredPost.setMessage(post.getMessage());
        filteredPost.setCreationDate(convertStringToDate(post.getCreatedTime()));
        return filteredPost;
    }

    public static Date convertStringToDate(String dateString) {
        DateFormat df = new SimpleDateFormat(FACEBOOK_DATETIME_FORMAT);
        Date date = null;
        try {
            date = df.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;

    }

    public static int getFirstNumberStartingFrom(String string, int startIndex){
        int i = startIndex;
        while (i < string.length() && !Character.isDigit(string.charAt(i))) i++;
        int j = i;
        while (j < string.length() && Character.isDigit(string.charAt(j))) j++;

        try {
            return Integer.parseInt(string.substring(i, j)); // might be an off-by-1 here
        } catch (NumberFormatException nfe){
            return 0;
        }
    }
}
