import Booking.Ticket;
import Flights.Flight.Flight;
import Flights.FlightsGenerator;
import Users.User.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

public class TestUser {
    private User testUser;
    private  Flight flight;
    private Ticket testTicket;

    @BeforeEach
    public void createUser(){
        testUser = new User("test", "test", "test", "12345");
        flight = FlightsGenerator.generateFlights(1).get(0);
        testTicket = new Ticket(testUser, flight);
    }

    @Test
    public void authTest(){
        assertFalse(testUser.isAuth);
        testUser.setIsAuth();
        assertTrue(testUser.isAuth);
        testUser.logout();
        assertFalse(testUser.isAuth);
    }

    @Test
    public void checkUser(){
        String wrongUserName = "wrong";
        String wrongPassword = "wrong";
        assertFalse(testUser.checkUser(wrongUserName, wrongPassword));
        assertFalse(testUser.isAuth);
        String rightUserName = "test";
        String rightPassword = "12345";
        assertTrue(testUser.checkUser(rightUserName, rightPassword));
        assertTrue(testUser.isAuth);
    }

    @Test
    public void userEquals(){
        User newTestUser = new User("test", "test", "test", "12345");
        assertEquals(testUser, newTestUser);
        newTestUser = new User("test1", "test1", "test1", "123456");
        assertNotEquals(testUser, newTestUser);
    }

    @Test
    public void addTicket(){
        testUser.addTicket(testTicket);
        Ticket[] testArr = {testTicket};
        assertArrayEquals(testArr, testUser.getAllTickets().toArray());
    }

    @Test
    public void removeTicket(){
        testUser.addTicket(testTicket);
        testUser.removeTicket(testTicket.ticketNumber);
        assertEquals(testUser.getAllTickets().size(), 0);
    }
}
