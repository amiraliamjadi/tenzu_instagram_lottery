package db;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseHandle
{


    // for first creation
    public void create()
    {

        final String DATABASE_URL = "jdbc:derby:tenzudb;create=true";
        final String TABLE_SETTING = "CREATE TABLE setting_config (image_path VARCHAR(255), handle_uri VARCHAR(255), insta_post_image_number VARCHAR(255))";
        final String TABLE_INSTAGRAM = "CREATE TABLE instagram_cm(id VARCHAR(255), username VARCHAR(255), text VARCHAR(255))";

        try
        {
            Connection connection = DriverManager.getConnection(DATABASE_URL);
            Statement statement = connection.createStatement();


            //setting
            statement.executeUpdate(TABLE_SETTING);

            // insta
            statement.executeUpdate(TABLE_INSTAGRAM);


        }
        catch (SQLException sqlExeption)
        {
            sqlExeption.printStackTrace();
        }



    }



    // setting data



    public void insertSettingData(String imagePath , String handleUri , String instaPostImageNumber)
    {


        final String DATABASE_URL = "jdbc:derby:tenzudb";
        final String INSERT_SETTING = "INSERT INTO setting_config VALUES " +
                "(" + "\'" + imagePath + "\'" + ", " + "\'" + handleUri + "\'" + ", " + "\'" + instaPostImageNumber + "\'" + ")";

        try
        {
            Connection connection = DriverManager.getConnection(DATABASE_URL);
            Statement statement = connection.createStatement();

            statement.execute("DELETE FROM setting_config");
            statement.executeUpdate(INSERT_SETTING);

        }
        catch (Exception e)
        {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }



    }

    public String querySettingData()
    {
        final String DATABASE_URL = "jdbc:derby:tenzudb";
        String message = "error";

        try
        {
            Connection connection = DriverManager.getConnection(DATABASE_URL);
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT image_path, handle_uri, insta_post_image_number FROM setting_config");

            while (resultSet.next())
            {
                message = resultSet.getString("image_path") + "\n" + resultSet.getString("handle_uri") + "\n" + resultSet.getString("insta_post_image_number");
            }

        }
        catch (SQLException sqlExeption)
        {
            sqlExeption.printStackTrace();
        }

        return message;

    }


    public String querySettingImagePath()
    {
        final String DATABASE_URL = "jdbc:derby:tenzudb";
        String message = "C:\\Users\\Adminstator\\Desktop\\instaimage";

        try
        {
            Connection connection = DriverManager.getConnection(DATABASE_URL);
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT image_path FROM setting_config");

            while (resultSet.next())
            {
                message = resultSet.getString("image_path");
            }

        }
        catch (SQLException sqlExeption)
        {
            sqlExeption.printStackTrace();
        }

        return message;
    }

    public String querySettingHandleUri()
    {
        final String DATABASE_URL = "jdbc:derby:tenzudb";
        String message = "tenzumusic";

        try
        {
            Connection connection = DriverManager.getConnection(DATABASE_URL);
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT handle_uri FROM setting_config");

            while (resultSet.next())
            {
                message = resultSet.getString("handle_uri");
            }

        }
        catch (SQLException sqlExeption)
        {
            sqlExeption.printStackTrace();
        }

        return message;
    }


    public String querySettingInstaPostImageNumber()
    {
        final String DATABASE_URL = "jdbc:derby:tenzudb";
        String message = "0";

        try
        {
            Connection connection = DriverManager.getConnection(DATABASE_URL);
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT insta_post_image_number FROM setting_config");

            while (resultSet.next())
            {
                message = resultSet.getString("insta_post_image_number");
            }


        }
        catch (SQLException sqlExeption)
        {
            sqlExeption.printStackTrace();
        }

        return message;
    }




    // instagram data


    public void insertInstaData(String id , String username , String text)
    {

        final String DATABASE_URL = "jdbc:derby:tenzudb";
        final String INSERT_INSTA = "INSERT INTO instagram_cm VALUES " +
                "(" + "\'" + id + "\'" + ", " + "\'" + username + "\'" + ", " + "\'" + text + "\'" + ")";

        try
        {
            Connection connection = DriverManager.getConnection(DATABASE_URL);
            Statement statement = connection.createStatement();

            statement.executeUpdate(INSERT_INSTA);

        }
        catch (Exception e)
        {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }

    }

    public class UserObjects
    {
        String id;
        String username;
        String text;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }


    }

    public ArrayList<UserObjects> queryInstaData()
    {
        final String DATABASE_URL = "jdbc:derby:tenzudb";

        ArrayList<UserObjects> usersData = new ArrayList<UserObjects>(100);




        try
        {
            Connection connection = DriverManager.getConnection(DATABASE_URL);
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT id, username, text FROM instagram_cm");

           while (resultSet.next())
           {
               UserObjects userObjects = new UserObjects();
               userObjects.setId(resultSet.getString("id"));
               userObjects.setUsername(resultSet.getString("username"));
               userObjects.setText(resultSet.getString("text"));

               usersData.add(userObjects);

           }



        }
        catch (SQLException sqlExeption)
        {
            sqlExeption.printStackTrace();
        }

        return usersData;

    }


    public void deleteInstaRows()
    {
        final String DATABASE_URL = "jdbc:derby:tenzudb";

        try
        {
            Connection connection = DriverManager.getConnection(DATABASE_URL);
            Statement statement = connection.createStatement();

            statement.execute("DELETE FROM instagram_cm");

        }
        catch (Exception e)
        {
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
    }


}
