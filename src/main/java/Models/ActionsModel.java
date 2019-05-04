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


    public static ArrayList<Action> getActionsNameAndAvailability(){
        try {
            String query = "SELECT actionLeap, availability FROM robotaction;";
            ResultSet rs = new DBConnection().queryGet(query, new String[]{});

            ArrayList<Action> actions = new ArrayList<Action>();
            while (rs.next()){
                actions.add(new Action(rs.getString("actionLeap"), rs.getInt("availability")));
            }
            return actions;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }


    public static ArrayList<Action> getAllActions(){
        try {
            String query = "SELECT actionLeap, handAction, leapMin, leapMax, servoDirection, " +
                    "servoMin, servoMax, availability FROM robotaction;";
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

    public static void changeAction(Action action) {
        try {
            String query = "UPDATE robotaction SET handAction = ?, leapMin = ?, leapMax = ?, " +
                    "servoDirection = ?, servoMin = ?, servoMax = ?, availability = ? WHERE actionLeap = ?";
            String[] values = new String[]{String.valueOf(action.getArduinoIndex()), String.valueOf(action.getLeapMinimum()),
                    String.valueOf(action.getLeapMaximum()), String.valueOf(action.getServoDirection()),
                            String.valueOf(action.getServoMinimum()), String.valueOf(action.getServoMaximum()),
                                    String.valueOf(action.getAvailability()), action.getName()};
            new DBConnection().queryUpdate(query, values);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
