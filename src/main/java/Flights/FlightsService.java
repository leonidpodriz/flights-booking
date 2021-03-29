package Flights;

import Flights.Flight.Flight;

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


public class FlightsService implements DAO {

    private final Map<String, Flight> db = new HashMap<>();

    private final File dbDir = new File("db");
    private final File dbFile = new File("db/db.bin");

    public void initializeDb() throws IOException, ClassNotFoundException {
        boolean mkdir = dbDir.mkdir();
        if (mkdir) {
            boolean newFile = dbFile.createNewFile();
            generateAndWriteFlights();
        } else {
            if (dbFile.exists()) {
                readFlights();
            } else {
                boolean newFile = dbFile.createNewFile();
                generateAndWriteFlights();
            }
        }
    }

    private List<Flight> generateFlights() {
        return FlightsGenerator.generateFlights(500);
    }

    private void generateAndWriteFlights() throws IOException {
        List<Flight> flights = generateFlights();
        flights.forEach(x -> db.put(x.getNumber(), x));
        writeFLights();
    }

    private void writeFLights() throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(dbFile));
        oos.writeObject(db);
    }

    private void readFlights() throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(dbFile));
        HashMap<String, Flight> fileDb = (HashMap<String, Flight>) ois.readObject();
        ois.close();
        fileDb.forEach(db::put);

    }

    private void save(Flight flight) throws IOException {
        db.put(flight.getNumber(), flight);
        dbFile.delete();
        dbFile.createNewFile();
        writeFLights();
    }

    @Override
    public Optional<Flight> get(String id) {
        return db.containsKey(id) ? Optional.of(db.get(id)) : Optional.empty();
    }

    @Override
    public List<Flight> getAll() {
        return new ArrayList<>(db.values());
    }

    @Override
    public boolean update(String id, Flight flight) throws IOException {
        if (db.containsKey(id)) {
            save(flight);
            return true;
        }
        return false;
    }
}
