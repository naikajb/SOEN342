package dataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {

    public static void initializeSchema(Connection conn) {
        createActorsTable(conn);
        createCityTable(conn);
        createAirportTable(conn);
        createAircraftTable(conn);
        createAirlineTable(conn);
        createFlightTable(conn);
    }

    private static void createActorsTable(Connection conn) {
        String sql = "CREATE TABLE IF NOT EXISTS Actors (" +
                "id INTEGER PRIMARY KEY, " +
                "Registered BOOLEAN NOT NULL, " +
                "Username TEXT NOT NULL, " +
                "Password TEXT NOT NULL, " +
                "Discriminator TEXT, " +
                "AirportID INTEGER, " +
                "AirlineID INTEGER, " +
                "FOREIGN KEY(AirportID) REFERENCES Airport(id), " +
                "FOREIGN KEY(AirlineID) REFERENCES Airline(id));";
        executeUpdate(conn, sql);
    }

    private static void createCityTable(Connection conn) {
        String sql = "CREATE TABLE IF NOT EXISTS City (" +
                "id INTEGER PRIMARY KEY, " +
                "name TEXT NOT NULL, " +
                "Country TEXT NOT NULL, " +
                "Temperature REAL);";
        executeUpdate(conn, sql);
    }

    private static void createAirportTable(Connection conn) {
        String sql = "CREATE TABLE IF NOT EXISTS Airport (" +
                "id INTEGER PRIMARY KEY, " +
                "name TEXT NOT NULL, " +
                "AirportCode TEXT NOT NULL, " +
                "CityID INTEGER, " +
                "FOREIGN KEY(CityID) REFERENCES City(id));";
        executeUpdate(conn, sql);
    }

    private static void createAircraftTable(Connection conn) {
        String sql = "CREATE TABLE IF NOT EXISTS Aircraft (" +
                "id INTEGER PRIMARY KEY, " +
                "location TEXT NOT NULL, " +
                "aircraftCode TEXT NOT NULL, " +
                "airlineID INTEGER, " +
                "airportID INTEGER, " +
                "FOREIGN KEY(airlineID) REFERENCES Airline(id), " +
                "FOREIGN KEY(airportID) REFERENCES Airport(id));";
        executeUpdate(conn, sql);
    }

    private static void createAirlineTable(Connection conn) {
        String sql = "CREATE TABLE IF NOT EXISTS Airline (" +
                "id INTEGER PRIMARY KEY, " +
                "name TEXT NOT NULL);";
        executeUpdate(conn, sql);
    }

    // Discriminator: p for private, np for non-private
    private static void createFlightTable(Connection conn) {
        String sql = "CREATE TABLE IF NOT EXISTS Flight (" +
                "id INTEGER PRIMARY KEY, " +
                "name TEXT NOT NULL, " +
                "flightNumber TEXT NOT NULL, " +
                "aircraftID INTEGER, " +
                "Discriminator TEXT NOT NULL, " +
                "sourceAirport INTEGER, " +
                "destinationAirport INTEGER, " +
                "scheduleDepart DATETIME, " +
                "scheduleArrival DATETIME, " +
                "actualDepart DATETIME, " +
                "actualArrival DATETIME, " +
                "flightType TEXT, " +
                "FOREIGN KEY(aircraftID) REFERENCES Aircraft(id), " +
                "FOREIGN KEY(sourceAirport) REFERENCES Airport(id), " +
                "FOREIGN KEY(destinationAirport) REFERENCES Airport(id));";
        executeUpdate(conn, sql);
    }

    private static void executeUpdate(Connection conn, String sql) {
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
