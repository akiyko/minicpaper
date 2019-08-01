package minic.simulate;

import minic.GamePlan;
import minic.GameState;
import minic.JsonHelperTest;
import minic.Position;
import minic.dto.ConfigDto;
import minic.dto.Direction;
import minic.dto.Turn;
import minic.strategy.GamePlanGenerator;
import minic.strategy.ParametrizedGameStrategy;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static minic.Position.of;
import static org.junit.Assert.*;


public class TwoPlayerSimulatorTest {
    static ConfigDto configDto = JsonHelperTest.configDto;

    @Test
    public void teststartgamebug1() {
        //Duel move: DuelDecision{firstMove=NONEalternativeFirstTurn=null, outcome=TwoPlayersOutcome{complete=true, firstCrossTraceOfSecondMicroTick=-1, secondCrossTraceOfFirstMicroTick=-1, collisionMicroTick=-1, firstWinsMicroTick=-1, secondWinsMicroTick=-1, drawMicroTick=-1}}
        GameState gs = JsonHelperTest.readGameState("configsample1.json", "tick-duel/startgameduelbug.json");

        List<GamePlan> fgps = GamePlanGenerator.allMovePlansOf(2, 5);
        List<GamePlan> sgps = GamePlanGenerator.allMovePlansOf(2, 5);

        Optional<DuelDecision> dd = TwoPlayerSimulator.findWinningDuelTurn(gs, 0, 3,
                Speed.defaultNormalSpeed(configDto),
                Speed.defaultNormalSpeed(configDto),
                fgps, sgps, configDto);

        System.out.println(dd.orElse(null));
        assertFalse(dd.isPresent());

    }

    @Test
    public void testFirstWin6bug1() {
        GameState gs = JsonHelperTest.readGameState("configsample1.json", "tick-duel/win6bug1.json");

        List<GamePlan> fgps = GamePlanGenerator.allMovePlansOf(2, 5);
        List<GamePlan> sgps = GamePlanGenerator.allMovePlansOf(2, 5);

        Optional<DuelDecision> dd = TwoPlayerSimulator.findWinningDuelTurn(gs, 0, 3,
                Speed.defaultNormalSpeed(configDto),
                Speed.defaultNormalSpeed(configDto),
                fgps, sgps, configDto);

        System.out.println(dd.orElse(null));
        assertFalse(dd.isPresent());
//        assertEquals(dd.get().firstMove, Turn.LEFT);
//        assertEquals(dd.get().alternativeFirstTurn, Turn.NONE);
//        assertTrue(dd.get().outcome.firstWinsMicroTick < 0);
//        assertTrue(dd.get().outcome.secondWinsMicroTick < 0);

    }


    @Test
    public void testFirstWin162bug() {
        GameState gs = JsonHelperTest.readGameState("configsample1.json", "tick-duel/win162bug.json");

        List<GamePlan> fgps = GamePlanGenerator.allMovePlansOf(2, 5);
        List<GamePlan> sgps = GamePlanGenerator.allMovePlansOf(2, 5);

        Optional<DuelDecision> dd = TwoPlayerSimulator.findWinningDuelTurn(gs, 0, 3,
                Speed.defaultNormalSpeed(configDto),
                Speed.defaultNormalSpeed(configDto),
                fgps, sgps, configDto);

        assertFalse(dd.isPresent());

    }


    @Test
    public void tesCrossMyselfBug1() {
        //Bug: Duel move: DuelDecision{firstMove=RIGHT, outcome=TwoPlayersOutcome{complete=true, firstCrossTraceOfSecondMicroTick=-1, secondCrossTraceOfFirstMicroTick=-1, collisionMicroTick=-1, firstWinsMicroTick=-1, secondWinsMicroTick=-1, drawMicroTick=-1}}
        GameState gs = JsonHelperTest.readGameState("configsample1.json", "tick-duel/crossmyself1bug.json");

        List<GamePlan> fgps = GamePlanGenerator.allMovePlansOf(2, 5);
        List<GamePlan> sgps = GamePlanGenerator.allMovePlansOf(2, 5);

        Optional<DuelDecision> dd = TwoPlayerSimulator.findWinningDuelTurn(gs, 0, 3,
                Speed.defaultNormalSpeed(configDto),
                Speed.defaultNormalSpeed(configDto),
                fgps, sgps, configDto);

        assertNotSame(dd.get().firstMove, Turn.RIGHT);

    }

    @Test
    public void simulateFindBestMoveOpponentLeft() throws Exception {
        GameState gs = GameState.emptyField(configDto);

        gs.withPlayer(configDto, 0,
                new Position[]{of(2,13), of(3,13), of(4,13)},
                new Position[]{of(2,12), of(2,11)},
                of(2,10),
                Direction.down);
        gs.withPlayer(configDto, 1,
                new Position[]{of(4,2), of(4,3)},
                new Position[]{of(4,7), of(4,6), of(4,5), of(4,4), of(4,3)},
                of(4,8),
                Direction.up);
//        List<GamePlan> fgps = GamePlanGenerator.allMovePlansOf(2, 5);
        GamePlan down = new GamePlan();
        down.movePlan.put(3, Turn.LEFT);
        List<GamePlan> fgps = Collections.singletonList(down);
        GamePlan left = new GamePlan();
        left.movePlan.put(1, Turn.LEFT);
        List<GamePlan> sgps = Collections.singletonList(left);

        Optional<DuelDecision> dd = TwoPlayerSimulator.findWinningDuelTurn(gs, 0, 1,
                Speed.defaultNormalSpeed(configDto),
                Speed.defaultNormalSpeed(configDto),
                fgps, sgps, configDto);

        assertTrue(dd.isPresent());

        assertTrue(dd.get().firstMove != Turn.LEFT); //right or down are equals
    }

    @Test
    @Ignore//TODO: fix test!!!
    public void simulateFindNotLoosing() throws Exception {
        GameState gs = GameState.emptyField(configDto);

        gs.withPlayer(configDto, 0,
                new Position[]{of(3,4)},
                new Position[]{of(3, 5), of(3,6), of(3,7), of(3,8)},
                of(4,8),
                Direction.right);
        gs.withPlayer(configDto, 1,
                new Position[]{of(2,14)},
                new Position[]{},
                of(2,13),
                Direction.down);
        List<GamePlan> fgps = GamePlanGenerator.allMovePlansOf(2, 5);
        List<GamePlan> sgps = GamePlanGenerator.allMovePlansOf(2, 5);

        Optional<DuelDecision> dd = TwoPlayerSimulator.findWinningDuelTurn(gs, 0, 1,
                Speed.defaultNormalSpeed(configDto),
                Speed.defaultNormalSpeed(configDto),
                fgps, sgps, configDto);

        System.out.println(dd.orElse(null));
        assertTrue(dd.isPresent());

        assertTrue(dd.get().firstMove == Turn.RIGHT);
        assertTrue(dd.get().outcome.secondWinsMicroTick < 0);
    }


    @Test
    public void testPerf() throws Exception {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {

            simulateFindBestMove();
        }
        System.out.println(System.currentTimeMillis() - start);

    }

    @Test
    public void simulateFindBestMove() throws Exception {
        GameState gs = GameState.emptyField(configDto);

        gs.withPlayer(configDto, 0,
                new Position[]{of(2,13), of(3,13), of(4,13)},
                new Position[]{of(2,12), of(2,11)},
                of(2,10),
                Direction.down);
        gs.withPlayer(configDto, 1,
                new Position[]{of(4,2), of(4,3)},
                new Position[]{of(4,7), of(4,6), of(4,5), of(4,4), of(4,3)},
                of(4,8),
                Direction.up);
        List<GamePlan> fgps = GamePlanGenerator.allMovePlansOf(2, 5);
        List<GamePlan> sgps = GamePlanGenerator.allMovePlansOf(2, 5);

        Optional<DuelDecision> dd = TwoPlayerSimulator.findWinningDuelTurn(gs, 0, 1,
                Speed.defaultNormalSpeed(configDto),
                Speed.defaultNormalSpeed(configDto),
                fgps, sgps, configDto);

        assertTrue(dd.isPresent());

        assertTrue(dd.get().firstMove != Turn.RIGHT); //right or down are equals
    }

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
        assertEquals(12, tpo.firstWinsMicroTick);

    }

    @Test
    public void simulateCollision2() throws Exception {
        GameState gs = GameState.emptyField(configDto);

        gs.withPlayer(configDto, 0,
                new Position[]{of(3,4), of(3,5)},
                new Position[]{of(3,6), of(3,7)},
                of(3,8),
                Direction.up);
        gs.withPlayer(configDto, 1,
                new Position[]{of(8,4)},
                new Position[]{of(7,4), of(7,5), of(7,6), of(7,7)},
                of(7,8),
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
        assertEquals(12, tpo.firstWinsMicroTick);

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


        assertEquals((30 / 5) *3, tpo.firstCrossTraceOfSecondMicroTick);
        assertTrue(tpo.secondCrossTraceOfFirstMicroTick < 0);

    }

}