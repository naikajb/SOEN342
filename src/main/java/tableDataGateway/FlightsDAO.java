package tableDataGateway;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

import logic.*;

public class FlightsDAO {
    private Connection conn;

    public FlightsDAO(Connection conn) {
        this.conn = conn;
    }

    public boolean registerPrivateFlight(Connection conn, String flightNumber, long sourceAirport,
                                         long destinationAirport, LocalDateTime scheduledDeparture, LocalDateTime scheduledArrival,
                                         LocalDateTime actualDeparture, LocalDateTime estimatedArrival, long aircraftId) {
        String sql = "INSERT INTO Flight (flightNumber, sourceAirport, destinationAirport, scheduleDepart, scheduleArrival, actualDepart, actualArrival, aircraftID, Discriminator) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, flightNumber);
            pstmt.setLong(2, sourceAirport);
            pstmt.setLong(3, destinationAirport);
            pstmt.setTimestamp(4, Timestamp.valueOf(scheduledDeparture));
            pstmt.setTimestamp(5, Timestamp.valueOf(scheduledArrival));
            pstmt.setTimestamp(6, Timestamp.valueOf(actualDeparture));
            pstmt.setTimestamp(7, Timestamp.valueOf(estimatedArrival));
            pstmt.setLong(8, aircraftId);
            pstmt.setString(9, "p"); // Discriminator for private flight

            int rowsInserted = pstmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean registerNonPrivateFlight(Connection conn, String flightNumber, long sourceAirport,
            long destinationAirport,
            LocalDateTime scheduledDeparture, LocalDateTime scheduledArrival, LocalDateTime actualDeparture,
            LocalDateTime estimatedArrival,
            long aircraftId, FlightTypes flightType) {

        String sql = "INSERT INTO Flight (flightNumber, sourceAirport, destinationAirport, scheduleDepart, scheduleArrival, actualDepart, actualArrival, aircraftID, Discriminator, flightType) "
                +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, flightNumber);
            pstmt.setLong(2, sourceAirport);
            pstmt.setLong(3, destinationAirport);
            pstmt.setTimestamp(4, Timestamp.valueOf(scheduledDeparture));
            pstmt.setTimestamp(5, Timestamp.valueOf(scheduledArrival));
            pstmt.setTimestamp(6, Timestamp.valueOf(actualDeparture));
            pstmt.setTimestamp(7, Timestamp.valueOf(estimatedArrival));
            pstmt.setLong(8, aircraftId);
            pstmt.setString(9, "np"); // Discriminator for non-private flight
            pstmt.setString(10, flightType.toString()); // Flight type as string

            int rowsInserted = pstmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public ArrayList<Flight> getFlightsDepartingFromAirport(Long airportID) {
        ArrayList<Flight> flights = new ArrayList<>();
        String sql = "SELECT * FROM Flight WHERE sourceAirport = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, airportID);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Flight flight = null;
                int id = rs.getInt("id");
                String flightNum = rs.getString("flightNumber");
                Long aircraftId = (long) rs.getInt("aircraftID");
                String type = rs.getString("Discriminator");
                Long srcAiport = (long) rs.getInt("sourceAirport");
                Long destAirport = (long) rs.getInt("destinationAirport");
                LocalDateTime scheduledDepart = rs.getTimestamp("scheduleDepart").toLocalDateTime();
                LocalDateTime scheduledArr = rs.getTimestamp("scheduleArrival").toLocalDateTime();
                LocalDateTime actualDepart = rs.getTimestamp("actualDepart").toLocalDateTime();
                LocalDateTime actualArr = rs.getTimestamp("actualArrival").toLocalDateTime();

                if (type.equals("p")) {
                    flight = new PrivateFlight(id, flightNum, srcAiport, destAirport, scheduledDepart, scheduledArr,
                            actualDepart, actualArr, aircraftId);
                } else if (type.equals("np")) {
                    FlightTypes flightType = FlightTypes.valueOf(rs.getString("flightType"));
                    flight = new NonPrivateFlight(id, flightNum, srcAiport, destAirport, scheduledDepart, scheduledArr,
                            actualDepart, actualArr, aircraftId, flightType);
                }

                flights.add(flight);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return flights;
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

    public boolean hasFlightWithDestinationAirport(long destinationAirportID) {
        boolean hasFlight = false;
        String sql = "SELECT COUNT(*) FROM Flight WHERE destinationAirport = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, destinationAirportID);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int count = rs.getInt(1);
                if (count > 0) {
                    hasFlight = true;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return hasFlight;
    }

    public boolean hasFlightWithScheduledArrival(LocalDateTime scheduledArrival) {
        boolean hasFlight = false;
        String sql = "SELECT COUNT(*) FROM Flight WHERE scheduleArrival = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setTimestamp(1, Timestamp.valueOf(scheduledArrival));
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int count = rs.getInt(1);
                if (count > 0) {
                    hasFlight = true;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return hasFlight;
    }

    // Same as before, but for private flights
    // Without flightType: Commercial or Cargo
    public ArrayList<PrivateFlight> fetchPrivateFlights(long sourceAirportId, long destAirportId) {
        ArrayList<PrivateFlight> flights = new ArrayList<>();
        // Selects only non-private flights with Discriminator = 'np'
        String sql = "SELECT * FROM Flight WHERE sourceAirport = ? AND destinationAirport = ? AND Discriminator = 'p'";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, sourceAirportId);
            pstmt.setLong(2, destAirportId);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                long id = rs.getLong("id");
                String flightNumber = rs.getString("flightNumber");
                long aircraftID = rs.getLong("aircraftID");
                LocalDateTime scheduleDepart = rs.getTimestamp("scheduleDepart").toLocalDateTime();
                LocalDateTime scheduleArrival = rs.getTimestamp("scheduleArrival").toLocalDateTime();
                LocalDateTime actualDepart = rs.getTimestamp("actualDepart") != null
                        ? rs.getTimestamp("actualDepart").toLocalDateTime()
                        : null;
                LocalDateTime actualArrival = rs.getTimestamp("actualArrival") != null
                        ? rs.getTimestamp("actualArrival").toLocalDateTime()
                        : null;

                PrivateFlight flight = new PrivateFlight(id, flightNumber, sourceAirportId, destAirportId,
                        scheduleDepart, scheduleArrival, actualDepart, actualArrival, aircraftID);
                flights.add(flight);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return flights;
    }
}