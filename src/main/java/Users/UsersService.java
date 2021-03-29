package Users;

import Users.User.User;

import java.io.*;
import java.util.*;

public class UsersService implements DAO {

    private final File dbDir = new File("db");
    private final File dbFile = new File("db/users.bin");

    private final List<User> users = new ArrayList<>();

    public void initializeDb() throws IOException, ClassNotFoundException {
        boolean mkdir = dbDir.mkdir();
        if (mkdir) {
            boolean newFile = dbFile.createNewFile();
            writeList();
        } else {
            if (dbFile.exists()) {
                users.addAll(readUsers());
            } else {
                boolean newFile = dbFile.createNewFile();
                writeList();
            }
        }
    }

    private List<User> readUsers() throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(dbFile));
        ArrayList<User> users = (ArrayList<User>) ois.readObject();
        ois.close();
        return users;
    }

    private void writeList() throws IOException {
        dbFile.delete();
        dbFile.createNewFile();
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(dbFile));
        oos.writeObject(users);
        oos.close();
    }

    @Override
    public void addUser(User user) throws IOException {
        if (users.contains(user)) return;
        users.add(user);
        writeList();
    }

    @Override
    public List<User> getAllUsers() {
        return users;
    }

    @Override
    public Optional<User> get(String login) {
        return users.stream().filter(x -> x.login.equals(login)).findFirst();
    }

    @Override
    public void update(String login, User newUser) throws IOException {
        users.removeIf(x -> x.login.equals(login));
        users.add(newUser);
        writeList();
    }

    @Override
    public boolean remove(String login) throws IOException {
        return checkAndWrite(users.removeIf(x -> x.login.equals(login)));
    }

    public boolean checkAndWrite(boolean isDeleted) throws IOException {
        if (isDeleted) { writeList();}
        return isDeleted;
    }

    @Override
    public boolean remove(User user) throws IOException {
        return checkAndWrite(users.remove(user));
    }
}
