package Models;

import Additions.DBConnection;
import Additions.PasswordSecurity;
import Additions.User;

import java.sql.*;

public class UserModel {
    public static boolean validate(String name,String pass){
        boolean status = false;
        try {
            String query = "SELECT login FROM users WHERE login = ? AND password = ?;";
            pass = PasswordSecurity.hashPassword(pass);
            String[] values = new String[]{name, pass};
            ResultSet rs = new DBConnection().queryGet(query, values);
            status = rs.next();
        } catch (Exception e) {
            System.out.println(e);
        }
        return status;
    }

    public static boolean signUp(String login, String password, String name, String surname, String secretQuestion, String secretAnswer){
        try {
            String query = "INSERT INTO users (login, password, name, surname, rights, secretQuestion, secretAnswer) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?); ";
            String[] values = new String[]{login, PasswordSecurity.hashPassword(password), name, surname,
                    "U", secretQuestion, PasswordSecurity.hashPassword(secretAnswer)};
            new DBConnection().queryUpdate(query, values);
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    public static boolean checkForRepetitiveLogin(String login){
        boolean status = false;
        try {
            String query = "SELECT login FROM users WHERE login = ?;";
            String[] values = new String[]{login};

            ResultSet rs = new DBConnection().queryGet(query, values);
            status = rs.next();
        } catch (Exception e) {
            System.out.println(e);
        }
        return status;
    }

    public static String getRightsByLogin(String login){
        String rights = "";

        try {
            String query = "SELECT rights FROM users WHERE login = ?;";
            String[] values = new String[]{login};

            ResultSet rs = new DBConnection().queryGet(query, values);
            while (rs.next()) {
                rights = rs.getString("rights");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return rights;
    }

    public static User getInfoAboutUser(String login){
        User user = null;

        try {
            String query = "SELECT * FROM users WHERE login = ?;";
            String[] values = new String[]{login};

            ResultSet rs = new DBConnection().queryGet(query, values);
            while (rs.next()) {
                user = new User(rs.getString("login"), rs.getString("password"),
                        rs.getString("name"), rs.getString("surname"),
                        rs.getString("rights").charAt(0), rs.getString("secretQuestion"),
                        rs.getString("secretAnswer"));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return user;
    }

    public static void updateUser(String login, String rights){
        try {
            String query = "UPDATE users SET login = ?, rights = ? WHERE login = ?";
            String[] values = new String[]{login, rights, login};
            new DBConnection().queryUpdate(query, values);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static boolean validatePasswordAnswer(String login, String password, String answer){
        try {
            String query = "SELECT password, secretAnswer FROM users WHERE login = ?;";
            String[] values = new String[]{login};

            ResultSet rs = new DBConnection().queryGet(query, values);
            while (rs.next()) {
                String dbPassword = rs.getString("password");
                String dbAnswer = rs.getString("secretAnswer");
                if(dbPassword.equals(PasswordSecurity.hashPassword(password)) && dbAnswer.equals(PasswordSecurity.hashPassword(answer))){
                    return true;
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    public static boolean updateUserInfo(String oldLogin, String login, String password, String name, String surname){
        try {
            String query = "UPDATE users SET password = ?, name = ?, surname = ?, login = ? WHERE login = ?";
            String[] values = new String[]{PasswordSecurity.hashPassword(password), name, surname, login, oldLogin};
            new DBConnection().queryUpdate(query, values);

            query = "UPDATE users SET password = ?, name = ?, surname = ?, login = ? WHERE login = ?";
            values = new String[]{login, oldLogin};
            new DBConnection().queryUpdate(query, values);
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }
}
