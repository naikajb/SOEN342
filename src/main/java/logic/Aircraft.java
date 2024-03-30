package logic;

public class Aircraft {
    private long id;
    private Locations location; // Enum: either TRANSIT or AIRPORT
    private String aircraftCode; // ex; Boing-You're-Gonna-Die
    private long airline; // in DB: airlineId
    private long currentAirport; // in DB: airportId

    public Aircraft(long id, Locations loc, String aircraftCode, long airline, long currentAirport) {
        this.id = id;
        this.location = loc;
        this.aircraftCode = aircraftCode;
        this.airline = airline;
        this.currentAirport = currentAirport;

    }

    public long getId() {
        return id;
    }

    public Locations getLocation() {
        return location;
    }

    public boolean setAirportCode(String airportCode) {
        // check first that aircraft is not in transit
        if (this.location == Locations.AIRPORT) {
            this.currentAirport = currentAirport;
            return true;
        } else {
            // TODO: change this to throwing an error if necessary
            return false;
        }
    }

    // TODO: should this be changed to having a parameter "airportCode" so that I
    // just compare with airportcode attribute
    public boolean checkAircraftAvailability() {
        return this.location == Locations.AIRPORT;
    }
}
