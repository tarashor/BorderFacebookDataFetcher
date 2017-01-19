package com.tarashor;

import com.tarashor.api.Facebook;
import com.tarashor.api.models.Post;
import com.tarashor.db.FilteredPost;
import com.tarashor.db.PostsDB;
import com.tarashor.db.StatItem;
import com.tarashor.utils.BorderUtils;
import com.tarashor.utils.CommonUtils;
import com.tarashor.utils.FileUtils;

import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    private static final long PAGEID = 388658784598700L;

    public static void main(String[] args) {

        //*********************************MAIN*************************

        //loadAllPosts();
        loadAllPostsForToday();
        //updateScore();
        //filterPosts();
        //parseDataFromFilteredPosts();

        //**************************************************************


        //System.out.println(CommonUtils.getFirstNumberStartingFrom("asdasd", 0));
        //updateScore();
        //getMostUsedWordsInRightPosts();
        //System.out.println(new java.io.File("").getAbsolutePath());
    }

    private static void loadAllPostsForToday() {
        List<Post> posts = Facebook.getPostsFromPageForToday(PAGEID);
        System.out.println(posts.size());
        PostsDB.savePostsInDB(posts);
    }


    private static void loadAllPosts(){
        List<Post> posts = Facebook.getAllPostsFromPage(PAGEID);
        System.out.println(posts.size());
        PostsDB.savePostsInDB(posts);
    }

    private static void filterPosts(){
        List<Post> posts = PostsDB.getPostsFromDB();
        List<FilteredPost> filteredPosts = new ArrayList<>();
        for(Post post : posts){
            if (isPostContainProperData(post)) {
                filteredPosts.add(CommonUtils.convertPostToFilteredPost(post));
            }
        }
        PostsDB.saveFilteredPostsInDB(filteredPosts);
    }

    private static boolean isPostContainProperData(Post post) {
        return BorderUtils.getPostScore(post) > 16;
    }

    private static void updateScore() {
        List<Post> posts = PostsDB.getPostsFromDB();
        for(Post post : posts){
            int score = BorderUtils.getPostScore(post);
            if (score < 0){
                PostsDB.updateScore(post, score);
            }
        }
    }

    private static void getMostUsedWordsInRightPosts() {
        String filename = "D:\\programs\\java\\FacebookFeedReader\\allpoststype.txt";

        try {
            FileUtils.countWordsInFile(filename);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void parseDataFromFilteredPosts(){
        List<StatItem> statItems = new ArrayList<>();

        List<FilteredPost> posts = PostsDB.getFilteredPostsFromDB();
        for (FilteredPost post : posts){
            statItems.addAll(BorderUtils.getStatItemsFromPost(post.getMessage(), post.getCreationDate()));
        }

        PostsDB.saveStatInDB(statItems);
    }
}
