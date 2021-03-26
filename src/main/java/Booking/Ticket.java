package Booking;

import Flights.Flight.Departures;
import Flights.Flight.Destinations;
import Users.User.User;

import java.time.LocalDateTime;

public class Ticket {
    public User user;
    public String ticketNumber;
    public Destinations destination;
    public Departures departure;
    public LocalDateTime dateTime;

    public Ticket(User user,String ticketNumber, Destinations destination, Departures departure, LocalDateTime dateTime) {
        this.user = user;
        this.ticketNumber = ticketNumber;
        this.destination = destination;
        this.departure = departure;
        this.dateTime = dateTime;
    }

    @Override
    public String toString(){
        return String.format("ticket owner -> %s, ticket number -> %s, destination -> %s, departure -> %s, date time -> %s",
                user,ticketNumber, destination.name(), departure.name(), dateTime.toString());
    }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (!(o instanceof Ticket)) return false;
        Ticket ticket = (Ticket) o;
        return this.user.equals(ticket.user) &&
                this.ticketNumber.equals(ticket.ticketNumber) &&
                this.destination.equals(ticket.destination) &&
                this.departure.equals(ticket.departure) &&
                this.dateTime.equals(ticket.dateTime);
    }


}
