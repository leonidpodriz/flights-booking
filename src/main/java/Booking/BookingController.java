package Booking;


import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class BookingController {
    BookingService service = new BookingService();

    public void initializeDb() throws IOException, ClassNotFoundException {service.initializeDb();}

    public List<Ticket> getAll(){return service.getAllTickets();}

    public Optional<Ticket> get(String number){return service.get(number);}

    public void add(Ticket ticket) throws IOException { service.add(ticket);}

    public void add(List<Ticket> tickets) throws IOException {service.add(tickets);}

    public boolean remove(String number) throws IOException {return service.remove(number);}

    public boolean remove(Ticket ticket) throws IOException {return service.remove(ticket);}
}
