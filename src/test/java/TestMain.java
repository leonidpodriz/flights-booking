import Flight.Flight;
import Flights.FlightsGenerator;
import Flights.FlightsService;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


public class TestMain {
    @Test
    public void testAdd() {
        assertEquals(Main.add(3, 5), 8);
        assertEquals(Main.add(3, 6), 9);
        assertEquals(Main.add(2, 3), 5);
    }

    @Test
    public void testGenerations(){
        List<Flight> flights = FlightsGenerator.generateFlights(1000);

        Map<String, Integer> elems = new HashMap<>();

        flights.stream().map(x -> x.getNumber()).forEach(x -> {
            int newValue = elems.getOrDefault(x, 0) + 1;
            elems.put(x, newValue);
        });

        List<String> numbersMoreThan1 = new ArrayList<>();

        elems.forEach((x, y) -> {
            if (y > 1) numbersMoreThan1.add(x);
        });

        assertEquals(numbersMoreThan1.size(), 0);
    }

    @Test
    public void testDbGeneration(){
        FlightsService service = new FlightsService();
        try {
            service.initializeDb();
            List<Flight> flights = service.getALL();
            assertEquals(flights.size(), 500);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
