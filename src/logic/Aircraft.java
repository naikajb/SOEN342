package src.logic;

enum Locations{
    TRANSIT, AIRPORT;
}

public class Aircraft {
    private long aircraftID;
    private Airline airline;
    private Locations location;
    private String airportCode;// if location is not in transit, then which airport is it at

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


    public boolean setAirportCode(String airportCode){
        //check first that aircraft is not in transit
        if(location == Locations.AIRPORT){
            this.airportCode = airportCode;
            return true;
        }else{
            //TODO: change this to throwing an error if necessary
            return false;
        }
    }

    //TODO: should this be changed to having a parameter "airportCode" so that I just compare with airportcode attribute
    public boolean checkAircraftAvailability(){
       return location == Locations.AIRPORT;
    }
}
