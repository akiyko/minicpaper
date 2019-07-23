package minic.simulate;

import minic.GamePlan;
import minic.GameState;
import minic.JsonHelperTest;
import minic.Position;
import minic.dto.ConfigDto;
import minic.dto.Direction;
import minic.dto.Turn;
import org.junit.Test;

import static minic.Position.of;
import static org.junit.Assert.*;


public class TwoPlayerSimulatorTest {
    static ConfigDto configDto = JsonHelperTest.configDto;

    @Test
    public void simulateCollision() throws Exception {
        GameState gs = GameState.emptyField(configDto);

        gs.withPlayer(configDto, 0,
                new Position[]{of(3,4), of(3,5)},
                new Position[]{of(3,6), of(3,7)},
                of(3,8),
                Direction.up);
        gs.withPlayer(configDto, 1,
                new Position[]{of(7,4)},
                new Position[]{of(6,4), of(6,5), of(6,6), of(6,7)},
                of(6,8),
                Direction.up);


        GamePlan fgp = new GamePlan();
        fgp.movePlan.put(1, Turn.RIGHT);

        GamePlan sgp = new GamePlan();
        sgp.movePlan.put(1, Turn.LEFT);

        TwoPlayersOutcome tpo = TwoPlayerSimulator.simulate(gs, 0, 1,
                Speed.defaultNormalSpeed(configDto),
                Speed.defaultNormalSpeed(configDto),
                fgp, sgp, configDto);


        assertTrue(tpo.collisionMicroTick > 0);

    }

    @Test
    public void simulate() throws Exception {
        GameState gs = GameState.emptyField(configDto);

        gs.withPlayer(configDto, 0,
                new Position[]{of(3,4), of(3,5)},
                new Position[]{of(3,6), of(3,7)},
                of(3,8),
                Direction.up);
        gs.withPlayer(configDto, 1,
                new Position[]{of(7,4)},
                new Position[]{of(6,4), of(6,5), of(6,6), of(6,7)},
                of(6,8),
                Direction.up);


        GamePlan fgp = new GamePlan();
        fgp.movePlan.put(1, Turn.RIGHT);

        GamePlan sgp = new GamePlan();
        sgp.movePlan.put(1, Turn.RIGHT);

        TwoPlayersOutcome tpo = TwoPlayerSimulator.simulate(gs, 0, 1,
                Speed.defaultNormalSpeed(configDto),
                Speed.defaultNormalSpeed(configDto),
                fgp, sgp, configDto);


        assertTrue(tpo.firstCrossTraceOfSecondMicroTick > 0);
        assertTrue(tpo.secondCrossTraceOfFirstMicroTick < 0);

    }

}