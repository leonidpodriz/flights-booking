package Flights;

import Flights.Flight.Flight;

import java.util.List;
import java.util.Optional;

public interface DAO {

    Optional<Flight> get(String id);

    List<Flight> getAll();

    boolean update(String number, Flight flight);

}
