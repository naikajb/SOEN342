package src.logic;

import src.dataSource.DataGateway;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class Console {

    static DataGateway dataAccess;
    static Scanner scanner = new Scanner(System.in);
    public static ArrayList<Actors> accounts = new ArrayList<>();

    private static ArrayList<Flight> flightList = new ArrayList<Flight>();
    private static ArrayList<Airport> airportList = new ArrayList<Airport>();
    private static  ArrayList<Airline> airlinesList = new ArrayList<>();

    public static String viewBasicInfo(Airport source, Airport destination){
        ArrayList<NonPrivateFlight> temp = new ArrayList<NonPrivateFlight>();
        String info = "";
        for(int i=0;i < flightList.size();i++){
            if(flightList.get(i).getSource() == source && flightList.get(i).getDestination() == destination && flightList.get(i) instanceof NonPrivateFlight ){
                temp.add((NonPrivateFlight) flightList.get(i));
            }
        }
        if(temp.isEmpty()){
            info+= "ERROR: There is no flight that matches the Source/Destination Airport in the flight catalog or there is no Non-Private flights in the catalog.";
        }else{
            for(int i=0;i<temp.size();i++){
                info += "Flight: " + temp.get(i).getFlightNumber() + "\n";
                info += "Source: " + temp.get(i).getSource().getName() + "\n";
                info += "Destination: " + temp.get(i).getDestination().getName() + "\n";
                info += "Scheduled Departure: " + temp.get(i).getScheduledDeparture() + "\n";
                info += "Scheduled Arrival: " + temp.get(i).getScheduledArrival() + "\n";
                info += "Actual Departure: " + temp.get(i).getActualDeparture() + "\n";
                info += "Estimated Arrival: " + temp.get(i).getEstimatedArrival() + "\n";
            }

        }

        return info;
    }

    public static String viewFullInfo(Airport source, Airport destination){
        ArrayList<NonPrivateFlight> temp = new ArrayList<NonPrivateFlight>();
        String info = "";
        for(int i=0;i < flightList.size();i++){
            if(flightList.get(i).getSource() == source && flightList.get(i).getDestination() == destination && flightList.get(i) instanceof NonPrivateFlight ){
                temp.add((NonPrivateFlight) flightList.get(i));
            }
        }
        if(temp.isEmpty()){
            info+= "ERROR: There is no flight that matches the Source/Destination Airport in the flight catalog or there is no Non-Private flights in the catalog.";
        }else{
            for(int i=0;i<temp.size();i++){
                info += "Flight: " + temp.get(i).getFlightNumber() + "\n";
                info += "Source: " + temp.get(i).getSource().getName() + "\n";
                info += "Destination: " + temp.get(i).getDestination().getName() + "\n";
                info += "Aircraft ID: " + temp.get(i).getAircraft().getAircraftID() + "\n";
                info += "Scheduled Arrival: " + temp.get(i).getScheduledArrival() + "\n";
                info += "Actual Departure: " + temp.get(i).getActualDeparture() + "\n";
                info += "Estimated Arrival: " + temp.get(i).getEstimatedArrival() + "\n";
                try{
                    info += "Airline Name: " + temp.get(i).getAircraft().getAirline().getName() + "\n";
                }catch(NullPointerException e){
                    info += "There is no Airline associated to the Aircraft of the Flight";
                }
            }

        }

        return info;
    }

    public static String viewPrivateInfo(Airport source, Airport destination){
        ArrayList<NonPrivateFlight> temp = new ArrayList<NonPrivateFlight>();
        String info = "";
        for(int i=0;i < flightList.size();i++){
            if(flightList.get(i).getSource() == source && flightList.get(i).getDestination() == destination && flightList.get(i) instanceof NonPrivateFlight ){
                temp.add((NonPrivateFlight) flightList.get(i));
            }
        }
        if(temp.isEmpty()){
            info+= "ERROR: There is no flight that matches the Source/Destination Airport in the flight catalog";
        }else{
            for(int i=0;i<temp.size();i++){
                info += "Flight: " + temp.get(i).getFlightNumber() + "\n";
                info += "Source: " + temp.get(i).getSource().getName() + "\n";
                info += "Destination: " + temp.get(i).getDestination().getName() + "\n";
                info += "Aircraft ID: " + temp.get(i).getAircraft().getAircraftID() + "\n";
                info += "Scheduled Arrival: " + temp.get(i).getScheduledArrival() + "\n";
                info += "Actual Departure: " + temp.get(i).getActualDeparture() + "\n";
                info += "Estimated Arrival: " + temp.get(i).getEstimatedArrival() + "\n";
                try{
                    info += "Airline Name: " + temp.get(i).getAircraft().getAirline().getName() + "\n";
                }catch(NullPointerException e){
                    info += "There is no Airline associated to the Aircraft of the Flight";
                }
            }

        }

        return info;
    }

    public static void main(String[] args)  {
//        try {
//            dataAccess = new DataGateway();
//        }catch (SQLException e){
//            System.out.println(e.getMessage());
//        }
        //flight test
        City montreal = new City("Montreal", "Canada", -10.0);
        City newYork = new City("New York", "USA", -11.5);
        City chicago = new City("Chicago", "USA", -10.0);


        Airport airport1 = new Airport("Pierre-Elliot Trudeau", "YUL", montreal);
        Airport airport2 = new Airport("John-F Kennedy", "JFK", newYork);
        Airport airportTest = new Airport("O'Hare International Airport", "ORD", chicago);


        airportList.add(airport1);
        airportList.add(airport2);
        airportList.add(airportTest);


        LocalDateTime scheduledDep = LocalDateTime.of(2025, 6, 30, 15, 45);
        LocalDateTime scheduledDArr = LocalDateTime.of(2025, 6, 30, 17, 15);
        LocalDateTime actualDep = LocalDateTime.of(2025, 6, 30, 15, 48);
        LocalDateTime estimatedArr = LocalDateTime.of(2025, 6, 30, 17, 18);

        Aircraft boeing = new Aircraft(1234567890L, Locations.AIRPORT,null);

        Airline airlineTest = new Airline("Air Canada", (new ArrayList<Aircraft>()));
        airlineTest.getAircraftList().add(boeing);

        NonPrivateFlight flight1 = new NonPrivateFlight("DC245", Types.CARGO , airport1,airport2,scheduledDep,scheduledDArr,actualDep,estimatedArr, boeing);
        flightList.add(flight1);


        Actors user = null;

        boolean valid = false;
        int userType = 0;


        while(!valid) {
            System.out.println("Please enter the type of user you are:");
            System.out.println(
                    "1. Registered User \n" +
                    "2. Non-Registered User \n" +
                    "3. Airport Administrator \n" +
                    "4. Airline Administrator\n" +
                    "5. System Administrator \n");

            userType = Integer.parseInt(scanner.nextLine());

            switch (userType) {
                case 1:
                    user = new Users(true);
                    valid = true;
                    break;
                case 2:
                    user = new Users(false);
                    valid = true;
                    break;
                case 3:
                    //airport administrators have two operations they can do in console
                    System.out.println("Which operation do you want to perform: " +
                            "\n1. View Flight Informations" +
                            "\n2. Register a New Flight");
                    int choice;
                    do {
                        choice = scanner.nextInt();
                        //view flight info
                        if (choice == 1) {
                            System.out.println("Please enter the Airport code of your Airport: ");
                            String airportCode = scanner.nextLine();
                            int count = 0;
                            for (int i = 0; i < airportList.size(); i++) {
                                if (airportList.get(i).getCode().equals(airportCode)) {
                                    user = new AirportAdministrator(airportList.get(i), "aiportadmin1", "123");
                                    count++;
                                    break;
                                }
                            }
                            if (count == 0) {
                                System.out.println("ERROR: The Airport you entered does not exists");
                            }
                            valid = true;
                        }//register a new flight into the database
                        else if (choice == 2) {
                            registerFlight();
                        }
                    }while (choice > 2 || choice < 1);
                    break;
                case 4:
                    user = new AirlineAdministrator("airlineadmin1", "123", null );
                    valid = true;
                    break;
                case 5:
                    user = new SystemAdministrator("systemadmin1", "123" );
                    valid = true;
                    break;
                default:
                    System.out.println("ERROR: Please select a valid user type");
                    valid = false;
            }

        }

        Airport source = null;
        Airport destination = null;

        System.out.println("Please enter the source airport of the flight you'd like to view");
        String src = scanner.nextLine();
        System.out.println("Please enter the destination airport of the flight you'd like to view");
        String dest = scanner.nextLine();

        for(int i=0;i< airportList.size();i++){
            if(airportList.get(i).getCode().equals(src)){
                source = airportList.get(i);
            }else if(airportList.get(i).getCode().equals(dest)){
                destination = airportList.get(i);
            }
        }
                if(userType==3){
//            user = new AirportAdministrator(airportTest, "admin2", "123");
            if(user.getAirportLocation().getCode().equals(src) || user.getAirportLocation().getCode().equals(dest)){
                System.out.println(viewPrivateInfo(source,destination));
            }else{
                System.out.println("You cannot view the information on this flight since it's source or destination airport does not match with yours");
            }
        }else if(userType == 1 || userType == 4 || userType == 5){
            System.out.println(viewFullInfo(source,destination));
        }else{
            System.out.println(viewBasicInfo(source,destination));
        }
    }

    private static void registerFlight() {

        System.out.println("Please enter the Airport code of your Airport: ");
        String airportCode = scanner.nextLine();
        Aircraft availableAircraft = null;

        //look through list of airports to find the right one
        //TODO change to database access
        for(int i = 0; i < airportList.size(); i++){
            if (airportList.get(i).getCode().equals(airportCode)){
                //once found, check if this airport has any available aircrafts
                availableAircraft = airportList.get(i).checkAvailableAircraft();
                //if an aircraft is found we can continue with the flight registration no need to keep looping
                if (availableAircraft != null){
                    break;
                }
            }
        }

        //don't continue if no aircraft was found
        if(availableAircraft == null){
            System.out.println("Unable to register a new flight since no aircrafts are currently available.");
        }else{
            System.out.print("Enter flight number: ");

        }

    }


    private static void generateAccounts() {
        accounts.add(new Users( "naika", "123"));
        accounts.add(new Users("asmae", "123"));
    }
}