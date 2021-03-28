package Console;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IOTest {
    private final InputStream systemIn = System.in;
    private final PrintStream systemOut = System.out;

    private IO io;
    private ByteArrayOutputStream testOut;

    @BeforeEach
    public void setUpOutput() {
        testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));
        io = new IO();
    }

    private void provideInput(String data) {
        ByteArrayInputStream testIn = new ByteArrayInputStream(data.getBytes());
        System.setIn(testIn);
    }

    private String getOutput() {
        return testOut.toString();
    }

    @AfterEach
    public void restoreSystemInputOutput() {
        System.setIn(systemIn);
        System.setOut(systemOut);
    }

    @Test
    void printError() {
        io.printError("Some error");
        assertEquals(String.format("%s: %s\n", IO.MessageType.ERROR.name(), "Some error"), getOutput());
    }

    @Test
    void printConditionError() {
    }

    @Test
    void printLine() {
        io.printLine("String");
        assertEquals("String\n", getOutput());
    }

    @Test
    void readString() {
    }

    @Test
    void testPredicate() {
    }

    @Test
    void testReadString() {
    }

    @Test
    void readInt() {
    }

    @Test
    void testReadInt() {
    }

    @Test
    void testReadInt1() {
    }

    @Test
    void printString() {
        io.printString("String");
        assertEquals("String", getOutput());
    }

    @Test
    void printMessage() {
    }
}