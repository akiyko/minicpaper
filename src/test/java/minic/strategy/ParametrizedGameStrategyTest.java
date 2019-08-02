package minic.strategy;

import minic.*;
import minic.dto.Direction;
import minic.dto.Turn;
import org.junit.Test;

import java.util.List;

import static minic.JsonHelperTest.configDto;
import static minic.Position.of;


public class ParametrizedGameStrategyTest {



    @Test
    public void testStupidUp155() throws Exception {

        ParametrizedGameStrategy pgs = new ParametrizedGameStrategy(GameStrategyParams.fromArgs(new String[]{}));

        GameState gs = JsonHelperTest.readGameState("configsample1.json", "tick-duel/stupidup/155.json");

        Turn nextMove = pgs.nextMove(gs, JsonHelperTest.configDto);

        System.out.println(nextMove);


    }

    @Test
    public void testBestMove1() throws Exception {

        GameState gs = GameState.emptyField(configDto);
        ParametrizedGameStrategy pgs = new ParametrizedGameStrategy(GameStrategyParams.fromArgs(new String[]{}));

        gs.withPlayer(configDto, 0,
                new Position[]{of(3,3)},
                new Position[]{},
                of(3,3),
                Direction.right);

        SimpleOutcome so = pgs.bestMoveNoDuel(gs, null, 60);

        System.out.println(so);
    }

    @Test
    public void testMovesCount() throws Exception {
        List<GamePlan> gamePlans = GamePlanGenerator.allMovePlansOf(3, 5);
        gamePlans.addAll(GamePlanGenerator.allMovePlansOf(2, 10));
        gamePlans.addAll(GamePlanGenerator.allMovePlansOf(1, 20));

        System.out.println(gamePlans.size());

    }


}