package Models;

import Additions.Message;
import Additions.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class MessagesModel {
    public static ArrayList<Message> getUserMessages(String login){
        return formList("SELECT * FROM qanda WHERE user = '" + login + "'");
    }

    public static ArrayList<Message> getAllMessages(){
        return formList("SELECT * FROM qanda");
    }

    public static ArrayList<Message> getMessagesForAdmin(){
        return formList("SELECT * FROM qanda WHERE status = 'N'");
    }

    private static ArrayList<Message> formList(String query){
        ArrayList<Message> messageList = new ArrayList<Message>();
        try {
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/RoboticHand","root","23011998Diana");
            PreparedStatement ps = con.prepareStatement(query);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String answer = rs.getString("answer");
                if(answer == null){
                    answer = "No answer yet";
                }
                messageList.add(new Message(rs.getString("user"), rs.getString("question"),
                        answer, rs.getString("status")));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return messageList;
    }

    public static void updateAnswer(String user, String answer, String status, String question) {
        try {
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/RoboticHand?autoReconnect=true&useSSL=false","root","23011998Diana");
            PreparedStatement ps = con.prepareStatement("UPDATE qanda SET answer = ?, status = ? WHERE user = ? AND question = ?");
            ps.setString(1, answer);
            ps.setString(2, status);
            ps.setString(3, user);
            ps.setString(4, question);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void updateAnswer(String login, String newQuestion) {
        try {
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/RoboticHand?autoReconnect=true&useSSL=false","root","23011998Diana");
            PreparedStatement ps = con.prepareStatement("INSERT INTO qanda (user, question, answer, status) VALUES (?, ?, ?, ?); ");
            ps.setString(1, login);
            ps.setString(2, newQuestion);
            ps.setString(3, null);
            ps.setString(4, "N");
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
