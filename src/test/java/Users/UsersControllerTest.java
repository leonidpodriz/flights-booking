package Users;

import Users.User.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UsersControllerTest {
    private final UsersController controller = new UsersController();
    private final User user = new User("test", "test", "test", "12345");
    private List<User> users;

    @BeforeEach
    public void generate() throws IOException, ClassNotFoundException {
        controller.initializeDb();
        users = controller.getAllUsers();
    }


    @Test
    public void addUser() throws IOException {
        int oldSize = controller.getAllUsers().size();
        controller.addUser(user);
        users = controller.getAllUsers();
        assertEquals(++oldSize, users.size());
        assertEquals(users.get(oldSize - 1), user);
    }

    @Test
    public void removeUser() throws IOException {
        int oldSize = controller.getAllUsers().size();
        controller.addUser(user);
        assertEquals(oldSize + 1, users.size());
        controller.remove(user);
        users = controller.getAllUsers();
        assertEquals(oldSize, users.size());
    }

    @Test
    public void update() throws IOException {
        controller.addUser(user);
        int oldSize = controller.getAllUsers().size();
        User newUser = new User("test1", "test1", "test1", "test");
        controller.update(user.login, newUser);
        users = controller.getAllUsers();
        assertEquals(oldSize, users.size());
        assertTrue(users.contains(newUser));
        controller.remove(newUser);
    }
}
