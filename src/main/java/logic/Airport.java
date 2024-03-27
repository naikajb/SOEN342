package src.main.java.logic;

import java.util.ArrayList;

public class Airport {

    private String name;
    private String airportCode;
    private City location;
    private ArrayList<PrivateFlight> privateFlights;
    private ArrayList<Aircraft> currentAircrafts;

    public Airport(String n, String code, City loc) {
        name = n;
        airportCode = code;
        location = loc;
        privateFlights = null;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return airportCode;
    }

    public City getLocation() {
        return location;
    }

    public void addPrivateFlight(PrivateFlight flight) {
        if (privateFlights == null) {
            privateFlights = new ArrayList<>();
        }
        privateFlights.add(flight);
    }

    public void addAircraft(Aircraft craft) {
        if (currentAircrafts == null) {
            currentAircrafts = new ArrayList<>();
        }

        currentAircrafts.add(craft);
    }

    public void removeAircraft(Aircraft craft) {
        currentAircrafts.remove(craft);
    }

    public Aircraft checkAvailableAircraft() {
        if (!currentAircrafts.isEmpty()) {
            return currentAircrafts.remove(0);
        } else {
            return null;
        }
    }

    public ArrayList<PrivateFlight> getListOfFlights() {
        return privateFlights;
    }
}
