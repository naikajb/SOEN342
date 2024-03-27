package src.main.java.logic;

import java.time.LocalDateTime;
import java.util.Random;

public abstract class Flight {
    protected String flightNumber;
    protected Airport source;
    protected Airport destination;
    protected LocalDateTime scheduledDeparture;
    protected LocalDateTime scheduledArrival;
    protected LocalDateTime actualDeparture;
    protected LocalDateTime estimatedArrival;
    protected Aircraft aircraft;

    public Airport getSource() {
        return source;
    }

    public Airport getDestination() {
        return destination;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public LocalDateTime getScheduledArrival() {
        return scheduledArrival;
    }

    public LocalDateTime getScheduledDeparture() {
        return scheduledDeparture;
    }

    public LocalDateTime getActualDeparture() {
        return actualDeparture;
    }

    public LocalDateTime getEstimatedArrival() {
        return estimatedArrival;
    }

    public Aircraft getAircraft() {
        return aircraft;
    }

    String assignRandomNumber() {
        Random rnd = new Random();
        StringBuilder code = new StringBuilder();
        if (rnd.nextInt() % 2 == 0) {
            code.append("AA");
        } else {
            code.append("BB");
        }
        code.append('-');
        for (int i = 0; i < 3; i++) {
            code.append((char) rnd.nextInt());
        }
        return code.toString();
    }

}
