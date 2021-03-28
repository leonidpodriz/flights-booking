package Console;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.function.Predicate;

public class IO {
    private final PrintStream out;
    private final InputStream in;
    private final Scanner scanner;
    private final String consolePrefix;

    enum MessageType {
        INFO, WARNING, ERROR
    }

    public IO(PrintStream out, InputStream in, String consolePrefix) {
        this.out = out;
        this.in = in;
        this.scanner = new Scanner(in);
        this.consolePrefix = consolePrefix;
    }

    public IO(PrintStream out, InputStream in) {
        this(out, in, ">>> ");
    }

    public IO() {
        this(System.out, System.in);
    }

    public void printString(String string) {
        out.print(string);
    }

    private void printConsolePrefix() {
        printString(consolePrefix);
    }

    public void printMessage(MessageType type, String message) {
        printLine(String.format("%s: %s", type.name(), message));
    }

    public void printError(String errorMessage) {
        printMessage(MessageType.ERROR, errorMessage);
    }

    public void printConditionError(Object value, String condition) {
        printError(String.format("The value (%s) is mismatching next condition: %s", value.toString(), condition));
    }

    public void printLine(String string) {
        printString(String.format("%s\n", string));
    }

    public String readString() {
        printConsolePrefix();
        return scanner.next();
    }

    public <T> boolean testPredicate(Predicate<T> predicate, T value, String predicateCondition) {
        if (predicate.test(value)) {
            return true;
        } else {
            printConditionError(value, predicateCondition);
        }
        return false;
    }

    public String readString(Predicate<String> predicate, String predicateCondition) {
        String string;

        while (true) {
            string = readString();
            if (predicate.test(string)) {
                break;
            } else {
                printConditionError(string, predicateCondition);
            }
        }

        return string;
    }

    public int readInt() throws InputMismatchException {
        printConsolePrefix();
        return scanner.nextInt();
    }

    public int readInt(String errorMessage) {
        Integer number = null;

        while (number == null) {
            try {
                number = readInt();
            } catch (InputMismatchException e) {
                printError(errorMessage);
            }
        }

        return number;
    }

    public int readInt(String errorMessage, Predicate<Integer> predicate, String predicateCondition) {
        int number;

        while (true) {
            number = readInt(errorMessage);
            if (predicate.test(number)) {
                break;
            } else {
                printConditionError(number, predicateCondition);
            }
        }

        return number;
    }
}
