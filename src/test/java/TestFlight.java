import Flights.Flight.Flight;
import Flights.FlightsGenerator;
import Users.User.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class TestFlight {
    private  Flight flight = FlightsGenerator.generateFlights(1).get(0);
    private User user = new User("test", "test", "test", "12345");


    @BeforeEach
    public void regenerateFlight(){
        flight = FlightsGenerator.generateFlights(1).get(0);
    }

    @Test
    public void addUser(){
        int oldPlaces = flight.getPlaces();
        flight.addUser(user);
        int newPlaces = flight.getPlaces();
        assertEquals(oldPlaces, newPlaces + 1);
    }

    @Test
    public void removeUser(){
        int oldPlaces = flight.getPlaces();
        flight.addUser(user);
        flight.removeUser(user);
        int newPlaces = flight.getPlaces();
        assertEquals(oldPlaces, newPlaces);
        flight.addUser(user);
        flight.removeUser(user.login);
        int anotherNewPlaces = flight.getPlaces();
        assertEquals(newPlaces, anotherNewPlaces);
    }

    @Test
    public void setDate(){
        LocalDateTime dateTime = LocalDateTime.now();
        flight.setDate(dateTime);
        assertEquals(flight.getDate(), dateTime);
    }

    @Test
    public void setPlaces(){
        int newPlaces = 100000;
        flight.setPlaces(newPlaces);
        assertEquals(flight.getPlaces(), newPlaces);
    }

    @Test
    public void equals(){
        Flight testFlight = new Flight(flight.getNumber(), flight.getDate(), flight.getDepart(), flight.getDest(), flight.getPlaces());
        assertEquals(flight, testFlight);
    }
}
