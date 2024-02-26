package src;

import java.time.LocalDateTime;

public class PrivateFlight extends Flight {
    private Airline airline;
    private final String type;
    public PrivateFlight(
            String type,
            String num,
            Airport src,
            Airport dest,
            LocalDateTime scheduledDep,
            LocalDateTime scheduledArr,
            LocalDateTime actualDep,
            LocalDateTime estimatedDep,
            Airline airL,
            Aircraft airC
    ){
        this.type = type;
        flightNumber = num;
        source = src;
        destination = dest;
        scheduledArrival = scheduledArr;
        scheduledDeparture = scheduledDep;
        actualDeparture = actualDep;
        estimatedArrival = estimatedDep;
        airline = airL;
        aircraft = airC;

    }

    public String viewFlightInfo(){
        return "hello";
    }


}
