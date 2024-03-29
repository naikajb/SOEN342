package tableDataGateway;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import logic.Airport;
import logic.Flight;
import logic.NonPrivateFlight;
import logic.FlightTypes;

public class FlightsDAO {
    private Connection conn;

    public FlightsDAO(Connection conn) {
        this.conn = conn;
    }

    public ArrayList<NonPrivateFlight> fetchNonPrivateFlights(long sourceAirportId, long destAirportId) {
        ArrayList<NonPrivateFlight> flights = new ArrayList<>();
        // Selects only non-private flights with Discriminator = 'np'
        String sql = "SELECT * FROM Flight WHERE sourceAirport = ? AND destinationAirport = ? AND Discriminator = 'np'";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, sourceAirportId);
            pstmt.setLong(2, destAirportId);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                long id = rs.getLong("id");
                String flightNumber = rs.getString("flightNumber");
                long aircraftID = rs.getLong("aircraftID");
                String flightTypeStr = rs.getString("flightType"); // COMMERCIAL, CARGO
                LocalDateTime scheduleDepart = rs.getTimestamp("scheduleDepart").toLocalDateTime();
                LocalDateTime scheduleArrival = rs.getTimestamp("scheduleArrival").toLocalDateTime();
                LocalDateTime actualDepart = rs.getTimestamp("actualDepart") != null
                        ? rs.getTimestamp("actualDepart").toLocalDateTime()
                        : null;
                LocalDateTime actualArrival = rs.getTimestamp("actualArrival") != null
                        ? rs.getTimestamp("actualArrival").toLocalDateTime()
                        : null;

                FlightTypes flightType = FlightTypes.valueOf(flightTypeStr); // Convert string to enum

                NonPrivateFlight flight = new NonPrivateFlight(id, flightNumber, sourceAirportId, destAirportId,
                        scheduleDepart, scheduleArrival, actualDepart, actualArrival, aircraftID, flightType);
                flights.add(flight);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return flights;
    }
}
