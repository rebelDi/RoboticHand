package Models;

import Additions.DBConnection;
import Additions.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class AdminModel {

    public static ArrayList<User> getAllUsers(){
        return formList("SELECT * FROM users WHERE rights = 'U'");
    }

    public static ArrayList<User> getEveryoneExceptSuperAdmin(){
        return formList("SELECT * FROM users WHERE rights = 'U' OR rights = 'A'");
    }

    private static ArrayList<User> formList(String query){
        ArrayList<User> userList = new ArrayList<User>();
        try {
            ResultSet rs = new DBConnection().queryGet(query, new String[]{});
            while (rs.next()) {
                userList.add(new User(rs.getString("login"), rs.getString("name"),
                        rs.getString("surname"), rs.getString("rights").charAt(0)));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return userList;
    }
}
