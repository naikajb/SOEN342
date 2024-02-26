package src;

import java.time.LocalDateTime;

public abstract class Flight {
    protected String flightNumber;
    protected Airport source;
    protected Airport destination;
    protected LocalDateTime scheduledDeparture;
    protected LocalDateTime scheduledArrival;
    protected LocalDateTime actualDeparture;
    protected LocalDateTime estimatedArrival;
    protected Aircraft aircraft;
    //does it have an airline?

    protected String viewFlightInfo(){
        return "hello";
    }

}

