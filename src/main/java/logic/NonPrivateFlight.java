package src.main.java.logic;

import java.time.LocalDateTime;

enum Types {
    COMMERCIAL, CARGO;
}

public class NonPrivateFlight extends Flight {

    private Types type;

    // TODO: generate the flight number w/ format "AC-365"
    public NonPrivateFlight(
            Types t,
            Airport src,
            Airport dest,
            LocalDateTime scheduledDep,
            LocalDateTime scheduledArr,
            LocalDateTime actualDep,
            LocalDateTime estimatedDep,
            Aircraft airC) {
        type = t;
        source = src;
        destination = dest;
        scheduledArrival = scheduledArr;
        scheduledDeparture = scheduledDep;
        actualDeparture = actualDep;
        estimatedArrival = estimatedDep;
        aircraft = airC;
        super.flightNumber = super.assignRandomNumber();
    }

}
