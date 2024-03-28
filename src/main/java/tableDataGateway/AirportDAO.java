package tableDataGateway;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AirportDAO {
    private Connection conn;

    public AirportDAO(Connection conn) {
        this.conn = conn;
    }

    // Get AirportCode by CityID
    public String getAirportCodeByCityId(Long cityId) {
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

}
