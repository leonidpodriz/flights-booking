package Console;

import java.util.function.Consumer;

public class Callback {
    String name = "Default callback";
    Consumer<Console> toRun;

    public Callback(String name) {
        this.name = name;
    }

    public Callback(String name, Consumer<Console> toRun) {
        this(name);
        this.toRun = toRun;
    }

    public Callback() {
    }

    public void run(Console console) {
        if (toRun != null) {
            toRun.accept(console);
        }
    }

    @Override
    public String toString() {
        return name;
    }
}
