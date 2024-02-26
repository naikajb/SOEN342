package src;

public class Airport {
    String name;
    String airportCode;
    City location;

    public Airport(String n, String code, City loc){
        name = n;
        airportCode = code;
        location = loc;
    }

    public String getName() {
        return name;
    }

    public String getAirportCode() {
        return airportCode;
    }

    public City getLocation() {
        return location;
    }
}
