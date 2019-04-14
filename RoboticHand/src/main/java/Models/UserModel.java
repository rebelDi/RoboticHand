package Models;

import Additions.PasswordSecurity;
import Additions.User;

import java.sql.*;

public class UserModel {
    public static boolean validate(String name,String pass){
        boolean status = false;
        try {
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/RoboticHand","root","23011998Diana");
            pass = PasswordSecurity.hashPassword(pass);

            PreparedStatement ps = con.prepareStatement("SELECT login FROM users WHERE login = ? AND password = ?;");
            ps.setString(1, name);
            ps.setString(2, pass);
            ResultSet rs = ps.executeQuery();
            status = rs.next();
        } catch (Exception e) {
            System.out.println(e);
        }
        return status;
    }

    public static boolean signUp(String login, String password, String name, String surname, String secretQuestion, String secretAnswer){
        try {
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/RoboticHand?autoReconnect=true&useSSL=false","root","23011998Diana");
            PreparedStatement ps = con.prepareStatement("INSERT INTO users (login, password, name, surname, rights, secretQuestion, secretAnswer) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?); ");
            ps.setString(1, login);
            ps.setString(2, PasswordSecurity.hashPassword(password));
            ps.setString(3, name);
            ps.setString(4, surname);
            ps.setString(5, "U");
            ps.setString(6, secretQuestion);
            ps.setString(7, PasswordSecurity.hashPassword(secretAnswer));

            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    public static boolean checkForRepetitiveLogin(String login){
        boolean status = false;
        try {
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/RoboticHand","root","23011998Diana");

            PreparedStatement ps = con.prepareStatement("SELECT login FROM users WHERE login = ?;");
            ps.setString(1, login);

            ResultSet rs = ps.executeQuery();
            status = rs.next();
        } catch (Exception e) {
            System.out.println(e);
        }
        return status;
    }

    public static String getRightsByLogin(String login){
        String rights = "";

        try {
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/RoboticHand","root","23011998Diana");

            PreparedStatement ps = con.prepareStatement("SELECT rights FROM users WHERE login = ?;");
            ps.setString(1, login);

            ResultSet rs = ps.executeQuery();
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
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/RoboticHand","root","23011998Diana");

            PreparedStatement ps = con.prepareStatement("SELECT * FROM users WHERE login = ?;");
            ps.setString(1, login);

            ResultSet rs = ps.executeQuery();
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
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/RoboticHand?autoReconnect=true&useSSL=false","root","23011998Diana");
            PreparedStatement ps = con.prepareStatement("UPDATE users SET login = ?, rights = ? WHERE login = ?");
            ps.setString(1, login);
            ps.setString(2, rights);
            ps.setString(3, login);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static boolean validatePasswordAnswer(String login, String password, String answer){
        try {
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/RoboticHand","root","23011998Diana");

            PreparedStatement ps = con.prepareStatement("SELECT password, secretAnswer FROM users WHERE login = ?;");
            ps.setString(1, login);

            ResultSet rs = ps.executeQuery();
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
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/RoboticHand?autoReconnect=true&useSSL=false","root","23011998Diana");
            PreparedStatement ps = con.prepareStatement("UPDATE users SET password = ?, name = ?, surname = ?, login = ? WHERE login = ?");
            ps.setString(1, PasswordSecurity.hashPassword(password));
            ps.setString(2, name);
            ps.setString(3, surname);
            ps.setString(4, login);
            ps.setString(5, oldLogin);
            ps.executeUpdate();


            ps = con.prepareStatement("UPDATE qanda SET user = ? WHERE user = ?");
            ps.setString(1, login);
            ps.setString(2, oldLogin);
            ps.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }
}
