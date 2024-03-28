package tableDataGateway;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import logic.Airport;

public class CityDAO {
    private Connection conn;

    public CityDAO(Connection conn) {
        this.conn = conn;
    }

    // Get CityID by given City Name
    public long getCityIdByName(String cityName) {
        String sql = "SELECT id FROM City WHERE name = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, cityName);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getLong("id");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0; // City not found or error occurred
    }

}
