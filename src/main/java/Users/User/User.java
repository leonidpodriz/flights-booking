package Users.User;

import Booking.Ticket;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {
    public  String name;
    public  String surname;
    public  String login;
    private String password;
    public final List<Ticket> tickets = new ArrayList<>();

    public User(String name, String surname, String login, String password) {
        this.name = name;
        this.surname = surname;
        this.login = login;
        this.password = password;
    }


    public boolean checkUser(String login, String password){
        boolean check = login.equals(this.login) && password.equals(this.password);
        if (check){
            return true;
        }
        return false;
    }

    public void addTicket(Ticket ticket){
        tickets.add(ticket);
    }

    public void removeTicket(String ticketNumber){
        tickets.removeIf(x -> x.ticketNumber.equals(ticketNumber));
    }

    public List<Ticket> getAllTickets(){
        return tickets;
    }

    @Override
    public String toString(){
        return String.format("User name -> %s, user surname -> %s, user login -> %s", name, surname, login);
    }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return this.login.equals(user.login);}
}
