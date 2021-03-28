
import Booking.Ticket;
import Flights.Flight.Departures;
import Flights.Flight.Destinations;
import Flights.Flight.Flight;
import Flights.FlightsGenerator;
import Users.User.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;


public class TestTicket {
    private final User testUser = new User("test", "test", "test", "12345");
    private final Flight flight = FlightsGenerator.generateFlights(1).get(0);
    private final Ticket testTicket = new Ticket(testUser, flight);


    @Test
    public void ticketEquals(){
        final User testUser2 = new User("test", "test", "test", "12345");
        final Ticket testTicket2 = new Ticket(testUser2, flight);
        assertEquals(testTicket, testTicket2);
    }
}
