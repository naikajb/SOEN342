package src.logic;

import java.util.ArrayList;

public class Airport {

    private String name;
    private String airportCode;
    private City location;
    private ArrayList<PrivateFlight> privateFlights;

    public Airport(String n, String code, City loc){
        name = n;
        airportCode = code;
        location = loc;
        privateFlights = null;
    }

    public String getName(){
        return name;
    }

    public String getCode(){
        return airportCode;
    }

    public City getLocation(){
        return location;
    }

    public void addPrivateFlight(PrivateFlight flight){
        if(privateFlights == null) {
            privateFlights = new ArrayList<>();
        }
        privateFlights.add(flight);
    }

    public void checkAvailableAircraft() {
        for(int i = 0; i < privateFlights.size(); i ++){
           // if (privateFlights.get(i))
        }
    }
}

