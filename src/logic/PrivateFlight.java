package src.logic;

import java.time.LocalDateTime;

public class PrivateFlight extends Flight {
    private Airline airline;
    //TODO: generate the flight number w/ format "AC-365"
    public PrivateFlight(
            String num,
            Airport src,
            Airport dest,
            LocalDateTime scheduledDep,
            LocalDateTime scheduledArr,
            LocalDateTime actualDep,
            LocalDateTime estimatedDep,
            Aircraft airC
    ){ //check type(this.type??)
        flightNumber = num;
        source = src;
        destination = dest;
        scheduledArrival = scheduledArr;
        scheduledDeparture = scheduledDep;
        actualDeparture = actualDep;
        estimatedArrival = estimatedDep;
        aircraft = airC;

    }


}
