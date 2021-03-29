package Flights;

import Flights.Flight.Destinations;
import Flights.Flight.Flight;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FlightsGeneratorTest {
    @Test
    public void flightsGenerations() {
        final List<Flight> flights = FlightsGenerator.generateFlights(1000);
        final Map<String, Integer> elems = new HashMap<>();
        flights.stream().map(Flight::getNumber).forEach(x -> {
            int newValue = elems.getOrDefault(x, 0) + 1;
            elems.put(x, newValue);
        });
        final List<String> numbersMoreThan1 = new ArrayList<>();
        elems.forEach((x, y) -> {
            if (y > 1) numbersMoreThan1.add(x);
        });
        assertEquals(numbersMoreThan1.size(), 0);
    }

    @Test
    public void generateDestination() {
        final Destinations destination = FlightsGenerator.generateDestination();
        assertTrue(Arrays.asList(Destinations.destinations).contains(destination));
    }

    @Test
    public void generatePlaces() {
        final int places = FlightsGenerator.generatePlaces();
        assertTrue(places >= 50 && places <= 200);
    }

    @Test
    public void generateDateTime() {
        final LocalDateTime dateTime = FlightsGenerator.generateDateTime();
        assertTrue(dateTime.isAfter(LocalDateTime.now()));
    }
}
