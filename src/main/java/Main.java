import Console.Callback;
import Console.Console;
import Console.ExitCallback;
import Console.RunnableConsole;
import Users.User.User;
import Users.UsersController;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        // Just example, remove it!
        Callback exitCallback = new ExitCallback();
        Callback printHello = new Callback() {
            @Override
            public void run(Console console) {
                console.print("What is your name?\n");
                console.print(String.format("Hello, %s!\n", console.readString(
                        (String string) -> string.length() > 4,
                        "name greater than 4 symbols"
                )));
            }

            @Override
            public String toString() {
                return "Make world smile";
            }
        };
//        Console console = new RunnableConsole(printHello, exitCallback);/

//        console.run();



//        UsersController controller = new UsersController();
//
//        User testUser = new User("test", "test", "test", "12345");
//
//        try {
//            controller.initializeDb();
//            controller.addUser(testUser);
//            controller.addUser(testUser);
//            controller.addUser(testUser);
//            System.out.println(controller.getAllUsers());
//            System.out.println(controller.getAllUsers());
//        } catch (IOException | ClassNotFoundException e) {
//            e.printStackTrace();
//        }
    }
}
