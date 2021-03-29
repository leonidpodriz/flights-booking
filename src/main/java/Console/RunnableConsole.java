package Console;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class RunnableConsole implements Console {
    final static String INVALID_INTEGER_MESSAGE = "Invalid number. Try again.";
    final Supplier<List<Callback>> callbackSupplier;
    final IO io;

    private boolean exit = false;


    public RunnableConsole(Supplier<List<Callback>> callbackSupplier) {
        this.callbackSupplier = callbackSupplier;
        io = new IO();
    }

    public RunnableConsole(Callback... callbacks) {
        this(() -> Arrays.asList(callbacks));
    }


    @Override
    public void prepareToExit() {
        exit = true;
    }

    public boolean isPreparedToExit() {
        return exit;
    }

    @Override
    public void print(String text) {
        io.printString(text);
    }

    @Override
    public void printLine(String text) {
        io.printLine(text);
    }

    public void printInvalidValue(String expected) {
        io.printConditionError("Invalid value!", expected);
    }

    @Override
    public int readInt() {
        return io.readInt(INVALID_INTEGER_MESSAGE);
    }

    @Override
    public int readInt(Predicate<Integer> predicate, String conditionDescription) {
        return io.readInt(INVALID_INTEGER_MESSAGE, predicate, conditionDescription);
    }

    private int readChoice() {
        return readInt(
                choice -> choice <= callbackSupplier.get().size() && choice > 0,
                String.format("Choice number should be between %d and %d", 1, callbackSupplier.get().size())
        );
    }

    @Override
    public String readString() {
        return io.readString();
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
            io.printLine(this.toString());
            callbackSupplier.get().get(readChoice() - 1).run(this);
            printLine("---");
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
