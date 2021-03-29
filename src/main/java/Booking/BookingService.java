package Booking;


import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookingService implements DAO {
    private final File dbDir = new File("db");
    private final File dbFile = new File("db/tickets.bin");
    private final List<Ticket> tickets = new ArrayList<>();

    public void initializeDb() throws IOException, ClassNotFoundException {
        boolean mkdir = dbDir.mkdir();
        if (mkdir) {
            boolean newFile = dbFile.createNewFile();
            writeList();
        } else {
            if (dbFile.exists()) {
                tickets.addAll(readTickets());
            } else {
                boolean newFile = dbFile.createNewFile();
                writeList();
            }
        }
    }

    private List<Ticket> readTickets() throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(dbFile));
        ArrayList<Ticket> tickets = (ArrayList<Ticket>) ois.readObject();
        ois.close();
        return tickets;
    }

    private void writeList() throws IOException {
        dbFile.delete();
        dbFile.createNewFile();
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(dbFile));
        oos.writeObject(tickets);
        oos.close();
    }

    @Override
    public List<Ticket> getAllTickets() {
        return tickets;
    }

    @Override
    public Optional<Ticket> get(String number) {
        return tickets.stream().filter(x -> x.ticketNumber.equals(number)).findFirst();
    }

    @Override
    public void add(Ticket ticket) throws IOException {
        if (tickets.contains(ticket)) return;
        tickets.add(ticket);
        writeList();
    }

    @Override
    public void add(List<Ticket> newTickets) throws IOException {
        tickets.addAll(newTickets);
        writeList();
    }

    @Override
    public boolean remove(String number) throws IOException {
        boolean check = tickets.removeIf(x -> x.ticketNumber.equals(number));
        if (check) writeList();
        return check;
    }

    @Override
    public boolean remove(Ticket ticket) throws IOException {
        boolean check = tickets.remove(ticket);
        if (check) writeList();
        return check;
    }
}
