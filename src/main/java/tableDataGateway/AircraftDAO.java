package tableDataGateway;

import logic.Aircraft;
import logic.Locations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AircraftDAO {
    private Connection conn;

    public AircraftDAO(Connection conn) {
        this.conn = conn;
    }

    public Aircraft findAircraftByAirportCode(String airportCode) {
        Aircraft aircraft = null;

        String sql = "SELECT * FROM Aircraft WHERE airportID = (SELECT id FROM Airport WHERE AirportCode = ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, airportCode);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id");
                Locations location = Locations.valueOf(rs.getString("location"));
                String aircraftCode = rs.getString("aircraftCode");
                Long airlineID = (long) rs.getInt("airlineID");
                Long airportID = (long) rs.getInt("airportID");
                aircraft = new Aircraft(id, location, aircraftCode, airlineID, airportID);

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return aircraft;
    }

    public String getAircraftCodeByAircraftId(long aircraftID) {
        String sql = "SELECT aircraftCode FROM Aircraft WHERE id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, aircraftID);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getString("aircraftCode");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public boolean hasAircraftsInAirline(long airlineID) {
        boolean hasAircrafts = false;
        String sql = "SELECT COUNT(*) FROM Aircraft WHERE airlineID = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, airlineID);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int count = rs.getInt(1);
                if (count > 0) {
                    hasAircrafts = true;
                }

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return hasAircrafts;
    }

    public long getAirlineIdByAircraftId(long aircraftID) {
        String sql = "SELECT airlineID FROM Aircraft WHERE id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, aircraftID);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getLong("airlineID");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return -1;
    }

}