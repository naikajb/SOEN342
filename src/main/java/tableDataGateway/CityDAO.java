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
    public Long getCityIdByName(String cityName) {
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
        return null; // City not found or error occurred
    }

    // Get Airport object by CityID
    public Airport getAirportByCityId(Long cityId) {
        String sql = "SELECT * FROM Airport WHERE CityID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, cityId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Long id = rs.getLong("id");
                String name = rs.getString("name");
                String airportCode = rs.getString("AirportCode");
                // Assuming the Airport constructor accepts null for the City
                // You may need to adjust this if the Airport constructor requires a non-null
                // City.
                return new Airport(id, name, airportCode, null);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null; // Airport not found or error occurred
    }

}
