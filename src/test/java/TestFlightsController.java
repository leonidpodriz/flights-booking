import Flights.Flight.Flight;
import Flights.FlightController;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestFlightsController {
    final FlightController service = new FlightController();

    @BeforeEach
    public void initializeDb(){
        try {
            service.initializeDb();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void dbGeneration() {
        try {
            service.initializeDb();
            final List<Flight> flights = service.getAll();
            assertEquals(flights.size(), 500);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getFlight(){
        List<Flight> flights = service.getAll();
        Flight firstFlight = flights.get(0);
        assertEquals(service.get(firstFlight.getNumber()).get(), firstFlight);
    }

    @Test
    public void flightsSize(){
        List<Flight> flights = service.getAll();
        assertEquals(flights.size(), 500);
    }

}
