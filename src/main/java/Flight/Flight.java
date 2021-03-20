package Flight;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class Flight implements Serializable {

    static final long serialVersionUID = 1L;

    private final String number;
    private LocalDateTime date;
    private final Departures depart;
    private final Destinations dest;
    private int places;


    public Flight(String number, LocalDateTime date, Departures depart, Destinations dest, int places) {
        this.number = number;
        this.date = date;
        this.depart = depart;
        this.dest = dest;
        this.places = places;
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

    public int getPlaces() {
        return places;
    }

    public void setPlaces(int places) {
        this.places = places;
    }

    @Override
    public String toString() {
        return String.format("Flight number -> %s, flight date -> %s, flight time -> %s, flight departure -> %s, flight destination -> %s, flight free places -> %d\n",
                number,
                date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)),
                date.format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)),
                depart.name(),
                dest.name(),
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
                this.getDest().name().equals(f.getDest().name());
    }
}
