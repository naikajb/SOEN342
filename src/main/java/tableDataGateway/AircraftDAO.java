package tableDataGateway;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AircraftDAO {
    private Connection conn;

    public AircraftDAO(Connection conn) {
        this.conn = conn;
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
