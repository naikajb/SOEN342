package src.logic;

enum Locations{
    TRANSIT, AIRPORT;
}

public class Aircraft {
    private long aircraftID;
    private Airline airline;
    private Locations location;

    public Aircraft(long id, Locations loc, Airline air){
        aircraftID = id;
        location = loc;
        airline = air;
    }

    public long getAircraftID(){
        return aircraftID;
    }

    public Airline getAirline(){
        return airline;
    }

    public boolean checkAircraftAvailability(){
       return location == Locations.AIRPORT;
    }
}
