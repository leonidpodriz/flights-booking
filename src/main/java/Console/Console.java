package Console;

import java.util.function.Predicate;

public interface Console {
    void run();

    void prepareToExit();

    int readInt(Predicate<Integer> predicate, String description);
    int readInt();

    String readString();

    String readString(Predicate<String> predicate, String description);

    void print(String text);

    void printLine(String text);
}
