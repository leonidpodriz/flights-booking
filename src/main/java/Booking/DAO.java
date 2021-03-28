package Booking;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface DAO {

    public List<Ticket> getAllTickets();

    public Optional<Ticket> get(String number);

    public void add(Ticket ticket) throws IOException;

    public void add(List<Ticket> newTickets) throws IOException;

    public boolean remove(String number) throws IOException;

    public boolean remove(Ticket ticket) throws IOException;
}
