package src;

import java.time.LocalDateTime;
import java.time.LocalTime;

enum Types {
    COMMERCIAL, CARGO;
}


public class NonPrivateFlight extends Flight{

    private Types type;

    public NonPrivateFlight(
            String num,
            Types t,
            Airport src,
            Airport dest,
            LocalDateTime scheduledDep,
            LocalDateTime scheduledArr,
            LocalDateTime actualDep,
            LocalDateTime estimatedDep,
            Aircraft airC
    ){
        type = t;
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
