package tableDataGateway;

import logic.Actors;
import logic.Airport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ActorsDAO {
    private Connection conn;

    public ActorsDAO(Connection conn) {
        this.conn = conn;
    }

    public String[] getUserInfo(String username, String password) {
        String[] userInfo = null;

        String sql = "SELECT * FROM Actors WHERE Username = ? AND Password = ?;";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);

            ResultSet result = pstmt.executeQuery();

            // Check if result has at least one row
            if (result.next()) {
                userInfo = new String[4];
                userInfo[0] = String.valueOf(result.getInt("id"));
                userInfo[1] = result.getString("Discriminator");
                userInfo[2] = String.valueOf(result.getInt("AirportID"));
                userInfo[3] = String.valueOf(result.getInt("AirlineID"));
                // No need to close the statement explicitly when using try-with-resources
                return userInfo;
            } else {
                System.out.println("No user found with the provided username and password.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
