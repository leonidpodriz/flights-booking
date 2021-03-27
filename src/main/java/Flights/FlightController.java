package Flights;

import Flights.Flight.Flight;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class FlightController {

    private final FlightsService service = new FlightsService();

    public void initializeDb() throws IOException, ClassNotFoundException {
        service.initializeDb();
    }

    public Optional<Flight> get(String id) {
        return service.get(id);
    }

    public List<Flight> getAll(){ return service.getAll(); }

    public boolean update(String id, Flight flight){ return service.update(id, flight);}


}
