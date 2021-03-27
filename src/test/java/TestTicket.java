
import Booking.Ticket;
import Flights.Flight.Departures;
import Flights.Flight.Destinations;
import Users.User.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;


public class TestTicket {
    private final User testUser = new User("test", "test", "test", "12345");
    private final Ticket testTicket = new Ticket(testUser, "test", Destinations.ANKARA, Departures.KYIV, LocalDateTime.parse("2019-11-01T11:43"));


    @Test
    public void ticketEquals(){
        final User testUser2 = new User("test", "test", "test", "12345");
        final Ticket testTicket2 = new Ticket(testUser2, "test", Destinations.ANKARA, Departures.KYIV,  LocalDateTime.parse("2019-11-01T11:43"));
        assertEquals(testTicket, testTicket2);
    }
}
