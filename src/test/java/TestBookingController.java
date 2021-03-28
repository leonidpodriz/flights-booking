
import Booking.BookingController;
import Booking.Ticket;
import Flights.Flight.Flight;
import Flights.FlightsGenerator;
import Users.User.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestBookingController {
    private final BookingController controller = new BookingController();
    private final User user = new User("test", "test", "test", "12345");
    private final Flight flight = FlightsGenerator.generateFlights(1).get(0);
    Ticket ticket = new Ticket(user, flight);
    private List<Ticket> tickets;

    @BeforeEach
    public void generate() throws IOException, ClassNotFoundException {
        controller.initializeDb();
        tickets = controller.getAll();
    }

    @Test
    public void generationCheck(){
        assertEquals(tickets.size(), 0);
    }

    @Test
    public void addTicket() throws IOException {
        controller.add(ticket);
        tickets = controller.getAll();
        assertEquals(tickets.size(), 1);
        assertEquals(tickets.get(0), ticket);
        controller.remove(ticket);
    }

    @Test
    public void removeTicket() throws IOException {
        controller.add(ticket);
        controller.remove(ticket);
        tickets = controller.getAll();
        assertEquals(tickets.size(), 0);
    }

    @Test
    public void addListTicket() throws IOException {
        User newTestUser = new User("test1", "test1", "test1", "112345");
        Flight newTestFlight = FlightsGenerator.generateFlights(1).get(0);
        Ticket newTestTicket = new Ticket(newTestUser, newTestFlight);
        newTestUser.addTicket(newTestTicket);
        List<Ticket> tickets1 = new ArrayList<Ticket>(){{
            add(ticket);
            add(newTestTicket);
        }};
        controller.add(tickets1);
        tickets = controller.getAll();
        assertArrayEquals(tickets.toArray(), tickets1.toArray());
        controller.remove(ticket);
        tickets = controller.getAll();
        tickets1.remove(ticket);
        assertArrayEquals(tickets.toArray(), tickets1.toArray());
        String flightNumber = newTestUser.login + newTestUser.getAllTickets().size() + newTestFlight.getNumber() + newTestFlight.getDate().toLocalDate().toString();
        controller.remove(flightNumber);
        System.out.println(flightNumber);
        System.out.println(newTestTicket.ticketNumber);
        tickets1.clear();
        tickets = controller.getAll();
        assertEquals(tickets.size(), 0);
        assertArrayEquals(tickets.toArray(), tickets1.toArray());

    }
}
