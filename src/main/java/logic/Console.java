package logic;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

import tableDataGateway.ActorsDAO;
import tableDataGateway.AircraftDAO;
import tableDataGateway.AirlineDAO;
import tableDataGateway.CityDAO;
import tableDataGateway.FlightsDAO;
import tableDataGateway.MultiTableFct;
import dataSource.DatabaseConnector;
import dataSource.DatabaseInitializer;

public class Console {

    static Scanner scanner = new Scanner(System.in);
    public static ArrayList<Actors> accounts = new ArrayList<>();

    private static ArrayList<Flight> flightList = new ArrayList<Flight>();
    private static ArrayList<Airport> airportList = new ArrayList<Airport>();
    private static ArrayList<Airline> airlinesList = new ArrayList<>();

    // Who: All users
    // What: basic flight info: flightNumber, source, destination
    // Which Flights: Non-Private Flights
    public static String viewBasicInfo(Airport source, Airport destination, Connection conn) {
        StringBuilder info = new StringBuilder();
        FlightsDAO basicFlightInfo = new FlightsDAO(conn);

        ArrayList<NonPrivateFlight> flights = basicFlightInfo.fetchNonPrivateFlights(source.getId(),
                destination.getId());

        if (flights.isEmpty()) {
            return "No non-private flights found from " + source.getName() + " to " + destination.getName() + ".";
        }

        for (NonPrivateFlight flight : flights) {
            info.append("Flight Number: ").append(flight.getFlightNumber())
                    .append(", Source: ").append(source.getName())
                    .append(", Destination: ").append(destination.getName())
                    .append("\n");
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
            return "No non-private flights found from " + source.getName() + " to " + destination.getName() + ".";
        }

        for (NonPrivateFlight flight : flights) {
            // Fetching additional details
            String aircraftCode = aircraftInfo.getAircraftCodeByAircraftId(flight.getAircraftId());
            long airlineId = aircraftInfo.getAirlineIdByAircraftId(flight.getAircraftId());
            String airlineName = airlineInfo.getAirlineNameByAirlineId(airlineId);

            info.append("Flight Number: ").append(flight.getFlightNumber())
                    .append(", Airline: ").append(airlineName)
                    .append(", Aircraft: ").append(aircraftCode)
                    .append(", Source: ").append(source.getName())
                    .append(", Destination: ").append(destination.getName())
                    .append(", Scheduled Departure: ").append(flight.getScheduledDeparture().toString())
                    .append(", Actual Departure: ")
                    .append(flight.getActualDeparture() != null ? flight.getActualDeparture().toString()
                            : "HAS NOT DEPARTED YET")
                    .append(", Scheduled Arrival: ").append(flight.getScheduledArrival().toString())
                    .append(", Estimated Arrival: ").append(flight.getEstimatedArrival().toString())
                    .append("\n");
        }

        return info.toString();
    }

    public static String viewPrivateInfo(Airport source, Airport destination) {
        ArrayList<NonPrivateFlight> temp = new ArrayList<NonPrivateFlight>();
        String info = "";
        for (int i = 0; i < flightList.size(); i++) {
            if (flightList.get(i).getSource() == source && flightList.get(i).getDestination() == destination
                    && flightList.get(i) instanceof NonPrivateFlight) {
                temp.add((NonPrivateFlight) flightList.get(i));
            }
        }
        if (temp.isEmpty()) {
            info += "ERROR: There is no flight that matches the Source/Destination Airport in the flight catalog";
        } else {
            for (int i = 0; i < temp.size(); i++) {
                info += "Flight: " + temp.get(i).getFlightNumber() + "\n";
                info += "Source: " + temp.get(i).getSource().getName() + "\n";
                info += "Destination: " + temp.get(i).getDestination().getName() + "\n";
                info += "Aircraft ID: " + temp.get(i).getAircraft().getAircraftID() + "\n";
                info += "Scheduled Arrival: " + temp.get(i).getScheduledArrival() + "\n";
                info += "Actual Departure: " + temp.get(i).getActualDeparture() + "\n";
                info += "Estimated Arrival: " + temp.get(i).getEstimatedArrival() + "\n";
                try {
                    info += "Airline Name: " + temp.get(i).getAircraft().getAirline().getName() + "\n";
                } catch (NullPointerException e) {
                    info += "There is no Airline associated to the Aircraft of the Flight";
                }
            }

        }

        return info;
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
            System.out.println("Please enter your username: ");
            String username = scanner.nextLine();
            System.out.println("Please enter your password: ");
            String password = scanner.nextLine();

            String type = ""; // depending on the type of user, different actions can be done
            boolean validChoice = false;
            int choice = 0; // type of action the user wants to do

            if (username.equals("") && password.equals("")) {
                type += "Non-registered";
            } else {
                // TODO:set the type depending on the username + password in the database
                ActorsDAO userDB = new ActorsDAO(conn);
                String[] info = userDB.getUserInfo(username, password);
                if (info != null) {
                    if (info[1].equals("P")) {
                        user = new AirportAdministrator(Long.valueOf(info[2]), username, password);
                        type = "Airport";
                        System.out.println("Logged in as Airport Administrator " + username);
                    } else if (info[1].equals("L")) {
                        user = new AirlineAdministrator(username, password, Long.valueOf(info[3]));
                        type = "Airline";
                        System.out.println("Logged in as Airline Administrator " + username);
                    } else if (info[1].equals("S")) {
                        user = new SystemAdministrator(username, password);
                        type = "System";
                        System.out.println("Logged in as System Administrator " + username);
                    } else if (info[1].equals("R")) {
                        user = new Users(username, password);
                        user.registered = true;
                        type = "Registered";
                    }
                } else {
                    System.out.println("The info are null");
                }

            }

            switch (type) {
                case "Aiport":
                    displayAdminOperations();
                    choice = scanner.nextInt();

                    while (!validChoice) {
                        if (choice == 1) {

                            System.out.println("Please enter the source city of the flight you'd like to view: ");
                            String sourceCity = scanner.next();
                            System.out.println("source city is: " + sourceCity);

                            System.out.println("Please enter the destination city of the flight you'd like to view: ");
                            String destinationCity = scanner.next();

                            // TODO:find airports in database from the srcCode and destCode
                            MultiTableFct airportForCity = new MultiTableFct(conn);
                            Airport sourceAirport = airportForCity.getAirportCodeForCity(sourceCity);
                            Airport destinationAirport = airportForCity.getAirportCodeForCity(destinationCity);

                            // TODO:call correct viewFlightInfo from found airports

                            validChoice = true;

                        } else if (choice == 2) {

                            // TODO:get user airportCode from database
                            // TODO:call registerPrivateFlight(airportCode);

                            validChoice = true;

                        } else {

                            System.out.println("Please enter a valid choice");

                        }
                    }

                    break;
                case "Airline":
                    displayAdminOperations();
                    choice = scanner.nextInt();

                    while (!validChoice) {
                        if (choice == 1) {
                            System.out.println("Please enter the source city of the flight you'd like to view: ");
                            String sourceCity = scanner.nextLine();

                            System.out
                                    .println("Please enter the destination city of the flight you'd like to view: ");
                            String destinationCity = scanner.nextLine();
                            System.out.println("dfhvbjd");

                            // Find airports in database from the srcCity and destCity
                            MultiTableFct airportForCity = new MultiTableFct(conn);
                            Airport sourceAirport = airportForCity.getAirportCodeForCity(sourceCity);
                            Airport destinationAirport = airportForCity.getAirportCodeForCity(destinationCity);

                            // TODO:call correct viewFlightInfo from found airports

                            validChoice = true;

                        } else if (choice == 2) {

                            System.out.print("Enter the name of your airline as \"Airline-Name\": ");
                            boolean success = registerNonPrivateFlight(scanner.next());
                            if (success) {
                                System.out.println("New flight was successfully added.");
                            } else {
                                System.out.println("Flight was not added. See above error.");
                            }

                            validChoice = true;

                        } else {

                            System.out.println("Please enter a valid choice");

                        }
                    }

                    break;
                case "System":
                    System.out.println("Which operation do you want to perform: " +
                            "\n1. View Flight Informations" +
                            "\n2. Enter Records on an Airport");
                    choice = scanner.nextInt();

                    while (!validChoice) {
                        if (choice == 1) {

                            System.out.println("Please enter the source City of the flight you'd like to view: ");
                            String sourceCity = scanner.nextLine();
                            System.out
                                    .println("Please enter the destination City of the flight you'd like to view: ");
                            String destinationCity = scanner.nextLine();

                            // Find airports in database from the srcCity and destCity
                            MultiTableFct airportForCity = new MultiTableFct(conn);
                            Airport sourceAirport = airportForCity.getAirportCodeForCity(sourceCity);
                            Airport destinationAirport = airportForCity.getAirportCodeForCity(destinationCity);

                            // TODO:call correct viewFlightInfo from found airports

                            validChoice = true;

                        } else if (choice == 2) {

                            // TODO:create enter record on airport method
                            validChoice = true;

                        } else {

                            System.out.println("Please enter a valid choice");

                        }
                    }
                    break;
                case "Non-Registered":
                    System.out.println("Which operation do you want to perform: " +
                            "\n1. View Flight Informations");
                    choice = scanner.nextInt();

                    while (!validChoice) {

                        if (choice == 1) {

                            System.out.println("Please enter the source airport of the flight you'd like to view: ");
                            String sourceCode = scanner.nextLine();
                            System.out
                                    .println("Please enter the destination airport of the flight you'd like to view: ");
                            String destinationCode = scanner.nextLine();

                            // TODO:find airports in database from the srcCode and destCode
                            // TODO:call correct viewFlightInfo from found airports

                            validChoice = true;

                        } else {

                            System.out.println("Please enter a valid choice");

                        }

                    }
                    break;
                case "Registered":
                    System.out.println("Which operation do you want to perform: " +
                            "\n1. View Flight Informations");
                    choice = scanner.nextInt();

                    while (!validChoice) {

                        if (choice == 1) {

                            System.out.println("Please enter the source airport of the flight you'd like to view: ");
                            String sourceCode = scanner.next();
                            System.out
                                    .println("Please enter the destination airport of the flight you'd like to view: ");
                            String destinationCode = scanner.next();

                            // TODO:find airports in database from the srcCode and destCode
                            // TODO:call correct viewFlightInfo from found airports

                            System.out.println("fhsdvhjd");
                            validChoice = true;

                        } else {

                            System.out.println("Please enter a valid choice");

                        }

                    }

                    break;
            }
        }
    }

    // while (!valid) {
    // System.out.println("Please enter the type of user you are:");
    // System.out.println(
    // "1. Registered User \n" +
    // "2. Non-Registered User \n" +
    // "3. Airport Administrator \n" +
    // "4. Airline Administrator\n" +
    // "5. System Administrator \n");
    //
    // userType = Integer.parseInt(scanner.nextLine());
    //
    // switch (userType) {
    // case 1:
    // user = new Users(true);
    // valid = true;
    // break;
    // case 2:
    // user = new Users(false);
    // valid = true;
    // break;
    // case 3:
    // // airport administrators have two operations they can do in console
    // displayAdminOperations();
    // int choice;
    // do {
    // choice = scanner.nextInt();
    // System.out.println("Please enter the Airport code of your Airport: ");
    // String airportCode = scanner.nextLine();
    // // view flight info
    // if (choice == 1) {
    //
    // int count = 0;
    // for (int i = 0; i < airportList.size(); i++) {
    // if (airportList.get(i).getCode().equals(airportCode)) {
    // user = new AirportAdministrator(airportList.get(i), "aiportadmin1", "123");
    // count++;
    // break;
    // }
    // }
    // if (count == 0) {
    // System.out.println("ERROR: The Airport you entered does not exists");
    // }
    // valid = true;
    // } // register a new flight into the database
    // else if (choice == 2) {
    // registerPrivateFlight(airportCode);
    // }
    // } while (choice > 2 || choice < 1);
    // break;
    // case 4:
    // user = new AirlineAdministrator("airlineadmin1", "123", null);
    // displayAdminOperations();
    // choice = scanner.nextInt();
    // if (choice == 2) {
    // System.out.print("Enter the name of your airline as \"Airline-Name\": ");
    // boolean success = registerNonPrivateFlight(scanner.next());
    // if (success) {
    // System.out.println("New flight was successfully added.");
    // } else {
    // System.out.println("Flight was not added. See above error.");
    // }
    // }
    // valid = true;
    // break;
    // case 5:
    // user = new SystemAdministrator("systemadmin1", "123");
    // valid = true;
    // break;
    // default:
    // System.out.println("ERROR: Please select a valid user type");
    // valid = false;
    // }
    //
    // }
    //
    // Airport source = null;
    // Airport destination = null;
    //
    // System.out.println("Please enter the source airport of the flight you'd like
    // to view");
    // String src = scanner.nextLine();
    // System.out.println("Please enter the destination airport of the flight you'd
    // like to view");
    // String dest = scanner.nextLine();
    //
    // for (int i = 0; i < airportList.size(); i++) {
    // if (airportList.get(i).getCode().equals(src)) {
    // source = airportList.get(i);
    // } else if (airportList.get(i).getCode().equals(dest)) {
    // destination = airportList.get(i);
    // }
    // }
    // if (userType == 3) {
    // // user = new AirportAdministrator(airportTest, "admin2", "123");
    // if (user.getAirportLocation().getCode().equals(src) ||
    // user.getAirportLocation().getCode().equals(dest)) {
    // System.out.println(viewPrivateInfo(source, destination));
    // } else {
    // System.out.println(
    // "You cannot view the information on this flight since it's source or
    // destination airport does not match with yours");
    // }
    // } else if (userType == 1 || userType == 4 || userType == 5) {
    // System.out.println(viewFullInfo(source, destination));
    // } else {
    // System.out.println(viewBasicInfo(source, destination));
    // }
    //
    // // Remember to close the connection when done
    // if (conn != null) {
    // try {
    // conn.close();
    // } catch (SQLException ex) {
    // System.out.println(ex.getMessage());
    // }
    // }
    // }

    private static Airport findAirport(String code) {
        for (int i = 0; i < airportList.size(); i++) {
            if (airportList.get(i).getCode().equals(code)) {
                return airportList.get(i);
            }
        }
        return null;
    }

    public static LocalDateTime convertToLocalDateTime(StringTokenizer s) {
        return LocalDateTime.of(Integer.parseInt(String.valueOf(s.nextToken())),
                Integer.parseInt(String.valueOf(s.nextToken())),
                Integer.parseInt(String.valueOf(s.nextToken())),
                Integer.parseInt(String.valueOf(s.nextToken())),
                Integer.parseInt(String.valueOf(s.nextToken())),
                Integer.parseInt(String.valueOf(s.nextToken())));
    }

    private static boolean registerNonPrivateFlight(String airportCode) {
        Aircraft availableAircraft = null;
        Airport currentAirport = null;

        // look through list of airports to find the right one
        // TODO change to database access
        for (int i = 0; i < airportList.size(); i++) {
            if (airportList.get(i).getCode().equals(airportCode)) {
                currentAirport = airportList.get(i);
                // once found, check if this airport has any available aircrafts
                availableAircraft = airportList.get(i).checkAvailableAircraft();
                // if an aircraft is found we can continue with the flight registration no need
                // to keep looping
                if (availableAircraft != null) {
                    break;
                }
            }
        }

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
            ArrayList<PrivateFlight> existingFlights = currentAirport.getListOfFlights();

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

            Airport destAirport = findAirport(destCode);
            if (destAirport != null) {
                System.out.print("Enter the arrival time for this flight: ");
                arrDateTime = convertToLocalDateTime(new StringTokenizer(scanner.next(), "-"));
                // check for a flight with same destination AND arrival time
                for (int i = 0; i < flightList.size(); i++) {
                    if (flightList.get(i).getDestination().equals(destAirport)
                            && flightList.get(i).getScheduledArrival().equals(dateTime)) {
                        System.out.print(
                                "Cannot register this flight because a flight is already landing at the same airport at this time.");
                        return false;
                    }
                }
                // none were found so we can create flight and register it
                PrivateFlight newFlight = new PrivateFlight(currentAirport, destAirport, dateTime, arrDateTime, null,
                        null, availableAircraft);
                flightList.add(newFlight);
                return true;
            } else {
                System.out.print("Cannot register this flight because no airport exists with this code.");
                return false;
            }
        }
    }

    private static boolean registerPrivateFlight(String airportCode) {
        Aircraft availableAircraft = null;
        Airport currentAirport = null;

        // look through list of airports to find the right one
        // TODO change to database access
        for (int i = 0; i < airportList.size(); i++) {
            if (airportList.get(i).getCode().equals(airportCode)) {
                currentAirport = airportList.get(i);
                // once found, check if this airport has any available aircrafts
                availableAircraft = airportList.get(i).checkAvailableAircraft();
                // if an aircraft is found we can continue with the flight registration no need
                // to keep looping
                if (availableAircraft != null) {
                    break;
                }
            }
        }

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
            ArrayList<PrivateFlight> existingFlights = currentAirport.getListOfFlights();

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

            Airport destAirport = findAirport(destCode);
            if (destAirport != null) {
                System.out.print("Enter the arrival time for this flight: ");
                arrDateTime = convertToLocalDateTime(new StringTokenizer(scanner.next(), "-"));
                // check for a flight with same destination AND arrival time
                for (int i = 0; i < flightList.size(); i++) {
                    if (flightList.get(i).getDestination().equals(destAirport)
                            && flightList.get(i).getScheduledArrival().equals(dateTime)) {
                        System.out.print(
                                "Cannot register this flight because a flight is already landing at the same airport at this time.");
                        return false;
                    }
                }
                // none were found so we can create flight and register it
                PrivateFlight newFlight = new PrivateFlight(currentAirport, destAirport, dateTime, arrDateTime, null,
                        null, availableAircraft);
                flightList.add(newFlight);
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
                "\n2. Register a New Flight");
    }

    private static void generateAccounts() {
        accounts.add(new Users("naika", "123"));
        accounts.add(new Users("asmae", "123"));
    }
}
