package minic;

import minic.dto.Direction;
import minic.simulate.FillResult;
import minic.simulate.FollowTraceResult;
import org.junit.Test;

import java.util.Set;

import static minic.JsonHelperTest.configDto;
import static minic.Position.of;
import static org.junit.Assert.*;


public class GameStateTest {
    @Test
    public void fillTest1() throws Exception {
        GameState gs = GameState.emptyField(configDto);

        gs.withPlayer(configDto, 0,
                new Position[]{of(2, 2)},
                new Position[]{of(3, 2), of(4, 2), of(5, 2), of(5, 3), of(5, 4),
                        of(2, 4), of(3, 4), of(4, 4), of(5, 4),
                        of(2,3)},
                of(2, 2),
                Direction.down);
        Set<Position> enclosed = gs.findEnclosed(of(2,3), 0);

        assertFalse(enclosed.isEmpty());
        assertEquals(11, enclosed.size());
    }

    @Test
    public void followTraceStartingFrom() throws Exception {
        GameState gs = GameState.emptyField(configDto);

        gs.withPlayer(configDto, 0,
                new Position[]{},
                new Position[]{of(2, 2), of(3, 2), of(3, 3), of(4, 2)},
                of(5, 2),
                Direction.right);

        FollowTraceResult ftr = gs.followTraceStartingFrom(of(3, 3), 0);

        assertEquals(4, ftr.traceCells.size());
        assertEquals(7, ftr.neighborNonTerritoryCells.size());
    }

    @Test
    public void fillStartingFrom() throws Exception {
        GameState gs = GameState.emptyField(configDto);

        gs.withPlayer(configDto, 0,
                new Position[]{},
                new Position[]{of(1, 1)},
                of(1, 2),
                Direction.right);

        FillResult fr = gs.fillStartFrom(of(2, 1), 0);

        assertFalse(fr.enclosed);
    }

    @Test
    public void fillStartingFromEnclosed() throws Exception {
        GameState gs = GameState.emptyField(configDto);

        gs.withPlayer(configDto, 0,
                new Position[]{of(2, 2)},
                new Position[]{of(3, 2), of(4, 2), of(5, 2), of(5, 3), of(5, 4),
                        of(2, 4), of(3, 4), of(4, 4), of(5, 4),
                        of(2,3)},
                of(2, 2),
                Direction.down);

        FillResult frnon = gs.fillStartFrom(of(6, 4), 0);
        assertFalse(frnon.enclosed);

        FillResult fr = gs.fillStartFrom(of(3, 3), 0);
        assertTrue(fr.enclosed);
        assertEquals(2, fr.filledPositions.size());
    }


}