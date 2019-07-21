package minic.strategy;

import org.junit.Test;

import java.util.Arrays;
import java.util.Iterator;

import static org.junit.Assert.*;

public class MultiIntIteratorTest {
    @Test
    public void testSingle() {
        MultiIntIterator single = new MultiIntIterator(new Integer[]{10});
        printall(single);

    }
    @Test
    public void testTwo() {
        MultiIntIterator two = new MultiIntIterator(new Integer[]{2,4});
        printall(two);
    }
    @Test
    public void testThree() {
        MultiIntIterator two = new MultiIntIterator(new Integer[]{10,10, 10});
        printall(two);
    }

    static void printall(MultiIntIterator it) {
        for (Iterator<Integer[]> iterator = it; iterator.hasNext(); ) {
            Integer[] next = iterator.next();
            System.out.println(Arrays.toString(next));

        }


    }
}