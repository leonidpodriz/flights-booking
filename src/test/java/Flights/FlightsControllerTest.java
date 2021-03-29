package Flights;

import Booking.Ticket;
import Flights.Flight.Flight;
import Users.User.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class FlightsControllerTest {
    private final FlightController controller = new FlightController();
    private List<Flight> flights;


    @BeforeEach
    public void initializeDb() {
        try {
            controller.initializeDb();
            flights = controller.getAll();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void getFlight() {
        Flight firstFlight = flights.get(0);
        String number = firstFlight.getNumber();
        Optional<Flight> flight = controller.get(number);
        boolean isFlightPresent = flight.isPresent();
        assertTrue(isFlightPresent);
        assertEquals(flight.get(), firstFlight);
    }

    @Test
    public void updateFlight() throws IOException {
        Flight flight = flights.get(0);
        final User testUser = new User("test", "test", "test", "12345");
        final Ticket testTicket = new Ticket(testUser, flight);
        flight.addTicket(testTicket);
        boolean update = controller.update(flight.getNumber(), flight);
        assertTrue(update);
        boolean update2 = controller.update("dasda", flight);
        assertFalse(update2);
        flights = controller.getAll();
        Flight newFlight = flights.get(0);
        assertTrue(flights.contains(newFlight));
    }

}
