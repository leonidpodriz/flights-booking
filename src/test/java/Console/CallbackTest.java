package Console;

import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.*;

public class CallbackTest {

    @Test
    public void testCallbackWithFulfilledConstructor() {
        AtomicBoolean wasRun = new AtomicBoolean(false);
        Consumer<Console> runner = console -> wasRun.set(true);
        String callbackName = "Test callback";
        Callback callback = new Callback(callbackName, runner);

        callback.run(new RunnableConsole());

        assertTrue(wasRun.get());
        assertEquals(callbackName, callback.toString());
    }

    @Test
    public void testCallbackWithNameConstructorWithoutRunner() {
        String callbackName = "Test callback 2";
        Callback callback = new Callback(callbackName);


        assertEquals(callbackName, callback.toString());
        RuntimeException runtimeException = assertThrows(RuntimeException.class, () -> callback.run(new RunnableConsole()));

        String expectedMessage = "Runner was not provided!";
        String actualMessage = runtimeException.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void testCallbackWithNameConstructorAndOverriddenRun() {
        AtomicBoolean wasRun = new AtomicBoolean(false);
        Consumer<Console> runner = console -> wasRun.set(true);
        String callbackName = "Test callback 2";
        Callback callback = new Callback(callbackName, runner) {
            @Override
            public void run(Console console) {
                wasRun.set(true);
            }
        };

        callback.run(new RunnableConsole());

        assertTrue(wasRun.get());
        assertEquals(callbackName, callback.toString());
    }

    @Test
    public void testCallbackWithEmptyConstructor() {
        Callback callback = new Callback();

        assertEquals("Callback", callback.toString());
        assertThrows(RuntimeException.class, () -> callback.run(new RunnableConsole()));
    }
}
