package Console;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class RunnableConsoleTest {
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();


    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(standardOut);
    }

    @Test
    void isPreparedToExit() {
        RunnableConsole runnableConsole = new RunnableConsole();
        assertFalse(runnableConsole.isPreparedToExit());

        runnableConsole.prepareToExit();
        assertTrue(runnableConsole.isPreparedToExit());
    }

    @Test
    void print() {
        String expectedPrint = "Some string";
        RunnableConsole runnableConsole = new RunnableConsole();
        runnableConsole.print(expectedPrint);

        assertEquals(expectedPrint, outputStreamCaptor.toString());
    }

    @Test
    void printLine() {
        String stringToPrint = "Some string";
        String expectedPrint = String.format("%s\n", stringToPrint);
        RunnableConsole runnableConsole = new RunnableConsole();
        runnableConsole.printLine(stringToPrint);

        assertEquals(expectedPrint, outputStreamCaptor.toString());
    }

    @Test
    void printInvalidValue() {
    }

    @Test
    void printInvalidChoice() {
    }

    @Test
    void printPrompt() {
    }

    @Test
    void readInt() {
    }

    @Test
    void testReadInt() {
    }

    @Test
    void readString() {
    }

    @Test
    void testReadString() {
    }

    @Test
    void run() {
    }

    @Test
    void testToString() {

    }
}