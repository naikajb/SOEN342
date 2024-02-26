package src;

import java.time.LocalDateTime;

public class Flight {
    protected String flightNumber;
    protected Airport source;
    protected Airport destination;
    protected LocalDateTime scheduledDeparture;
    protected LocalDateTime scheduledArrival;
    protected LocalDateTime actualDeparture;
    protected LocalDateTime estimatedArrival;
    protected Aircraft aircraft;



    public Airport getSource(){
        return source;
    }

    public Airport getDestination(){
        return destination;
    }

    public String getFlightNumber(){
        return flightNumber;
    }

    public LocalDateTime getScheduledArrival(){
        return scheduledArrival;
    }

    public LocalDateTime getScheduledDeparture(){
        return scheduledDeparture;
    }

    public LocalDateTime getActualDeparture(){
        return actualDeparture;
    }

    public LocalDateTime getEstimatedArrival(){
        return estimatedArrival;
    }

    public Aircraft getAircraft(){
        return aircraft;
    }



}

