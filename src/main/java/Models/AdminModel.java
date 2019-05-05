package Models;

import Additions.DBConnection;
import Additions.User;
import java.sql.ResultSet;
import java.util.ArrayList;

public class AdminModel {

    public static ArrayList<User> getAllUsers(){
        return formList("SELECT * FROM users WHERE rights = 'U' OR rights = 'B' OR rights = '0'");
    }

    public static ArrayList<User> getEveryoneExceptSuperAdmin(){
        return formList("SELECT * FROM users WHERE rights != 'S'");
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

    public static ArrayList<User> getUsersInWaitingList() {
        return formList("SELECT * FROM users WHERE rights = '0'");
    }
}
