import Console.Callback;
import Console.Console;
import Console.ExitCallback;
import Console.RunnableConsole;
import Flights.Flight.Flight;
import Flights.FlightsService;
import Schedule.Schedule;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Main {
    static FlightsService flightsService = new FlightsService();
    static Console console = new RunnableConsole(Main::getCallbacks);

    public static void onStart() throws IOException, ClassNotFoundException {
        flightsService.initializeDb();
    }

    public static void showSchedule(Console console) {
        Schedule.showFlights(flightsService.getAll());
    }

    public static void showFlightInformation(Console console) {
        console.print("Enter flight ID:\n");
        String flightID = console.readString();
        Optional<Flight> flightObj = flightsService.get(flightID);
        if (flightObj.isPresent()) {
            Flight flight = flightObj.get();
            console.print(String.format(
                    "Number: %s, Date: %s,  Dest: %s, Places: %s\n",
                    flight.getNumber(), flight.getDate(), flight.getDest(), flight.getPlaces()
            ));
        } else {
            console.print("No flight with this ID!\n");
        }
    }

    public static void searchAndBookFlight(Console console) {
        List<Flight> allFlights = flightsService.getAll();
        List<String> possibleDestination = allFlights.stream().map(f -> f.getDest().name().toLowerCase()).collect(Collectors.toList());

        console.println("Enter destination:");
        String destination = console.readString(
                s -> possibleDestination.contains(s.toLowerCase()),
                String.format("one of: %s", String.join(", ", possibleDestination)));

        boolean isDateInvalid;
        LocalDate date1 = LocalDate.now();
        do {
            try {
                console.println("Enter date [MM.dd.yyyy]:");
                date1 = LocalDate.parse(console.readString(), DateTimeFormatter.ofPattern("MM.dd.yyyy"));
                isDateInvalid = false;
            } catch (DateTimeParseException e) {
                isDateInvalid = true;
            }
        } while (isDateInvalid);

        LocalDate date = date1;

        console.println("Enter peoples count:");
        int peoplesCount = console.readInt();

        String searchResult = flightsService.getAll()
                .stream()
                .filter(f -> f.getDest().name().equalsIgnoreCase(destination) && f.getPlaces() >= peoplesCount && f.getDate().toLocalDate().equals(date))
                .map(Flight::toString)
                .collect(Collectors.joining("\n"));

        if (searchResult.isEmpty()) {
            console.println("No flight founded!");
            return;
        }

        console.println(searchResult);
        console.println("Enter flight ID:");
        String flight_id = console.readString();
        Optional<Flight> flight = flightsService.get(flight_id);

        if (!flight.isPresent()) {
            console.println("Incorrect flight ID!");
            return;
        }
        console.println(flight.get().toString());
    }

    public static ArrayList<Callback> getCallbacks() {
        ArrayList<Callback> callbacks = new ArrayList<>();

        callbacks.add(new Callback("Display online table", Main::showSchedule));
        callbacks.add(new Callback("Display flight information", Main::showFlightInformation));
        callbacks.add(new Callback("Search and book flight", Main::searchAndBookFlight));
        callbacks.add(new ExitCallback());

        return callbacks;
    }

    public static void run() {
        console.run();
    }

    public static void onClose() {

    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        onStart();
        run();
        onClose();
    }
}
