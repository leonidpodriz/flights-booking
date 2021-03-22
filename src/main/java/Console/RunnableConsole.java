package Console;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;

public class RunnableConsole implements Console {
    final List<Callback> callbackList;
    final PrintStream out = System.out;
    private boolean exit = false;
    Scanner scanner = new Scanner(System.in);

    public RunnableConsole(Callback... callbacks) {
        callbackList = Arrays.asList(callbacks);
    }

    @Override
    public void prepareToExit() {
        exit = true;
    }

    @Override
    public void print(String text) {
        out.print(text);
    }

    public void printInvalidValue(String expected) {
        print(String.format("Invalid value! Expected %s.\n", expected));
    }

    public void printInvalidChoice() {
        printInvalidValue(getExpectedChoice());
    }

    public void printPrompt() {
        print(">>> ");
    }

    private void printBreak() {
        print("---\n");
    }

    private boolean isChoiceValid(int choice) {
        return choice <= callbackList.size() && choice > 0;
    }

    private String getExpectedChoice() {
        return String.format("integer between %d and %d", 1, callbackList.size());
    }

    private void printMe() {
        print(this.toString());
    }

    @Override
    public int readInt() {
        while (true) {
            printPrompt();
            if (scanner.hasNextInt()) {
                return scanner.nextInt();
            } else {
                scanner.next();
                printInvalidChoice();
            }
        }
    }

    private int readChoice() {
        int choice;

        while (true) {
            choice = readInt();
            if (isChoiceValid(choice)) {
                return choice;
            } else {
                printInvalidChoice();
            }
        }
    }

    @Override
    public String readString() {
        return scanner.next();
    }

    @Override
    public String readString(Predicate<String> predicate, String description) {
        String string;
        while (true) {
            string = readString();
            if (predicate.test(string)) {
                return string;
            } else {
                printInvalidValue(description);
            }
        }
    }

    @Override
    public void run() {

        while (!exit) {
            printMe();
            callbackList.get(readChoice() - 1).run(this);
            printBreak();
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < callbackList.size(); i++) {
            Callback callback = callbackList.get(i);
            stringBuilder.append(String.format("%2d) %s\n", i + 1, callback));
        }

        return stringBuilder.toString();
    }
}
