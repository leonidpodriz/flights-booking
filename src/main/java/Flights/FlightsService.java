package Flights;

import Flight.Flight;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


public class FlightsService implements DAO<Flight> {

    private final Map<String, Flight> db = new HashMap<>();

    private final int flightsQuantity = 500;

    public void initializeDb() throws IOException, ClassNotFoundException {

        File dbDir = new File("src/db");
        File dbFile = new File("src/db/db.bin");

        boolean mkdir = dbDir.mkdir();

        if (mkdir) {
            boolean newFile = dbFile.createNewFile();
            generateAndWriteFlights(db, dbFile);
        } else {
            if (dbFile.exists()) {
                readFlights(db, dbFile);
            } else {
                boolean newFile = dbFile.createNewFile();
                generateAndWriteFlights(db, dbFile);
            }
        }
    }

    private List<Flight> generateFlights() {
        return FlightsGenerator.generateFlights(flightsQuantity);
    }

    private void generateAndWriteFlights(Map<String, Flight> db, File file) throws IOException {
        List<Flight> flights = generateFlights();
        flights.forEach(x -> db.put(x.getNumber(), x));
        writeFLights(flights, file);
    }

    private void writeFLights(List<Flight> flights, File file) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            for (Flight flight : flights) {
                oos.writeObject(flight);
            }
        }
    }

    private void readFlights(Map<String, Flight> flights, File file) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            for (int i = 1; i <= flightsQuantity; i++) {
                Flight flight = (Flight) ois.readObject();
                flights.put(flight.getNumber(), flight);
            }
        }
    }

    private void save(Flight flight) {
        db.put(flight.getNumber(), flight);
    }

    @Override
    public Optional<Flight> get(String id) {
        return db.containsKey(id) ? Optional.of(db.get(id)) : Optional.empty();
    }

    @Override
    public List<Flight> getALL() {
        return new ArrayList<>(db.values());
    }

    @Override
    public boolean update(String id, Flight flight) {
        if (db.containsKey(id)) {
            save(flight);
            return true;
        }
        return false;
    }
}
