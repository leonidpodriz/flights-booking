package Flights.Flight;

import Booking.Ticket;
import Flights.Flight.Flight;
import Flights.FlightsGenerator;
import Users.User.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FlightTest {
    private Flight flight = FlightsGenerator.generateFlights(1).get(0);
    private final User user = new User("test", "test", "test", "12345");
    private final Ticket testTicket = new Ticket(user, flight);


    @BeforeEach
    public void regenerateFlight() {
        flight = FlightsGenerator.generateFlights(1).get(0);
    }

    @Test
    public void addUser() {
        int oldPlaces = flight.getPlaces();
        flight.addTicket(testTicket);
        int newPlaces = flight.getPlaces();
        assertEquals(oldPlaces, newPlaces + 1);
    }

    @Test
    public void removeUser() {
        int oldPlaces = flight.getPlaces();
        flight.addTicket(testTicket);
        flight.removeTicket(testTicket);
        int newPlaces = flight.getPlaces();
        assertEquals(oldPlaces, newPlaces);
        flight.addTicket(testTicket);
        flight.removeTicket(testTicket.ticketNumber);
        int anotherNewPlaces = flight.getPlaces();
        assertEquals(newPlaces, anotherNewPlaces);
    }

    @Test
    public void setDate() {
        LocalDateTime dateTime = LocalDateTime.now();
        flight.setDate(dateTime);
        assertEquals(flight.getDate(), dateTime);
    }


    @Test
    public void equals() {
        Flight testFlight = new Flight(flight.getNumber(), flight.getDate(), flight.getDepart(), flight.getDest(), flight.getPlaces());
        assertEquals(flight, testFlight);
    }
}
