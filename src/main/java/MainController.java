import Booking.BookingController;
import Flights.FlightController;
import Users.UsersController;

import java.io.IOException;

public class MainController {
    public final UsersController usersController = new UsersController();
    public final FlightController flightController = new FlightController();
    public final BookingController bookingController = new BookingController();

    public void initializeDb() throws IOException, ClassNotFoundException {
        usersController.initializeDb();
        flightController.initializeDb();
        bookingController.initializeDb();
    }
}
