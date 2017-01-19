package com.tarashor.db;

import com.tarashor.api.models.Post;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by Taras on 03.01.2017.
 */
public class PostsDB {
    private static final String connectionStringMYSQL = "jdbc:mysql://localhost:3306/facebookposts";
    private static final String insertPostStringMYSQL = "Insert into posts (id, message, creation_date) values (?, ?, ?)";
    private static final String selectPostsStringMYSQL = "select * from posts";
    private static java.lang.String updateScorePostStringMYSQL = "UPDATE posts SET words_score = ? WHERE id = ?";

    private static final String insertFilteredPostStringMYSQL = "Insert into filtered_posts (facebook_id, message, creation_date) values (?, ?, ?)";
    private static final String selectFilteredPostsStringMYSQL = "select * from filtered_posts";

    private static final String insertStatStringMYSQL = "Insert into stat (pass_name, car_count, date) values (?, ?, ?)";


    public static void savePostsInDB(List<Post> posts){

        try {
            Connection con = DriverManager.getConnection(connectionStringMYSQL, getConnectionProperties());

            PreparedStatement ps = con.prepareStatement(insertPostStringMYSQL);

            for (Post post : posts){
                ps.setString(1, post.getId());
                ps.setString(2, post.getMessage());
                ps.setString(3, post.getCreatedTime());
                ps.addBatch();
                ps.clearParameters();
            }

            int[] results = ps.executeBatch();
            ps.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Post> getPostsFromDB(){
        List<Post> posts = new ArrayList<>();

        try {
            Connection con = DriverManager.getConnection(connectionStringMYSQL, getConnectionProperties());
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(selectPostsStringMYSQL);
            while(resultSet.next()){
                //Retrieve by column name
                String id  = resultSet.getString("id");
                String message = resultSet.getString("message");
                String creationDate = resultSet.getString("creation_date");

                posts.add(Post.createPost(id, message, creationDate));
            }

            resultSet.close();
            statement.close();
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return posts;
    }

    public static void saveFilteredPostsInDB(List<FilteredPost> posts){

        try {
            Connection con = DriverManager.getConnection(connectionStringMYSQL, getConnectionProperties());

            PreparedStatement ps = con.prepareStatement(insertFilteredPostStringMYSQL);

            for (FilteredPost post : posts){
                ps.setString(1, post.getFacebookId());
                ps.setString(2, post.getMessage());
                Timestamp timestamp = new Timestamp(post.getCreationDate().getTime());
                ps.setTimestamp(3, timestamp);
                ps.addBatch();
                ps.clearParameters();
            }

            int[] results = ps.executeBatch();
            ps.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<FilteredPost> getFilteredPostsFromDB(){
        List<FilteredPost> posts = new ArrayList<>();

        try {
            Connection con = DriverManager.getConnection(connectionStringMYSQL, getConnectionProperties());
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(selectFilteredPostsStringMYSQL);
            while(resultSet.next()){
                //Retrieve by column name
                String id  = resultSet.getString("facebook_id");
                String message = resultSet.getString("message");
                Timestamp creationDate = resultSet.getTimestamp("creation_date");

                posts.add(FilteredPost.createPost(id, message, creationDate));
            }

            resultSet.close();
            statement.close();
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return posts;
    }

    private static Properties getConnectionProperties() {
        Properties properties = new Properties();
        properties.setProperty("user", "root");
        properties.setProperty("password", "");
        properties.setProperty("useUnicode","true");
        properties.setProperty("characterEncoding","UTF-8");
        return properties;
    }

    public static void updateScore(Post post, int score) {
        if (post != null && score != 0) {
            try {
                Connection con = DriverManager.getConnection(connectionStringMYSQL, getConnectionProperties());

                PreparedStatement ps = con.prepareStatement(updateScorePostStringMYSQL);

                // set the preparedstatement parameters
                ps.setInt(1, score);
                ps.setString(2, post.getId());

                // call executeUpdate to execute our sql update statement
                ps.executeUpdate();

                ps.close();
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void saveStatInDB(List<StatItem> statItems) {
        try {
            Connection con = DriverManager.getConnection(connectionStringMYSQL, getConnectionProperties());

            PreparedStatement ps = con.prepareStatement(insertStatStringMYSQL);

            for (StatItem statItem : statItems){
                ps.setString(1, statItem.getPass());
                ps.setInt(2, statItem.getCount());
                Timestamp timestamp = new Timestamp(statItem.getDate().getTime());
                ps.setTimestamp(3, timestamp);
                ps.addBatch();
                ps.clearParameters();
            }

            int[] results = ps.executeBatch();
            ps.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
