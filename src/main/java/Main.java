import Booking.BookingController;
import Booking.Ticket;
import Console.Callback;
import Console.Console;
import Console.ExitCallback;
import Console.RunnableConsole;
import Flights.Flight.Flight;
import Flights.FlightsService;
import Schedule.Schedule;
import Users.User.User;
import Users.UsersController;

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
    static UsersController usersController = new UsersController();
    static BookingController bookingController = new BookingController();
    static Console console = new RunnableConsole(Main::getCallbacks);

    public static void onStart() throws IOException, ClassNotFoundException {
        flightsService.initializeDb();
        usersController.initializeDb();
        bookingController.initializeDb();
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
        Flight bookedFight = flight.get();


        List<Ticket> bookingTickets = new ArrayList<>();
        for (int i = 1; i <= peoplesCount; i++) {
            Ticket ticket = new Ticket(user, bookedFight);
            user.addTicket(ticket);
            bookedFight.addTicket(ticket);
            bookingTickets.add(ticket);
        }
        bookedFight.setPlaces(bookedFight.getPlaces() - peoplesCount);

        try {
            bookingController.add(bookingTickets);
            console.println(String.format("Congratulations ! You have booked %d ticket%c!", peoplesCount, peoplesCount > 1 ? 's' : ' '));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean isAuthenticated() {
        return user != null;
    }

    public static void loginCallback(Console console) {
        String login = getValue(console, "enter your login");
        String password = getValue(console, "enter your password", s -> s.length() != 0, "password length should me greater than zero");
        List<User> allUsers = usersController.getAllUsers();
        Optional<User> first = allUsers.stream().filter(x -> x.login.equals(login)).findFirst();
        if (first.isPresent()) {
            boolean isUser = first.get().checkUser(login, password);
            if (isUser) {
                console.println("Shadow logged...");
                user = new User("Name", "Surname", "login", "password");
            } else {
                console.println("Wrong password !");
                logoutCallback(console);
            }
        } else {
            console.println("Wrong login !");
            logoutCallback(console);
        }
    }

    public static void logoutCallback(Console console) {
        user = null;
    }

    public static String getValue(Console console, String message, Predicate<String> predicate, String error) {
        console.println(message);
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
        try {
            usersController.addUser(user);
        } catch (IOException e) {
        }
        console.println(String.format("User is registered! Hello, @%s", user.login));
    }

    public static void displayMyFlightsCallback(Console console) {
        user.getAllTickets().stream().map(Ticket::toString).forEach(console::println);
    }

    public static void cancelFlightBookCallback(Console console) {
        console.println("Your tickets:");
        displayMyFlightsCallback(console);

        String ticketNumber = getValue(console, "enter ticket number", n -> bookingController.getAll().stream().anyMatch(t -> t.ticketNumber.equals(n)), "enter the valid ticket number");
        try {
            bookingController.remove(ticketNumber);
            user.tickets.removeIf(x -> x.ticketNumber.equals(ticketNumber));
        } catch (IOException e) {
            e.printStackTrace();
        }
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
