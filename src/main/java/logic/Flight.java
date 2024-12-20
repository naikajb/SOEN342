package logic;

import java.time.LocalDateTime;
import java.util.Random;

public abstract class Flight {
    protected long id;
    protected String flightNumber;
    protected long sourceAirport; // in DB: sourceAirportId
    protected long destinationAirport; // in DB: destinationAirportId
    protected LocalDateTime scheduledDeparture;
    protected LocalDateTime scheduledArrival;
    protected LocalDateTime actualDeparture;
    protected LocalDateTime estimatedArrival;
    protected long aircraftId;

    public Flight(long id, String flightNumber, long sourceAirport, long destinationAirport,
            LocalDateTime scheduledDeparture,
            LocalDateTime scheduledArrival, LocalDateTime actualDeparture, LocalDateTime estimatedArrival,
            long aircraftId) {
        this.id = id;
        this.flightNumber = flightNumber;
        this.sourceAirport = sourceAirport;
        this.destinationAirport = destinationAirport;
        this.scheduledDeparture = scheduledDeparture;
        this.scheduledArrival = scheduledArrival;
        this.actualDeparture = actualDeparture;
        this.estimatedArrival = estimatedArrival;
        this.aircraftId = aircraftId;
    }

    public long getSource() {
        return sourceAirport;
    }

    public long getDestination() {
        return destinationAirport;
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

    public long getAircraftId() {
        return aircraftId;
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
