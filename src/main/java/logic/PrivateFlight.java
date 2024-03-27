package src.main.java.logic;

import java.time.LocalDateTime;

public class PrivateFlight extends Flight {
    private Airline airline;

    // TODO: generate the flight number w/ format "AC-365"
    public PrivateFlight(
            Airport src,
            Airport dest,
            LocalDateTime scheduledDep,
            LocalDateTime scheduledArr,
            LocalDateTime actualDep,
            LocalDateTime estimatedDep,
            Aircraft airC) { // check type(this.type??)
        source = src;
        destination = dest;
        scheduledArrival = scheduledArr;
        scheduledDeparture = scheduledDep;
        actualDeparture = actualDep;
        estimatedArrival = estimatedDep;
        aircraft = airC;

        flightNumber = assignRandomNumber();

    }

}
