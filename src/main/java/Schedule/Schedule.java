package Schedule;

import Flights.Flight.Flight;

import java.util.List;

public class Schedule {
    static public void showFlights(List<Flight> flights) {
        System.out.printf("FLIGHT NUMBER%sFLIGHT DATE%sFLIGHT TIME%sFLIGHT DEPARTURE%sFLIGHT DESTINATION%sFLIGHT FREE PLACES\n",
                new String(new char[17]).replace('\0', ' '),
                new String(new char[18]).replace('\0', ' '),
                new String(new char[14]).replace('\0', ' '),
                new String(new char[9]).replace('\0', ' '),
                new String(new char[9]).replace('\0', ' '));
        flights.forEach(System.out::println);
    }

}
