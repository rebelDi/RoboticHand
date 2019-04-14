package Models;

import Additions.Action;
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
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/RoboticHand","root","23011998Diana");

            PreparedStatement ps = con.prepareStatement("SELECT actionLeap, availability FROM robotichand.robotaction;");

            ArrayList<Action> actions = new ArrayList<Action>();
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                actions.add(new Action(rs.getString("actionLeap"), rs.getInt("availability")));
            }
            return actions;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }
}
