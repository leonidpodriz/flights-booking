package Booking;


import Flights.Flight.Flight;
import Flights.FlightsGenerator;
import Users.User.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BookingControllerTest {
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
    public void addTicket() throws IOException {
        int oldSize = controller.getAll().size();
        controller.add(ticket);
        assertEquals(oldSize + 1, controller.getAll().size());
        assertEquals(tickets.get(oldSize), ticket);
        controller.remove(ticket);
    }

    @Test
    public void removeTicket() throws IOException {
        int oldSize = controller.getAll().size();
        controller.add(ticket);
        assertEquals(oldSize + 1, controller.getAll().size());
        controller.remove(ticket);
        assertEquals(oldSize, controller.getAll().size());
    }

    @Test
    public void addListTicket() throws IOException {
        int oldSize = controller.getAll().size();
        User newTestUser = new User("test1", "test1", "test1", "112345");
        Flight newTestFlight = FlightsGenerator.generateFlights(1).get(0);
        Ticket newTestTicket = new Ticket(newTestUser, newTestFlight);
        newTestUser.addTicket(newTestTicket);
        List<Ticket> futureTickets = new ArrayList<Ticket>() {{
            addAll(controller.getAll());
            add(ticket);
            add(newTestTicket);
        }};

        List<Ticket> testTickets = new ArrayList<Ticket>(){{
            add(ticket);
            add(newTestTicket);
        }};
        controller.add(testTickets);
        assertArrayEquals(controller.getAll().toArray(), futureTickets.toArray());
        controller.remove(ticket);
        futureTickets.remove(ticket);
        assertArrayEquals(controller.getAll().toArray(), futureTickets.toArray());
        String flightNumber = newTestUser.login + newTestUser.getAllTickets().size() + newTestFlight.getNumber() + newTestFlight.getDate().toLocalDate().toString();
        controller.remove(flightNumber);
        futureTickets.remove(newTestTicket);
        assertEquals(oldSize, controller.getAll().size());
        assertArrayEquals(controller.getAll().toArray(), futureTickets.toArray());
    }
}
