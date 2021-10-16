
import org.junit.Test;
import static org.junit.Assert.*;


public class TestOffByN {
    static OffByN offByFive = new OffByN(5);

    @Test
    public void testOffBy5() {
        assertTrue(offByFive.equalChars('a', 'f'));
        assertTrue(offByFive.equalChars('f', 'a'));
        assertTrue(offByFive.equalChars('#', '('));
        assertTrue(offByFive.equalChars('A', 'F'));
        assertFalse(offByFive.equalChars('a', 'j'));
        assertFalse(offByFive.equalChars('A', 'b'));
        assertFalse(offByFive.equalChars('A', 'C'));
        assertFalse(offByFive.equalChars('C', 'C'));
    }

}
