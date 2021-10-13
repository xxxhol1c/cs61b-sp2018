import org.junit.Test;
import static org.junit.Assert.*;

public class ArraryDequeTest {

    @Test
    public void addTest() {
        ArrayDeque<Integer> test0 = new ArrayDeque<>();
        assertTrue(test0.isEmpty());
        test0.addFirst(1);
        test0.addFirst(2);
        test0.addLast(3);
        test0.addLast(4);
        assertEquals(4, test0.size());
        test0.addLast(5);
        assertEquals(5, test0.size());
        // it should be [2][1][3][4][5]
        test0.printDeque();
    }

    @Test
    public void removeTest() {
        ArrayDeque<Integer> test1 = new ArrayDeque<>();
        assertTrue(test1.isEmpty());
        test1.addFirst(1);
        test1.addFirst(2);
        test1.addLast(2);
        test1.addLast(3);
        test1.addLast(4);
        assertEquals(5, test1.size());
        int removedFirst = test1.removeFirst();
        int removedLast = test1.removeLast();
        assertEquals(2, removedFirst);
        assertEquals(4, removedLast);
        assertEquals(3, test1.size());
        // it should be [1][2][3]
        test1.printDeque();
        test1.removeLast();
        test1.removeLast();
        test1.removeLast();
        test1.removeLast();
        assertEquals(0, test1.size());
        test1.addLast(2);
        assertEquals(1, test1.size());
    }

    @Test
    public void addRemoveGet() {
        ArrayDeque<Integer> test2 = new ArrayDeque<>();
        test2.addFirst(0);
        int point1 = test2.get(0);
        assertEquals(0, point1);
        int point2 = test2.removeLast();
        assertEquals(0, point2);
        test2.addFirst(5);
        test2.addFirst(6);
        test2.addLast(7);
        test2.addFirst(8);
        test2.addFirst(9);
        test2.addFirst(10);
        test2.addLast(11);
        int point3 = test2.get(6);
        assertEquals(11, point3);
        test2.addLast(13);
        int point4 = test2.removeLast();
        assertEquals(13, point4);
        int point5 = test2.removeFirst();
        assertEquals(10, point5);
    }

    @Test
    public void getTest() {
        ArrayDeque<Integer> test3 = new ArrayDeque<>();
        test3.addFirst(1);
        test3.addFirst(2);
        test3.addLast(3);
        test3.addLast(4);
        test3.addLast(5);
        test3.addLast(6);
        test3.addFirst(7);
        test3.addLast(8);
        // it should be [7][2][1][3][4][5][6][8]
        int actual1 = test3.get(0);
        assertEquals(7, actual1);
        int actual2 = test3.get(5);
        assertEquals(5, actual2);
        int actual3 = test3.get(7);
        assertEquals(8, actual3);
    }

    @Test
    public void addResizeTest() {
        ArrayDeque<Integer> test4 = new ArrayDeque<>();
        for (int i = 0; i < 5; i++) {
            test4.addFirst(i);
        }
        for (int i = 5; i < 10; i++) {
            test4.addLast(i);
        }
        // it should be [4][3][2][1][0][5][6][7][8][9]
        test4.printDeque();
        assertEquals(10, test4.size());
        int actual1 = test4.get(0);
        assertEquals(4, actual1);
        int actual2 = test4.get(9);
        assertEquals(9, actual2);

        ArrayDeque<Integer> test5 = new ArrayDeque<>();
        for (int i = 0; i < 16; i++) {
            test5.addFirst(i);
        }
        // it should be [99]....[0]
        test5.printDeque();
        assertEquals(16, test5.size());
        int actual3 = test5.get(0);
        assertEquals(15, actual3);
        int actual4 = test5.get(15);
        assertEquals(0, actual4);

        ArrayDeque<Integer> test6 = new ArrayDeque<>();
        for (int i = 0; i < 100; i++) {
            test6.addLast(i);
        }
        // it should be [0]....[99]
        test6.printDeque();
        assertEquals(100, test6.size());
        int actual5 = test6.get(0);
        assertEquals(0, actual5);
        int actual6 = test6.get(99);
        assertEquals(99, actual6);
    }

    @Test
    public void removeCutTest() {
        ArrayDeque<Integer> test7 = new ArrayDeque<>();
        for (int i = 0; i < 100; i++) {
            test7.addFirst(i);
        }
        for (int i = 0; i < 100; i++) {
            test7.removeFirst();
            /* GradeScope need the capacity to be private so
            if (test7.capacity >= 16) {
                double usage = (double) test7.size() / test7.capacity;
                assertTrue(usage >= 0.25);
            } */
        }
    }
}
