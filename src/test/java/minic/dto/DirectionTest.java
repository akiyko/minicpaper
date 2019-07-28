package minic.dto;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class DirectionTest {
    @Test
    public void testTurns() {

        Arrays.stream(Direction.values())
                .forEach(d -> {
                    for (Turn turn : Turn.values()) {
                        if (d != Direction.none) {
                            System.out.println(d + ":" + turn + " = " + d.turn(turn));
                        }
                    }
                });
    }
}