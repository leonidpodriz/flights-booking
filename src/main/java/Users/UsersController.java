package Users;

import Users.User.User;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class UsersController {
    private final UsersService service = new UsersService();

    public void initializeDb() throws IOException, ClassNotFoundException {service.initializeDb();}

    public List<User> getAllUsers(){return service.getAllUsers();}

    public Optional<User> get(String login){return service.get(login);}

    public void update(String login, User newUser) throws IOException { service.update(login, newUser);}

    public boolean remove(String login) throws IOException {  return service.remove(login);}

    public boolean remove(User user) throws IOException {  return service.remove(user);}

    public void addUser(User user) throws IOException { service.addUser(user);}
}
