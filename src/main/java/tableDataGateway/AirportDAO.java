package tableDataGateway;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import logic.Airport;

public class AirportDAO {
    private Connection conn;

    public AirportDAO(Connection conn) {
        this.conn = conn;
    }

    // Get AirportCode by CityID
    public String getAirportCodeByCityId(long cityId) {
        String sql = "SELECT AirportCode FROM Airport WHERE CityID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, cityId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString("AirportCode");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null; // Airport not found or error occurred
    }

    // Get Airport Object by CityID
    public Airport getAirportByCityId(long cityId) {
        // Query adjusted to select all fields necessary for creating an Airport object
        String sql = "SELECT * FROM Airport WHERE CityID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, cityId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                // Extracting data from ResultSet
                long id = rs.getLong("id");
                String name = rs.getString("name");
                String airportCode = rs.getString("AirportCode");
                long location = rs.getLong("CityID");

                // Creating and returning an Airport object with the extracted data
                Airport airport = new Airport(name, airportCode, location, id);
                // Assuming the `id` field in your Airport class is not final and you have a
                // setter for it
                // as it's not set in the constructor. If it's final, you might need to adjust
                // your Airport class design.
                return airport;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null; // Airport not found or error occurred
    }
}