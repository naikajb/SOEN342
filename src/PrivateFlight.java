package src;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class PrivateFlight extends Flight {
    private Airline airline;

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
