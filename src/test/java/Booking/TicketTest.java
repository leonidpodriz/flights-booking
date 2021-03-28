package Booking;

import Flights.Flight.Flight;
import Flights.FlightsGenerator;
import Users.User.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class TicketTest {
    private final User testUser = new User("test", "test", "test", "12345");
    private final Flight flight = FlightsGenerator.generateFlights(1).get(0);
    private final Ticket testTicket = new Ticket(testUser, flight);


    @Test
    public void ticketEquals() {
        final User testUser2 = new User("test", "test", "test", "12345");
        final Ticket testTicket2 = new Ticket(testUser2, flight);
        assertEquals(testTicket, testTicket2);
    }
}
