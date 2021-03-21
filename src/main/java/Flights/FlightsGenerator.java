package Flights;

import Flight.Flight;
import Flight.Destinations;
import Flight.Departures;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class FlightsGenerator {

    private static final Random random = new Random();

    public static String generateFlightNumber() {
        return random.ints(48, 90)
                .filter(i -> i < 57 || i > 65)
                .mapToObj(i -> (char) i)
                .limit(4)
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
    }

    public static Destinations generateDestination() {
        return Destinations.getRandomDest();
    }

    public static int generatePlaces() {
        return (int) (Math.random() * 150) + 50;
    }

    public static LocalDateTime generateDateTime() {
        long minutes = (long) (Math.random() * 100000);
        return LocalDateTime.now().plus(minutes, ChronoUnit.MINUTES);
    }

    public static List<Flight> generateFlights(int quantity) {
        List<Flight> flights = new ArrayList<>();
        List<String> flightNumbers = new ArrayList<>();
        for (int i = 1; i <= quantity; i++) {
            String number = generateFlightNumber();
            while (flightNumbers.contains(number)) {
                number = generateFlightNumber();
            }
            flightNumbers.add(number);
            flights.add(new Flight(number, generateDateTime(), Departures.KYIV, generateDestination(), generatePlaces()));
        }
        return flights;
    }


}




