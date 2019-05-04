package Models;

import Additions.Action;
import Additions.DBConnection;
import Additions.PasswordSecurity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ActionsModel {
    public static ArrayList<Action> getAllActions(){
        boolean status = false;
        try {
            String query = "SELECT actionLeap, handAction, leapMin, leapMax, servoDirection, " +
                    "servoMin, servoMax, availability FROM robotichand.robotaction;";
            ResultSet rs = new DBConnection().queryGet(query, new String[]{});

            ArrayList<Action> actions = new ArrayList<Action>();
            while (rs.next()){
                actions.add(new Action(rs.getString("actionLeap"), rs.getInt("handAction"), rs.getInt("leapMin"),
                        rs.getInt("leapMax"), rs.getInt("servoDirection"), rs.getInt("servoMin"),
                        rs.getInt("servoMax"), rs.getInt("availability")));
            }
            return actions;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }
}
