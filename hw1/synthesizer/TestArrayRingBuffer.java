package synthesizer;
import org.junit.Test;
import static org.junit.Assert.*;

/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug
 */

public class TestArrayRingBuffer {
    @Test
    public void addRemoveTest() {
        ArrayRingBuffer<Integer> arb = new ArrayRingBuffer<>(10);
        assertTrue(arb.isEmpty());
        for (int i = 0; i < 10; i += 1) {
            arb.enqueue(i);
        }
        assertTrue(arb.isFull());
        assertEquals((Integer) 0, arb.dequeue());
        assertEquals((Integer) 1, arb.peek());
        assertFalse(arb.isFull());
        arb.enqueue(11);
        assertTrue(arb.isFull());
        for (Integer i: arb) {
            System.out.println(i);
        }
    }

    /** Calls tests for ArrayRingBuffer. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestArrayRingBuffer.class);
    }
} 
