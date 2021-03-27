package Booking;

import Flights.Flight.Departures;
import Flights.Flight.Destinations;
import Flights.Flight.Flight;
import Users.User.User;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Ticket implements Serializable {
    public final User user;
    public final String ticketNumber;
    public final Flight flight;

    public Ticket(User user, Flight flight) {
        this.user = user;
        this.ticketNumber = String.format("%s%d%s%s", user.login,user.getAllTickets().size() + 1, flight.getNumber(), flight.getDate().toLocalDate().toString());
        this.flight = flight;
    }

    private void generateNumber(){

    }

    @Override
    public String toString() {
        return String.format("ticket number -> %s, destination -> %s, departure -> %s, date time -> %s",
                ticketNumber, flight.getDest().name(), flight.getDepart().name(), flight.getDate().format(DateTimeFormatter.ofPattern("DD.MM.YYYY")));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ticket)) return false;
        Ticket ticket = (Ticket) o;
        return this.user.equals(ticket.user) &&
                this.ticketNumber.equals(ticket.ticketNumber) &&
                this.flight.equals(ticket.flight);
    }


}
