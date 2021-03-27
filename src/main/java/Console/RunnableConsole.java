package Console;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class RunnableConsole implements Console {
    final Supplier<List<Callback>> callbackSupplier;
    final PrintStream out = System.out;
    private boolean exit = false;
    Scanner scanner = new Scanner(System.in);

    public RunnableConsole(Callback... callbacks) {
        this(() -> Arrays.asList(callbacks));
    }

    public RunnableConsole(Supplier<List<Callback>> callbackSupplier) {
        this.callbackSupplier = callbackSupplier;
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

    public void println(String text) {
        print(String.format("%s\n", text));
    }

    private void printBreak() {
        print("---\n");
    }

    private boolean isChoiceValid(int choice) {
        return choice <= callbackSupplier.get().size() && choice > 0;
    }

    private String getExpectedChoice() {
        return String.format("integer between %d and %d", 1, callbackSupplier.get().size());
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

    @Override
    public int readInt(Predicate<Integer> predicate, String description) {
        int number;
        while (true) {
            number = readInt();
            if (predicate.test(number)) {
                return number;
            } else {
                printInvalidValue(description);
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
            callbackSupplier.get().get(readChoice() - 1).run(this);
            printBreak();
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        List<Callback> callbacks = callbackSupplier.get();

        for (int i = 0; i < callbacks.size(); i++) {
            Callback callback = callbacks.get(i);
            stringBuilder.append(String.format("%2d) %s\n", i + 1, callback));
        }

        return stringBuilder.toString();
    }
}
