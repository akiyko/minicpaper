package minic.strategy;

import minic.dto.Direction;
import org.junit.Test;

import java.util.EnumSet;
import java.util.List;

import static org.junit.Assert.*;

public class GamePlanGeneratorTest {
    @Test
    public void tetAllpermutations() {
        List<List<Direction>> options = GamePlanGenerator.allPermutations(EnumSet.allOf(Direction.class));

        assertEquals(24, options.size());

        for (List<Direction> option : options) {
            System.out.println(option);
        }
    }
}