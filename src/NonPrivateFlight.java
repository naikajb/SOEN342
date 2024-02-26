package src;

import java.time.LocalDateTime;

public class NonPrivateFlight extends Flight{
    public NonPrivateFlight(
            String num,
            Airport src,
            Airport dest,
            LocalDateTime scheduledDep,
            LocalDateTime scheduledArr,
            LocalDateTime actualDep,
            LocalDateTime estimatedDep,
            Aircraft aircr
    ){
        flightNumber = num;
        source = src;
        destination = dest;
        scheduledArrival = scheduledArr;
        scheduledDeparture = scheduledDep;
        actualDeparture = actualDep;
        estimatedArrival = estimatedDep;
        aircraft = aircr;
    }

    public String viewFlightInfo(){
        return "hello";
    }

}
