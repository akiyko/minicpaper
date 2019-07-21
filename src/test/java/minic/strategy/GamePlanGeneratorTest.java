package minic.strategy;

import minic.GamePlan;
import minic.dto.Direction;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

import static org.junit.Assert.*;

public class GamePlanGeneratorTest {
    @Test
    public void testComb() {
        List<GamePlan> result = new ArrayList<>();
        MultiIntIterator turnsIterator = new MultiIntIterator(GamePlanGenerator.intArrayofLen(2, 3));

        while (turnsIterator.hasNext()) {
            Integer[] turnsSequence = turnsIterator.next();

            System.out.println(Arrays.toString(turnsSequence));

        }
    }

    @Test
    public void testAllOf() {
        List<GamePlan> gps = GamePlanGenerator.allMovePlansOf(3, 5);

        assertEquals(1000, gps.size());
    }

    @Test
    public void tetAllpermutations() {
        List<List<Direction>> options = GamePlanGenerator.allPermutations(EnumSet.allOf(Direction.class));

        assertEquals(24, options.size());

        for (List<Direction> option : options) {
            System.out.println(option);
        }
    }
}