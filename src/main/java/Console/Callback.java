package Console;

import java.util.function.Consumer;

public class Callback {
    static final String RUNNER_NOT_PROVIDED_EXCEPTION_MESSAGE = "Runner was not provided!";
    static final String DEFAULT_NAME = "Callback";
    static final Consumer<Console> DEFAULT_RUNNER = console -> {
        throw new RuntimeException(RUNNER_NOT_PROVIDED_EXCEPTION_MESSAGE);
    };

    final String name;
    final Consumer<Console> runner;

    public Callback(String name, Consumer<Console> toRun) {
        this.name = name;
        this.runner = toRun;
    }

    public Callback(String name) {
        this(name, DEFAULT_RUNNER);
    }

    public Callback() {
        this(DEFAULT_NAME, DEFAULT_RUNNER);
    }

    private boolean isToRunPresent() {
        return runner != null;
    }

    private void acceptToRunner(Console console) {
        runner.accept(console);
    }

    public void run(Console console) {
        if (isToRunPresent()) acceptToRunner(console);
    }

    @Override
    public String toString() {
        return name;
    }
}
