package Flights.Flight;

import Booking.Ticket;
import Users.User.User;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;

public class Flight implements Serializable {

    static final long serialVersionUID = 1L;

    private final String number;
    private LocalDateTime date;
    private final Departures depart;
    private final Destinations dest;
    private int places;
    private final List<Ticket> tickets = new ArrayList<>();

    public Flight(String number, LocalDateTime date, Departures depart, Destinations dest, int places) {
        this.number = number;
        this.date = date;
        this.depart = depart;
        this.dest = dest;
        this.places = places;
    }


    public void addTicket(Ticket ticket){
        tickets.add(ticket);
        places--;
    }

    public void removeTicket(Ticket ticket){
        if (tickets.remove(ticket)) places++;
    }

    public void removeTicket(String number){
        if (tickets.removeIf(x -> x.ticketNumber.equals(number))) places++;
    }


    public String getNumber() {
        return number;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Destinations getDest() { return dest; }

    public Departures getDepart(){return depart;}

    public int getPlaces() {
        return places;
    }

    public void setPlaces(int places) {
        this.places = places;
    }

    @Override
    public String toString() {
        return String.format("%8s %s %8s %s %s %s %s %s %-7s %s %d",
                number,
                new String(new char[20]).replace('\u0000', '-'),
                date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)),
                new String(new char[20]).replace('\u0000', '-'),
                date.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)),
                new String(new char[20]).replace('\u0000', '-'),
                depart.name(),
                new String(new char[20]).replace('\u0000', '-'),
                dest.name(),
                new String(new char[20]).replace('\u0000', '-'),
                places);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Flight))
            return false;
        Flight f = (Flight) obj;
        if (this.places != f.places) return false;
        return this.getNumber().equals(f.getNumber()) &&
                this.getDate().toString().equals(f.getDate().toString()) &&
                this.getDest().name().equals(f.getDest().name()) && tickets.equals(f.tickets);
    }
}
