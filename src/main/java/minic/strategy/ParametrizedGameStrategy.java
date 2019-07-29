package minic.strategy;


import minic.GamePlan;
import minic.GameState;
import minic.SimpleOutcome;
import minic.Simulator;
import minic.dto.ConfigDto;
import minic.dto.Turn;
import minic.simulate.DuelDecision;
import minic.simulate.Speed;
import minic.simulate.TwoPlayerSimulator;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ParametrizedGameStrategy {
    private GameState previousGameState;
    public final GameStrategyParams params;

    public ParametrizedGameStrategy(GameStrategyParams params) {
        this.params = params;
    }

    public Turn nextMove(GameState gs, ConfigDto configDto) {
        //1.check if I can win or die in duel - use this move ?
        IndividualPositioningInfo positioning = PositioninCalculator.forExistingPlayer(gs, 0);

        Map.Entry<Integer, DirectionTo> toNearest = positioning.nearestEnemy();

        List<GamePlan> fgps = GamePlanGenerator.allMovePlansOf(2, 5);
        List<GamePlan> sgps = GamePlanGenerator.allMovePlansOf(2, 5);

        Optional<DuelDecision> dd = TwoPlayerSimulator.findWinningDuelTurn(gs, 0, toNearest.getKey(),
                Speed.defaultNormalSpeed(configDto), //TODO: use actual speed
                Speed.defaultNormalSpeed(configDto), //TODO: use actual speed
                fgps, sgps, configDto);

        if (dd.isPresent()) {
            System.err.println("Duel move: " + dd.get());
            return dd.get().firstMove;//TODO: rather double check first move
        }


        //2. Otherwise - Random moves - score function
        //score function should take into account poisitioning
        //how to move to better position (that allows attack other player or take bonus)

        //TODO: saw - attack, defent from it
        previousGameState = gs;
        return randomValidMove(gs);
    }

    public Turn randomValidMove(GameState gs) {

        List<GamePlan> gamePlans = GamePlanGenerator.allMovePlansOf(3, 5);
        Collections.shuffle(gamePlans);
        for (GamePlan gamePlan : gamePlans) {
            SimpleOutcome outcome = Simulator.checkMovePath(gs, gamePlan, 0);
            if (outcome.valid) {
                return Optional.ofNullable(gamePlan.movePlan.get(1)).orElse(Turn.NONE);
            }
        }

        return Turn.NONE;
    }

}