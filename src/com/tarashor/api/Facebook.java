package com.tarashor.api;

import com.google.gson.Gson;
import com.tarashor.api.models.PageOfPosts;
import com.tarashor.api.models.Post;
import com.tarashor.utils.UrlUtils;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 * Created by Taras on 02.01.2017.
 */
public class Facebook {
    private static final String ACCESS_TOKEN_VALUE = "EAACEdEose0cBAJ4gHCx1ZAp9RmoHgwjRRTQ7xYsKC4TWGC7Lgc6KMcw78kJWZB5hcCT7Tw3YWZA0HGrZCAZB43vpkOoazZC0u4Lw62KRz3OuAnJvZBxvnm4dkTv4JbK7xZBTniYLsgAVQ4ZByyuSZAXvVBpfwrZANFksuh6l1v8jtXSPAZDZD";

    private static final String HOST = "graph.facebook.com";
    private static final String SCHEME = "https";
    private static final String PATH = "/v2.8/%d/posts";

    private static final String ACCESS_TOKEN_KEY = "access_token";
    private static final String LIMIT_KEY = "limit";
    private static final String SINCE_KEY = "since";

    private static final String UTF_8 = "UTF-8";
    private static final int LIMIT = 100;

    public static List<Post> getPostsFromPageForToday(long pageId){
        PageOfPosts posts = getPostsFromUrl(createUrlForPostsForToday(pageId));
        return posts.getPosts();
    }

    public static List<Post> getAllPostsFromPage(long pageId){
        List<Post> allPosts = new ArrayList<>();
        PageOfPosts posts = getPostsFromUrl(createUrlForPosts(pageId));
        allPosts.addAll(posts.getPosts());
        while (posts.getNextPageUrl() != null){
            posts = getPostsFromUrl(posts.getNextPageUrl());
            allPosts.addAll(posts.getPosts());
        }
        return allPosts;
    }

    public static URL createUrlForPostsForToday(long pageid) {
        URL url = null;
        try {
            Map<String, String> params = new HashMap<>();
            params.put(ACCESS_TOKEN_KEY, ACCESS_TOKEN_VALUE);
            params.put(LIMIT_KEY, String.valueOf(LIMIT));
            params.put(SINCE_KEY, String.valueOf(getTodaysTimeInSeconds()));
            String query = UrlUtils.createQueryString(params);
            String pagePath = String.format(PATH, pageid);
            URI uri = new URI(SCHEME, HOST, pagePath, query, "");
            url = uri.toURL();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static URL createUrlForPosts(long pageid) {
        URL url = null;
        try {
            Map<String, String> params = new HashMap<>();
            params.put(ACCESS_TOKEN_KEY, ACCESS_TOKEN_VALUE);
            params.put(LIMIT_KEY, String.valueOf(LIMIT));
            //params.put(SINCE_KEY, String.valueOf(getTodaysTimeInSeconds()));
            String query = UrlUtils.createQueryString(params);
            String pagePath = String.format(PATH, pageid);
            URI uri = new URI(SCHEME, HOST, pagePath, query, "");
            url = uri.toURL();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    private static long getTodaysTimeInSeconds() {
        Calendar today = Calendar.getInstance();
        today.add(Calendar.DAY_OF_MONTH, -1);
        return today.getTimeInMillis() / 1000;
    }

    private static PageOfPosts getPostsFromUrl(URL url) {
        PageOfPosts posts = new PageOfPosts();
        if (url != null) {
            InputStream inputStream = null;
            try {
                URLConnection myURLConnection = url.openConnection();
                inputStream = myURLConnection.getInputStream();
                posts = getPostsFromStream(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (inputStream != null)
                        inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return posts;
    }

    private static PageOfPosts getPostsFromStream(InputStream inputStream) throws IOException {
        Reader reader = new InputStreamReader(inputStream, UTF_8);
        PageOfPosts result = new Gson().fromJson(reader, PageOfPosts.class);
        return result;
    }
}
