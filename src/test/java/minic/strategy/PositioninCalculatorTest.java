package minic.strategy;

import org.junit.Test;

import static minic.Position.of;
import static org.junit.Assert.*;

public class PositioninCalculatorTest {

    @Test
    public void between() {
        DirectionTo dt = PositioninCalculator.between(of(3,2), of(6,4));

        assertEquals(3, dt.right);
        assertEquals(2, dt.up);
    }
    @Test
    public void len() {
        DirectionTo dt = PositioninCalculator.between(of(3,2), of(6,4));
        DirectionTo dt2 = PositioninCalculator.between(of(8,6), of(3,2));

        assertEquals(5, dt.len());
        assertEquals(9, dt2.len());

        assertEquals(-1, dt.compareTo(dt2));

    }
}