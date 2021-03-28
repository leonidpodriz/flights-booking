import Flights.Flight.Flight;
import Flights.FlightController;

import Flights.FlightsGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestFlightsController {
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
    public void dbGeneration() {
        assertEquals(flights.size(), 500);
    }

    @Test
    public void getFlight() {
        Flight firstFlight = flights.get(0);
        assertEquals(controller.get(firstFlight.getNumber()).get(), firstFlight);
    }

    @Test
    public void updateFlight() {
        Flight testFlight = FlightsGenerator.generateFlights(1).get(0);
        Flight oldFlight = flights.get(0);
        String oldFlightNumber = oldFlight.getNumber();
        boolean update = controller.update(oldFlightNumber, testFlight);
        assertTrue(update);
        boolean update2 = controller.update("dasda", testFlight);
        assertFalse(update2);
        flights = controller.getAll();
        assertTrue(flights.contains(testFlight));
        assertFalse(flights.contains(oldFlight));
    }

}
