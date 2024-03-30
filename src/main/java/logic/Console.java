package logic;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.StringTokenizer;

import tableDataGateway.*;
import dataSource.DatabaseConnector;
import dataSource.DatabaseInitializer;

public class Console {

    static Scanner scanner = new Scanner(System.in);
    public static ArrayList<Actors> accounts = new ArrayList<>();

    private static ArrayList<Flight> flightList = new ArrayList<Flight>();
    private static ArrayList<Airport> airportList = new ArrayList<Airport>();
    private static ArrayList<Airline> airlinesList = new ArrayList<>();

    // Get Airport Object by City Name
    public static Airport getAirportObjectByCityName(String cityName, Connection conn) {
        MultiTableFct airportForCity = new MultiTableFct(conn);

        return airportForCity.getAirportObjectByCityName(cityName);
    }

    // Get user info:
    public static String[] getUserInfo(String username, String password, Connection conn) {
        ActorsDAO userDB = new ActorsDAO(conn);
        return userDB.getUserInfo(username, password);
    }

    // Who: All users
    // What: basic flight info: flightNumber, source, destination
    // Which Flights: Non-Private Flights
    public static String viewBasicInfo(Airport source, Airport destination, Connection conn) {
        StringBuilder info = new StringBuilder();
        FlightsDAO basicFlightInfo = new FlightsDAO(conn);

        ArrayList<NonPrivateFlight> flights = basicFlightInfo.fetchNonPrivateFlights(source.getId(),
                destination.getId());

        if (flights.isEmpty()) {
            return "No non-private flights found from " + source.getName() + " to " + destination.getName() + ".\n";
        }

        for (NonPrivateFlight flight : flights) {
            info.append("Flight Number: ").append(flight.getFlightNumber()).append("\n")
                    .append("Source: ").append(source.getName()).append("\n")
                    .append("Destination: ").append(destination.getName()).append("\n\n");
        }

        return info.toString();
    }

    // Who: Registered Users
    // What: full flight info: flightNumber, source, destination, airline, aircraft,
    // scheduledDeparture, scheduledArrival
    // Which Flights: Non-Private Flights.
    public static String viewFullInfo(Airport source, Airport destination, Connection conn) {
        StringBuilder info = new StringBuilder();
        FlightsDAO fullFlightInfo = new FlightsDAO(conn);
        AircraftDAO aircraftInfo = new AircraftDAO(conn); // Assuming you have this DAO
        AirlineDAO airlineInfo = new AirlineDAO(conn); // Assuming you have this DAO

        ArrayList<NonPrivateFlight> flights = fullFlightInfo.fetchNonPrivateFlights(source.getId(),
                destination.getId());

        if (flights.isEmpty()) {
            return "No non-private flights found from " + source.getName() + " to " + destination.getName() + ".\n";
        }

        for (NonPrivateFlight flight : flights) {
            // Fetching additional details
            String aircraftCode = aircraftInfo.getAircraftCodeByAircraftId(flight.getAircraftId());
            long airlineId = aircraftInfo.getAirlineIdByAircraftId(flight.getAircraftId());
            String airlineName = airlineInfo.getAirlineNameByAirlineId(airlineId);

            info.append("Flight Number: ").append(flight.getFlightNumber()).append("\n")
                    .append("Airline: ").append(airlineName).append("\n")
                    .append("Aircraft: ").append(aircraftCode).append("\n")
                    .append("Flight Type: ").append(flight.getFlightType().toString()).append("\n")
                    .append("Source: ").append(source.getName()).append("\n")
                    .append("Destination: ").append(destination.getName()).append("\n")
                    .append("Scheduled Departure: ").append(flight.getScheduledDeparture().toString()).append("\n")
                    .append("Actual Departure: ")
                    .append(flight.getActualDeparture() != null ? flight.getActualDeparture().toString()
                            : "HAS NOT DEPARTED YET")
                    .append("\n")
                    .append("Scheduled Arrival: ").append(flight.getScheduledArrival().toString()).append("\n")
                    .append("Estimated Arrival: ").append(flight.getEstimatedArrival().toString()).append("\n\n");
        }

        return info.toString();
    }

    public static String viewPrivateInfo(Airport source, Airport destination, Connection conn) {
        StringBuilder info = new StringBuilder();
        FlightsDAO fullFlightInfo = new FlightsDAO(conn);
        AircraftDAO aircraftInfo = new AircraftDAO(conn); // Assuming you have this DAO
        AirlineDAO airlineInfo = new AirlineDAO(conn); // Assuming you have this DAO

        ArrayList<PrivateFlight> flights = fullFlightInfo.fetchPrivateFlights(source.getId(), destination.getId());

        if (flights.isEmpty()) {
            return "No private flights found from " + source.getName() + " to " + destination.getName() + ".\n";
        }

        for (PrivateFlight flight : flights) {
            // Fetching additional details
            String aircraftCode = aircraftInfo.getAircraftCodeByAircraftId(flight.getAircraftId());
            long airlineId = aircraftInfo.getAirlineIdByAircraftId(flight.getAircraftId());
            String airlineName = airlineInfo.getAirlineNameByAirlineId(airlineId);

            info.append("Flight Number: ").append(flight.getFlightNumber()).append("\n")
                    .append("Airline: ").append(airlineName).append("\n")
                    .append("Aircraft: ").append(aircraftCode).append("\n")
                    .append("Source: ").append(source.getName()).append("\n")
                    .append("Destination: ").append(destination.getName()).append("\n")
                    .append("Scheduled Departure: ")
                    .append(flight.getScheduledDeparture() != null ? flight.getScheduledDeparture().toString() : "N/A")
                    .append("\n")
                    .append("Actual Departure: ")
                    .append(flight.getActualDeparture() != null ? flight.getActualDeparture().toString()
                            : "HAS NOT DEPARTED YET")
                    .append("\n")
                    .append("Scheduled Arrival: ")
                    .append(flight.getScheduledArrival() != null ? flight.getScheduledArrival().toString() : "N/A")
                    .append("\n")
                    .append("Estimated Arrival: ")
                    .append(flight.getEstimatedArrival() != null ? flight.getEstimatedArrival().toString() : "N/A")
                    .append("\n\n");
        }

        return info.toString();
    }

    public static void main(String[] args) {
        // Establish a connection to the database
        Connection conn = DatabaseConnector.connect();

        // Create the tables if they don't exist
        DatabaseInitializer.initializeSchema(conn);

        Actors user = null;

        boolean valid = false;
        int userType = 0;

        while (!valid) {
            System.out.print("Please enter your username: ");
            String username = scanner.nextLine();

            System.out.print("Please enter your password: ");
            String password = scanner.nextLine();

            String type = ""; // depending on the type of user, different actions can be done
            boolean validChoice = false;
            boolean validChoice2 = false;
            int choice = 0; // type of action the user wants to do

            if (username.equals("") && password.equals("")) {
                type += "Non-Registered";
                System.out.println(type);
            } else {

                String[] info = getUserInfo(username, password, conn);
                if (info != null) {
                    if (info[1].equals("P")) {
                        user = new AirportAdministrator(Long.valueOf(info[2]), username, password);
                        System.out.println(Long.valueOf(info[2]));
                        type = "Airport";
                        // System.out.println(type);
                        System.out.println("Logged in as Airport Administrator " + username);
                    } else if (info[1].equals("L")) {
                        user = new AirlineAdministrator(username, password, Long.valueOf(info[3]));
                        type = "Airline";
                        System.out.println("Logged in as Airline Administrator " + username);
                        // System.out.println(type);
                    } else if (info[1].equals("S")) {
                        user = new SystemAdministrator(username, password);
                        type = "System";
                        System.out.println("Logged in as System Administrator " + username);
                        // System.out.println(type);
                    } else if (info[1].equals("R")) {
                        user = new Users(username, password);
                        user.registered = true;
                        type = "Registered";
                        // Systsem.out.println(type);
                    }
                } else {
                    System.out.println("The info are null");
                }

            }

            switch (type) {
                case "Airport":
                    while (true) {
                        displayAdminOperations();
                        choice = scanner.nextInt();
                        scanner.nextLine(); // Consume the newline character left after reading the integ

                        if (choice == 3) {
                            System.out.println("Exiting...");
                            break; // Exit the loop, and thus, exit this case block
                        }

                        switch (choice) {
                            case 1:

                                System.out.print("Please enter the source city of the flight you'd like to view: ");
                                String sourceCity = scanner.nextLine();
                                System.out.println("source city is: " + sourceCity);

                                // Find airport in database from the srcCity
                                Airport sourceAirport = getAirportObjectByCityName(sourceCity, conn);

                                // Before proceeding, check airport is not null
                                if (sourceAirport == null) {
                                    System.out.println("The source airport for city " + sourceCity
                                            + " could not be found in the database.");
                                    continue;
                                }

                                System.out
                                        .print("Please enter the destination city of the flight you'd like to view: ");
                                String destinationCity = scanner.nextLine();
                                System.out.println("destination city is: " + destinationCity);

                                // Find airport in database from the destCity
                                Airport destinationAirport = getAirportObjectByCityName(destinationCity, conn);

                                // Before proceeding, check airport is not null
                                if (destinationAirport == null) {
                                    System.out.println("The destination airport for city " + destinationCity
                                            + " could not be found in the database.");
                                    continue;
                                }

                                // call correct viewFlightInfo from found airports
                                System.out.println(
                                        "Please enter if you'd like to see a Private Flight (P) or a non-private Flight NP): ");
                                String wantPrivate = scanner.next();

                                while (!validChoice2) {
                                    if (wantPrivate.equals("P")) {
                                        // Establish value of airport of user
                                        long userAirport = user.getAirportLocation();
                                        // Check if it is equal to source OR destination airport
                                        if (userAirport == sourceAirport.getId()
                                                || userAirport == destinationAirport.getId()) {
                                            System.out
                                                    .println(viewPrivateInfo(sourceAirport, destinationAirport, conn));
                                        } else {
                                            System.out.println(
                                                    "You do not have access to this information since you are not in the airport of the Private Flight that you want to get the info of.");
                                        }
                                        validChoice2 = true;

                                    } else if (wantPrivate.equals("NP")) {
                                        System.out.println(viewFullInfo(sourceAirport, destinationAirport, conn));
                                        validChoice2 = true;
                                    } else {
                                        System.out.println("Please enter a valid choice");
                                        validChoice2 = false;
                                    }
                                }
                                break;

                            case 2:
                                long airportId = ((AirportAdministrator) user).getLocation();
                                AirportDAO airportDAO = new AirportDAO(conn);
                                String airportCode = airportDAO.getAirportCodeById(airportId);
                                boolean success = registerPrivateFlight(airportCode, conn);

                                if (success) {
                                    System.out.println("New flight was successfully added.");
                                } else {
                                    System.out.println("Flight was not added. See above error.");
                                }

                                break;

                            default:
                                System.out.println("Please enter a valid choice");
                        }
                    }
                    break;
                case "Airline":
                    while (true) {
                        displayAdminOperations();
                        choice = scanner.nextInt();
                        scanner.nextLine(); // Consume the newline character left after reading the integ

                        if (choice == 3) {
                            System.out.println("Exiting...");
                            break; // Exit the loop, and thus, exit this case block
                        }

                        switch (choice) {
                            case 1:
                                System.out.print("Please enter the source city of the flight you'd like to view: ");
                                String sourceCity = scanner.nextLine();
                                System.out.println("source city is: " + sourceCity);

                                // Find airport in database from the srcCity
                                Airport sourceAirport = getAirportObjectByCityName(sourceCity, conn);

                                // Before proceeding, check airport is not null
                                if (sourceAirport == null) {
                                    System.out.println("The source airport for city " + sourceCity
                                            + " could not be found in the database.");
                                    continue;
                                }

                                System.out
                                        .print("Please enter the destination city of the flight you'd like to view: ");
                                String destinationCity = scanner.nextLine();
                                System.out.println("destination city is: " + destinationCity);

                                // Find airport in database from the destCity
                                Airport destinationAirport = getAirportObjectByCityName(destinationCity, conn);

                                // Before proceeding, check airport is not null
                                if (destinationAirport == null) {
                                    System.out.println("The destination airport for city " + destinationCity
                                            + " could not be found in the database.");
                                    continue;
                                }

                                // call correct viewFlightInfo from found airports
                                System.out.println(viewFullInfo(sourceAirport, destinationAirport, conn));

                                break;

                            case 2:
                                System.out.print("Enter the source airport of the flight you'd like to register: ");
                                String airportCode = scanner.nextLine();
                                boolean success = registerNonPrivateFlight(airportCode,
                                        ((AirlineAdministrator) user).getAirline());
                                if (success) {
                                    System.out.println("New flight was successfully added.");
                                } else {
                                    System.out.println("Flight was not added. See above error.");
                                }

                                break;

                            default:
                                System.out.println("Please enter a valid choice");
                        }
                    }
                    break;
                case "System":
                    while (true) {
                        displayAdminOperations();
                        choice = scanner.nextInt();
                        scanner.nextLine(); // Consume the newline character left after reading the integ

                        if (choice == 3) {
                            System.out.println("Exiting...");
                            break; // Exit the loop, and thus, exit this case block
                        }

                        switch (choice) {
                            case 1:
                                System.out.print("Please enter the source city of the flight you'd like to view: ");
                                String sourceCity = scanner.nextLine();
                                System.out.println("source city is: " + sourceCity);

                                // Find airport in database from the srcCity
                                Airport sourceAirport = getAirportObjectByCityName(sourceCity, conn);

                                // Before proceeding, check airport is not null
                                if (sourceAirport == null) {
                                    System.out.println("The source airport for city " + sourceCity
                                            + " could not be found in the database.");
                                    continue;
                                }

                                System.out
                                        .print("Please enter the destination city of the flight you'd like to view: ");
                                String destinationCity = scanner.nextLine();
                                System.out.println("destination city is: " + destinationCity);

                                // Find airport in database from the destCity
                                Airport destinationAirport = getAirportObjectByCityName(destinationCity, conn);

                                // Before proceeding, check airport is not null
                                if (destinationAirport == null) {
                                    System.out.println("The destination airport for city " + destinationCity
                                            + " could not be found in the database.");
                                    continue;
                                }

                                // call correct viewFlightInfo from found airports
                                System.out.println(viewFullInfo(sourceAirport, destinationAirport, conn));

                                break;
                            case 2:
                                // Create enter record on airport method

                                System.out.print("Enter an Airport name: ");
                                scanner.nextLine();
                                String name = scanner.nextLine();

                                System.out.print("Enter the city and country where " + name
                                        + " is located as comma seperated values: ");
                                // scanner.nextLine();
                                String location = scanner.nextLine();
                                StringTokenizer st = new StringTokenizer(location, ",");

                                addAirportRecord(name, st.nextToken(), st.nextToken(), conn);

                                break;

                            default:
                                System.out.println("Please enter a valid choice");
                        }
                    }
                    break;
                case "Non-Registered":
                    while (true) {
                        System.out.println("Which operation do you want to perform: " +
                                "\n1. View Flight Informations" +
                                "\n2. Exit");
                        choice = scanner.nextInt();
                        scanner.nextLine(); // Consume the newline character left after reading the integ

                        if (choice == 2) {
                            System.out.println("Exiting...");
                            break; // Exit the loop, and thus, exit this case block
                        }
                        switch (choice) {

                            case 1:

                                System.out.print("Please enter the source city of the flight you'd like to view: ");
                                String sourceCity = scanner.nextLine();
                                System.out.println("source city is: " + sourceCity);

                                // Find airport in database from the srcCity
                                Airport sourceAirport = getAirportObjectByCityName(sourceCity, conn);

                                // Before proceeding, check airport is not null
                                if (sourceAirport == null) {
                                    System.out.println("The source airport for city " + sourceCity
                                            + " could not be found in the database.");
                                    continue;
                                }

                                System.out
                                        .print("Please enter the destination city of the flight you'd like to view: ");
                                String destinationCity = scanner.nextLine();
                                System.out.println("destination city is: " + destinationCity);

                                // Find airport in database from the destCity
                                Airport destinationAirport = getAirportObjectByCityName(destinationCity, conn);

                                // Before proceeding, check airport is not null
                                if (destinationAirport == null) {
                                    System.out.println("The destination airport for city " + destinationCity
                                            + " could not be found in the database.");
                                    continue;
                                }

                                // Call correct viewFlightInfo from found airports
                                System.out.println(viewBasicInfo(sourceAirport, destinationAirport, conn));

                                break;

                            default:

                                System.out.println("Please enter a valid choice");

                        }
                    }
                    break;
                case "Registered":
                    while (true) {
                        System.out.println("Which operation do you want to perform: " +
                                "\n1. View Flight Informations" +
                                "\n2. Exit");
                        choice = scanner.nextInt();
                        scanner.nextLine(); // Consume the newline character left after reading the integ

                        if (choice == 2) {
                            System.out.println("Exiting...");
                            break; // Exit the loop, and thus, exit this case block
                        }

                        switch (choice) {

                            case 1:
                                System.out.print("Please enter the source city of the flight you'd like to view: ");
                                String sourceCity = scanner.nextLine();
                                System.out.println("source city is: " + sourceCity);

                                // Find airport in database from the srcCity
                                Airport sourceAirport = getAirportObjectByCityName(sourceCity, conn);

                                // Before proceeding, check airport is not null
                                if (sourceAirport == null) {
                                    System.out.println("The source airport for city " + sourceCity
                                            + " could not be found in the database.");
                                    continue;
                                }

                                System.out
                                        .print("Please enter the destination city of the flight you'd like to view: ");
                                String destinationCity = scanner.nextLine();
                                System.out.println("destination city is: " + destinationCity);

                                // Find airport in database from the destCity
                                Airport destinationAirport = getAirportObjectByCityName(destinationCity, conn);

                                // Before proceeding, check airport is not null
                                if (destinationAirport == null) {
                                    System.out.println("The destination airport for city " + destinationCity
                                            + " could not be found in the database.");
                                    continue;
                                }

                                // Call correct viewFlightInfo from found airports
                                System.out.println(viewFullInfo(sourceAirport, destinationAirport, conn));

                                break;

                            default:
                                System.out.println("Please enter a valid choice");

                        }
                    }
                    break;
            }
        }

    }

    public static LocalDateTime convertToLocalDateTime(StringTokenizer s) {
        try {
            int year = Integer.parseInt(s.nextToken());
            int month = Integer.parseInt(s.nextToken());
            int day = Integer.parseInt(s.nextToken());
            int hour = Integer.parseInt(s.nextToken());
            int minute = Integer.parseInt(s.nextToken());
            int second = Integer.parseInt(s.nextToken());
            return LocalDateTime.of(year, month, day, hour, minute, second);
        } catch (NumberFormatException | NoSuchElementException | DateTimeException e) {
            // Handle input format errors
            System.out.println("Invalid input format: " + e.getMessage());
            return null;
        }
    }

    private static boolean registerNonPrivateFlight(String airportCode, long airlineID) {
        Connection conn = DatabaseConnector.connect();
        AirportDAO airportDAO = new AirportDAO(conn);
        AircraftDAO aircraftDAO = new AircraftDAO(conn);
        FlightsDAO flightDAO = new FlightsDAO(conn);

        Aircraft availableAircraft = aircraftDAO.findAircraftByAirportCode(airportCode); // available aircraft in
                                                                                         // airport
        Airport currentAirport = airportDAO.getAirportByAirportCode(airportCode);
        boolean airlineHasAircraft = aircraftDAO.hasAircraftsInAirline(airlineID); // available aircraft in the
                                                                                   // airline
        // don't continue if no aircraft was found

        if (availableAircraft == null || !airlineHasAircraft) {
            System.out.println("Unable to register a new flight since no aircrafts are currently available.");
            return false;
        } else {
            boolean alreaydExists = false;// used to hold T/F if the time for departure/arrival already exists
            LocalDateTime dateTime = null;

            System.out.print("Enter a date and time of departure for this flight: (yyyy-MM-dd-HH-mm-ss): ");
            StringTokenizer timeInput = new StringTokenizer(scanner.next(), "-");
            dateTime = convertToLocalDateTime(timeInput);

            // get the flights that are departing from this airport
            ArrayList<Flight> existingFlights = flightDAO.getFlightsDepartingFromAirport(currentAirport.getId());

            // check if any flights are departing at the same time
            for (int i = 0; i < existingFlights.size(); i++) {
                if (existingFlights.get(i).getScheduledDeparture() == dateTime) {
                    alreaydExists = true;
                    break;
                }
            }

            if (alreaydExists) {
                System.out.println(
                        "Cannot register this flight because a flight is already departing or landing at the same time.");
                return false;
            }

            // if for loop wasn't broken then all the flights were fine
            // check for arrival time and airport

            System.out.print("Enter the destination airport code of this flight: ");
            String destCode = scanner.next();
            LocalDateTime arrDateTime = null;

            Airport destAirport = airportDAO.getAirportByAirportCode(destCode);
            if (destAirport != null) {
                System.out.print("Enter the arrival time for this flight: ");
                arrDateTime = convertToLocalDateTime(new StringTokenizer(scanner.next(), "-"));
                // check for a flight with same destination AND arrival time

                boolean sameDest = flightDAO.hasFlightWithDestinationAirport(destAirport.getId());
                boolean sameTime = flightDAO.hasFlightWithScheduledArrival(arrDateTime);
                for (int i = 0; i < flightList.size(); i++) {
                    if (sameDest && sameTime) {
                        System.out.print(
                                "Cannot register this flight because a flight is already landing at the same airport at this time.");
                        return false;
                    }
                }

                System.out.println("Enter the type of the flight: ");
                String flightType = scanner.nextLine();
                // none were found so we can create flight and register it
                flightDAO.registerNonPrivateFlight(conn, "AC 456", currentAirport.getId(), destAirport.getId(),
                        dateTime, arrDateTime, null, null, availableAircraft.getId(), FlightTypes.valueOf(flightType));
                return true;
            } else {
                System.out.print("Cannot register this flight because no airport exists with this code.");
                return false;
            }
        }
    }

    private static boolean registerPrivateFlight(String airportCode, Connection conn) {
        AirportDAO airportDAO = new AirportDAO(conn);
        AircraftDAO aircraftDAO = new AircraftDAO(conn);
        FlightsDAO flightDAO = new FlightsDAO(conn);

        Aircraft availableAircraft = aircraftDAO.findAircraftByAirportCode(airportCode);
        Airport currentAirport = airportDAO.getAirportByAirportCode(airportCode);

        // don't continue if no aircraft was found
        if (availableAircraft == null) {
            System.out.println("Unable to register a new flight since no aircrafts are currently available.");
            return false;
        } else {
            boolean alreaydExists = false;// used to hold T/F if the time for departure/arrival already exists
            LocalDateTime dateTime = null;

            System.out.print("Enter a date and time of departure for this flight: (yyyy-MM-dd-HH-mm-ss): ");
            StringTokenizer timeInput = new StringTokenizer(scanner.next(), "-");
            dateTime = convertToLocalDateTime(timeInput);

            // get the flights that are departing from this airport
            ArrayList<Flight> existingFlights = flightDAO.getFlightsDepartingFromAirport(currentAirport.getId());

            // check if any flights are departing at the same time
            for (int i = 0; i < existingFlights.size(); i++) {
                if (existingFlights.get(i).getScheduledDeparture() == dateTime) {
                    alreaydExists = true;
                    break;
                }
            }

            if (alreaydExists) {
                System.out.println(
                        "Cannot register this flight because a flight is already departing or landing at the same time.");
                return false;
            }

            // if for loop wasn't broken then all the flights were fine
            // check for arrival time and airport
            System.out.print("Enter the destination airport code of this flight: ");
            String destCode = scanner.next();
            LocalDateTime arrDateTime = null;

            Airport destAirport = airportDAO.getAirportByAirportCode(destCode);
            if (destAirport != null) {
                System.out.print("Enter the arrival time for this flight: ");
                arrDateTime = convertToLocalDateTime(new StringTokenizer(scanner.next(), "-"));
                // check for a flight with same destination AND arrival time
                boolean sameDest = flightDAO.hasFlightWithDestinationAirport(destAirport.getId());
                boolean sameTime = flightDAO.hasFlightWithScheduledArrival(arrDateTime);
                for (int i = 0; i < flightList.size(); i++) {
                    if (sameDest && sameTime) {
                        System.out.print(
                                "Cannot register this flight because a flight is already landing at the same airport at this time.");
                        return false;
                    }
                }

                // none were found so we can create flight and register it
                flightDAO.registerPrivateFlight(conn, "AC 456", currentAirport.getId(), destAirport.getId(), dateTime,
                        arrDateTime, dateTime, arrDateTime, availableAircraft.getId());
                return true;
            } else {
                System.out.print("Cannot register this flight because no airport exists with this code.");
                return false;
            }

        }

    }

    private static void displayAdminOperations() {
        System.out.println("Which operation do you want to perform: " +
                "\n1. View Flight Informations" +
                "\n2. Register a New Flight" +
                "\n3. Exit");
    }

    private static void addAirportRecord(String name, String city, String Country, Connection connection) {

        CityDAO cityData = new CityDAO(connection);
        // get the id city input
        long cityId = cityData.getCityID(city);

        // if the city doesn't already exist
        if (cityId == 0) {
            // request temperature info for the city
            System.out.print("Enter the current temperature info of this city: ");
            Double temp = scanner.nextDouble();
            // create entry for the city
            cityId = cityData.registerCity(city, Country, temp);
        }

        // create entry for the airport
        AirportDAO airportData = new AirportDAO(connection);
        System.out.println("Enter a three letter code for the airport: ");
        // scanner.nextLine();

        String code = scanner.next();
        airportData.registerAirport(cityId, name, code);

        System.out.println("New airport was successfully added to the database.");
    }

}