import org.junit.Test;

import static org.junit.Assert.*;

public class TestArrayDequeGold {

    private static final int MAXCALL = 1000;

    private static String message = "";

    private static void addRandom(StudentArrayDeque<Integer> test,
                                  ArrayDequeSolution<Integer> solution,
                                  Integer i, double random) {
        if (random < 0.5) {
            test.addFirst(i);
            solution.addFirst(i);
            message += "\naddFirst(" + i + ")";
        } else {
            test.addLast(i);
            solution.addLast(i);
            message += "\naddLast(" + i + ")";
        }
    }

    private static void removeRandom(StudentArrayDeque<Integer> test,
                                     ArrayDequeSolution<Integer> solution,
                                     Integer i, double random) {
        Integer expected;
        Integer actual;
        if (random < 0.5) {
            expected = test.removeFirst();
            actual = solution.removeFirst();
            message += "\nremoveFirst()";
        } else {
            expected = test.removeLast();
            actual = solution.removeLast();
            message += "\nremoveLast()";
        }
        assertEquals(message, expected, actual);
    }

    @Test
    public void studentTest() {
        StudentArrayDeque<Integer> test = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> solution = new ArrayDequeSolution<>();
        for (int i = 0; i < MAXCALL; i += 1) {
            if (test.isEmpty()) {
                double random = StdRandom.uniform();
                addRandom(test, solution, i, random);
            } else {
                double random1 = StdRandom.uniform();
                double random2 = StdRandom.uniform();
                if (random1 > 0.5) {
                    addRandom(test, solution, i, random2);
                } else {
                    removeRandom(test, solution, i, random2);
                }
            }

        }
    }
}
