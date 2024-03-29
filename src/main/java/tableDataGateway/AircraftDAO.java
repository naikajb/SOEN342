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

    public static Aircraft findAircraftByAirportCode(Connection conn, String airportCode) {
        Aircraft aircraft = null;
        try {
            String sql = "SELECT * FROM Aircraft WHERE airportID = (SELECT id FROM Airport WHERE AirportCode = ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, airportCode);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id");
                Locations location = Locations.valueOf(rs.getString("location"));
                String aircraftCode = rs.getString("aircraftCode");
                Long airlineID = (long) rs.getInt("airlineID");
                Long airportID = (long) rs.getInt("airportID");
                aircraft = new Aircraft(id,location, aircraftCode,airlineID,airportID );

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return aircraft;
    }


}
