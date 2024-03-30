package tableDataGateway;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AirlineDAO {
    private Connection conn;

    public AirlineDAO(Connection conn) {
        this.conn = conn;
    }

    // with Aircraft
    public String getAirlineNameByAirlineId(long airlineId) {
        String sql = "SELECT name FROM Airline WHERE id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, airlineId);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getString("name");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }
}
