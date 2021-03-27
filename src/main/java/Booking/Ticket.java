package Booking;

import Flights.Flight.Departures;
import Flights.Flight.Destinations;
import Flights.Flight.Flight;
import Users.User.User;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Ticket implements Serializable {
    public User user;
    public String ticketNumber;
    public Flight flight;

    public Ticket(User user, Flight flight) {
        this.user = user;
        this.ticketNumber = String.format("%s%s%s", user.login, flight.getNumber(), flight.getDate().toLocalDate().toString());
        this.flight = flight;
    }

    @Override
    public String toString() {
        return String.format("ticket owner -> %s, ticket number -> %s, destination -> %s, departure -> %s, date time -> %s",
                user, ticketNumber, flight.getDest().name(), flight.getDepart().name(), flight.getDate().toString());
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
