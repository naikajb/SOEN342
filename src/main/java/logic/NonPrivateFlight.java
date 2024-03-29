package logic;

import java.time.LocalDateTime;
import logic.FlightTypes;

public class NonPrivateFlight extends Flight {

    private FlightTypes flightType; // enum: COMMERCIAL, CARGO

    // TODO: generate the flight number w/ format "AC-365"
    public NonPrivateFlight(
            long id,
            String flightNumber,
            long sourceAirport, // in DB: sourceAirportId
            long destinationAirport, // in DB: destinationAirportId
            LocalDateTime scheduledDeparture,
            LocalDateTime scheduledArrival,
            LocalDateTime actualDeparture,
            LocalDateTime estimatedArrival,
            long aircraftId,
            FlightTypes flightType) {
        super(id, flightNumber, sourceAirport, destinationAirport, scheduledDeparture, scheduledArrival,
                actualDeparture, estimatedArrival, aircraftId);
        this.flightType = flightType;
    }

}
