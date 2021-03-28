package Users;

import Users.User.User;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface DAO {
    public List<User> getAllUsers();

    public Optional<User> get(String login);

    public void addUser(User user) throws IOException ;

    public void update(String login, User neUser) throws IOException;

    public boolean remove(String login) throws IOException;

    public boolean remove(User user) throws IOException;
}
