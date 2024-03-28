package logic;

import java.time.LocalDateTime;

public class PrivateFlight extends Flight {

    // TODO: generate the flight number w/ format "AC-365"
    public PrivateFlight(
            long id,
            String flightNumber,
            long sourceAirport, // in DB: sourceAirportId
            long destinationAirport, // in DB: destinationAirportId
            LocalDateTime scheduledDeparture,
            LocalDateTime scheduledArrival,
            LocalDateTime actualDeparture,
            LocalDateTime estimatedArrival,
            long aircraftId) {
        super(id, flightNumber, sourceAirport, destinationAirport, scheduledDeparture, scheduledArrival,
                actualDeparture, estimatedArrival, aircraftId);
    }

}
