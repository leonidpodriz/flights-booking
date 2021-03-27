import Booking.Ticket;
import Console.Callback;
import Console.Console;
import Console.RunnableConsole;
import Flights.Flight.Flight;
import Flights.FlightsService;
import Schedule.Schedule;
import Users.User.User;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Main {
    static User user;
    static FlightsService flightsService = new FlightsService();
    static Console console = new RunnableConsole(Main::getCallbacks);

    static Callback exitCallback = new Callback("Exit", Main::exitCallbackRunner);

    public static void exitCallbackRunner(Console console) {
        console.printLine("Good bye!");
        console.prepareToExit();
    }

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

        console.printLine("Enter destination:");
        String destination = console.readString(
                s -> possibleDestination.contains(s.toLowerCase()),
                String.format("one of: %s", String.join(", ", possibleDestination)));

        boolean isDateInvalid;
        LocalDate date1 = LocalDate.now();
        do {
            try {
                console.printLine("Enter date [MM.dd.yyyy]:");
                date1 = LocalDate.parse(console.readString(), DateTimeFormatter.ofPattern("MM.dd.yyyy"));
                isDateInvalid = false;
            } catch (DateTimeParseException e) {
                isDateInvalid = true;
            }
        } while (isDateInvalid);

        LocalDate date = date1;

        console.printLine("Enter peoples count:");
        int peoplesCount = console.readInt();

        String searchResult = flightsService.getAll()
                .stream()
                .filter(f -> f.getDest().name().equalsIgnoreCase(destination) && f.getPlaces() >= peoplesCount && f.getDate().toLocalDate().equals(date))
                .map(Flight::toString)
                .collect(Collectors.joining("\n"));

        if (searchResult.isEmpty()) {
            console.printLine("No flight founded!");
            return;
        }

        console.printLine(searchResult);
        console.printLine("Enter flight ID:");
        String flight_id = console.readString();
        Optional<Flight> flight = flightsService.get(flight_id);

        if (!flight.isPresent()) {
            console.printLine("Incorrect flight ID!");
            return;
        }
        console.printLine(flight.get().toString());

        // TODO: Add booking
    }

    public static boolean isAuthenticated() {
        return user != null;
    }

    public static void loginCallback(Console console) {
        user = new User("Name", "Surname", "login", "password");
        console.printLine("Shadow logged...");
    }

    public static void logoutCallback(Console console) {
        user = null;
    }

    public static String getValue(Console console, String message, Predicate<String> predicate, String error) {
        console.printLine(message);
        return console.readString(predicate, error);
    }

    public static String getValue(Console console, String message) {
        return getValue(console, message, s -> true, "");
    }

    public static void registerCallback(Console console) {
        String name = getValue(console, "enter your name");
        String surname = getValue(console, "enter your surname");
        String login = getValue(console, "enter your login");
        String password = getValue(console, "enter your password", s -> s.length() > 6, "password length should me greater than 6");

        user = new User(name, surname, login, password);
        console.printLine(String.format("User is registered! Hello, @%s", user.login));
    }

    public static void displayMyFlightsCallback(Console console) {
        user.getAllTickets().stream().map(Ticket::toString).forEach(console::printLine);
    }

    public static void cancelFlightBookCallback(Console console) {
        console.printLine("Your tickets:");
        displayMyFlightsCallback(console);

        String flight_id = getValue(console, "enter flight id");
    }

    public static ArrayList<Callback> getCallbacks() {
        ArrayList<Callback> callbacks = new ArrayList<>();

        callbacks.add(new Callback("Display online table", Main::showSchedule));
        callbacks.add(new Callback("Display flight information", Main::showFlightInformation));
        if (!isAuthenticated()) {
            callbacks.add(new Callback("Login", Main::loginCallback));
            callbacks.add(new Callback("Register", Main::registerCallback));
        } else {
            callbacks.add(new Callback("Cancel flight book", Main::cancelFlightBookCallback));
            callbacks.add(new Callback("Search and book flight", Main::searchAndBookFlight));
            callbacks.add(new Callback("Display my flights", Main::displayMyFlightsCallback));
            callbacks.add(new Callback("Logout", Main::logoutCallback));
        }
        callbacks.add(exitCallback);

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
