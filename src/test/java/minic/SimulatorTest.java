package minic;

import minic.dto.Direction;
import minic.dto.TickDto;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.Reader;

import static org.junit.Assert.*;

public class SimulatorTest {
    @Test
    public void testPathOutcomeRightRight() {
        GameState gs = standardGameState();

        GamePlan gp = new GamePlan();
        gp.movePlan.put(1, Direction.right);

        SimpleOutcome so = Simulator.checkMovePath(gs, gp, 0);

        assertFalse(so.valid);
    }

    @Test
    public void testCross1() {
        testCrossTraceAt(3,4,5,5);
        testCrossTraceAt(5,7,9,10);
    }

    @Test
    public void testBorder() {
        testReachWallAt(3,4,6,22);
        testReachWallAt(30,40,100,16);
    }

    @Test
    public void testNormal() {
        testCloseAt(2,3,4,4);
    }

    void testCloseAt(int up, int left, int down, int expectedFinishOnMyTerrCellTick) {
        GameState gs = standardGameState();

        GamePlan gp = new GamePlan();
        gp.movePlan.put(up, Direction.up);
        gp.movePlan.put(left, Direction.left);
        gp.movePlan.put(down, Direction.down);

        SimpleOutcome so = Simulator.checkMovePath(gs, gp, 0);

        assertFalse(so.valid);
        Assert.assertEquals(-1, so.crossMyTraceCellTick);
        Assert.assertEquals(expectedFinishOnMyTerrCellTick, so.finishOnMyTerrCellTick);
    }

    void testCrossTraceAt(int up, int left, int down, int expectedCrossTick) {
        GameState gs = standardGameState();

        GamePlan gp = new GamePlan();
        gp.movePlan.put(up, Direction.up);
        gp.movePlan.put(left, Direction.left);
        gp.movePlan.put(down, Direction.down);

        SimpleOutcome so = Simulator.checkMovePath(gs, gp, 0);

        assertFalse(so.valid);
        Assert.assertEquals(expectedCrossTick, so.crossMyTraceCellTick);
    }


    void testReachWallAt(int up, int left, int down, int expectedBorderTick) {
        GameState gs = standardGameState();

        GamePlan gp = new GamePlan();
        gp.movePlan.put(up, Direction.up);
        gp.movePlan.put(left, Direction.left);
        gp.movePlan.put(down, Direction.down);

        SimpleOutcome so = Simulator.checkMovePath(gs, gp, 0);

        assertFalse(so.valid);
        Assert.assertEquals(-1, so.crossMyTraceCellTick);
        Assert.assertEquals(expectedBorderTick, so.crossBorderCellTick);
    }


    static GameState standardGameState() {
        try (Reader r = JsonHelperTest.readFile("ticksamplegs.json")) {
            TickDto tickDto = JsonHelper.readTick(r);

            GameState gameState = GameState.fromTick(tickDto, JsonHelperTest.configDto);

            return gameState;
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}