import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class TestMain {
    @Test
    public void testAdd() {
        assertEquals(Main.add(3, 5), 8);
    }
}
