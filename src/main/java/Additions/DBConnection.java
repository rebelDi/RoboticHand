package Additions;

import java.sql.*;

public class DBConnection {
    private Connection connection;

    public DBConnection() {
        try {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/RoboticHand?autoReconnect=true&useSSL=false","root","23011998Diana");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet queryGet(String query, String[] values) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(query);

        if(values != null) {
            for (int i = 0; i < values.length; i++) {
                ps.setString(i + 1, values[i]);
            }
        }
        return ps.executeQuery();
    }

    public void queryUpdate(String query, String[] values) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(query);
        if(values != null) {
            for (int i = 0; i < values.length; i++) {
                ps.setString(i + 1, values[i]);
            }
        }
        ps.executeUpdate();
    }
}
