import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;


public class PositionTest {
    @Test
    public void advance() throws Exception {
        Position.of(0, 0).advance(Direction.left).ifPresent(p -> Assert.fail(p.toString()));
        Position.of(0, 10).advance(Direction.left).ifPresent(p -> Assert.fail(p.toString()));
        Position.of(30, 5).advance(Direction.right).ifPresent(p -> Assert.fail(p.toString()));
        Position.of(1, 0).advance(Direction.down).ifPresent(p -> Assert.fail(p.toString()));
        Position.of(1, 30).advance(Direction.up).ifPresent(p -> Assert.fail(p.toString()));

        Position.of(15, 15).advance(Direction.left).ifPresent(p -> {
            assertEquals(Position.of(14, 15), p);
        });
        Position.of(15, 15).advance(Direction.right).ifPresent(p -> {
            assertEquals(Position.of(16, 15), p);
        });
        Position.of(15, 15).advance(Direction.up).ifPresent(p -> {
            assertEquals(Position.of(15, 16), p);
        });
        Position.of(15, 15).advance(Direction.down).ifPresent(p -> {
            assertEquals(Position.of(15, 14), p);
        });

    }

}