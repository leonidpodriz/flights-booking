package Booking;


import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookingService implements DAO{
    private final File dbDir = new File("src/db");
    private final File dbFile = new File("src/db/tickets.bin");
    private final List<Ticket> tickets = new ArrayList<>();

    public void initializeDb() throws IOException, ClassNotFoundException {
        boolean mkdir = dbDir.mkdir();
        if (mkdir) {
            boolean newFile = dbFile.createNewFile();
            writeList();
        } else {
            if (dbFile.exists()) {
                tickets.addAll(readUsers());
            } else {
                boolean newFile = dbFile.createNewFile();
                writeList();
            }
        }
    }

    private List<Ticket> readUsers() throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(dbFile))) {
            return (ArrayList<Ticket>) ois.readObject();
        }
    }

    private void writeList() throws IOException {
        dbFile.delete();
        dbFile.createNewFile();
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(dbFile))) {
            oos.writeObject(tickets);
        }
    }

    @Override
    public List<Ticket> getAllTickets() {
        return tickets;
    }

    @Override
    public Optional<Ticket> get(String number) {
        return tickets.stream().filter(x ->x.ticketNumber.equals(number)).findFirst();
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
        boolean check =  tickets.remove(ticket);
        if (check) writeList();
        return check;
    }
}
