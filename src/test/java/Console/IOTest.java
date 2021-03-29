package Console;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.InputMismatchException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

    IO getIo() {
        return new IO();
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
        io.printConditionError("some value", "it should be another value");
        assertEquals("ERROR: The value (some value) is mismatching next condition: it should be another value\n", getOutput());
    }

    @Test
    void printLine() {
        io.printLine("String");
        assertEquals("String\n", getOutput());
    }

    @Test
    void readString() {
        provideInput("string");
        String s = getIo().readString();
        assertEquals("string", s);
        assertEquals(">>> ", getOutput());
    }

    @Test
    void testReadValidString() {
        provideInput("string");
        String s = getIo().readString(str -> str.contains("str"), "string should contains 'str'");
        assertEquals("string", s);
        assertEquals(">>> ", getOutput());
    }

    @Test
    void testReadInvalidString() {
        provideInput("ing\nstring");
        String s = getIo().readString(str -> str.contains("str"), "string should contains 'str'");
        assertEquals("string", s);
        assertEquals(">>> ERROR: The value (ing) is mismatching next condition: string should contains 'str'\n>>> ", getOutput());
    }

    @Test
    void testReadValidInt() {
        provideInput("1");
        int i = getIo().readInt();
        assertEquals(1, i);
    }

    @Test
    void testReadInvalidInt() {
        provideInput("string");
        assertThrows(InputMismatchException.class, getIo()::readInt);
    }

    @Test
    void testReadInvalidIntWithErrorMessage() {
        provideInput("test\n1");
        int i = getIo().readInt("Invalid integer value!");
        assertEquals(1, i);
        assertEquals(">>> >>> ERROR: Invalid integer value!\n>>> ", getOutput());
    }

    @Test
    void testReadValidIntWithPredicate() {
        provideInput("1");
        int i1 = getIo().readInt("Invalid integer.", i -> i < 10, "less than 10");
        assertEquals(1, i1);
        assertEquals(">>> ", getOutput());
    }

    @Test
    void testReadInvalidIntWithPredicate() {
        provideInput("10\n5");
        int i1 = getIo().readInt("Invalid integer.", i -> i < 10, "less than 10");
        assertEquals(5, i1);
        assertEquals(">>> ERROR: The value (10) is mismatching next condition: less than 10\n>>> ", getOutput());
    }

    @Test
    void printString() {
        io.printString("String");
        assertEquals("String", getOutput());
    }

    @Test
    void printInfoMessage() {
        io.printMessage(IO.MessageType.INFO, "info message");
        assertEquals("INFO: info message\n", getOutput());
    }

    @Test
    void printWarningMessage() {
        io.printMessage(IO.MessageType.WARNING, "warning message");
        assertEquals("WARNING: warning message\n", getOutput());
    }
}