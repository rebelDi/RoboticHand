package Models;

import Additions.DBConnection;
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
            ResultSet rs = new DBConnection().queryGet(query, new String[]{});
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
            String query = "UPDATE qanda SET answer = ?, status = ? WHERE user = ? AND question = ?";
            String[] values = new String[]{answer, status, user, question};

            new DBConnection().queryUpdate(query, values);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void updateAnswer(String login, String newQuestion) {
        try {
            String query = "INSERT INTO qanda (user, question, answer, status) VALUES (?, ?, ?, ?);";
            String[] values = new String[]{login, newQuestion, null, "N"};

            new DBConnection().queryUpdate(query, values);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
