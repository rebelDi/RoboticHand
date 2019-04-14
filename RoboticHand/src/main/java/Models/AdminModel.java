package Models;

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
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/RoboticHand","root","23011998Diana");
            PreparedStatement ps = con.prepareStatement(query);

            ResultSet rs = ps.executeQuery();
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
