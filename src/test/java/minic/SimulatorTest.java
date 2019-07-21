package minic;

import minic.dto.Direction;
import minic.dto.TickDto;
import minic.dto.Turn;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.Reader;

import static org.junit.Assert.*;

public class SimulatorTest {

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

    void testCloseAt(int left1, int left2, int left3, int expectedFinishOnMyTerrCellTick) {
        GameState gs = standardGameState();

        GamePlan gp = new GamePlan();
        gp.movePlan.put(left1, Turn.LEFT);
        gp.movePlan.put(left2, Turn.LEFT);
        gp.movePlan.put(left3, Turn.LEFT);;

        SimpleOutcome so = Simulator.checkMovePath(gs, gp, 0);

        assertFalse(so.valid);
        Assert.assertEquals(-1, so.crossMyTraceCellTick);
        Assert.assertEquals(expectedFinishOnMyTerrCellTick, so.finishOnMyTerrCellTick);
    }

    void testCrossTraceAt(int left1, int left2, int left3, int expectedCrossTick) {
        GameState gs = standardGameState();

        GamePlan gp = new GamePlan();
        gp.movePlan.put(left1, Turn.LEFT);
        gp.movePlan.put(left2, Turn.LEFT);
        gp.movePlan.put(left3, Turn.LEFT);

        SimpleOutcome so = Simulator.checkMovePath(gs, gp, 0);

        assertFalse(so.valid);
        Assert.assertEquals(expectedCrossTick, so.crossMyTraceCellTick);
    }


    void testReachWallAt(int left1, int left2, int left3, int expectedBorderTick) {
        GameState gs = standardGameState();

        GamePlan gp = new GamePlan();
        gp.movePlan.put(left1, Turn.LEFT);
        gp.movePlan.put(left2, Turn.LEFT);
        gp.movePlan.put(left3, Turn.LEFT);

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