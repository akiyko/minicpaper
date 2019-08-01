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
import minic.util.Log;

import java.util.*;
import java.util.stream.Collectors;

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

        Optional<DuelDecision> dd = Optional.empty();

        if(toNearest != null) {
            dd = TwoPlayerSimulator.findWinningDuelTurn(gs, 0, toNearest.getKey(),
                    Speed.defaultNormalSpeed(configDto), //TODO: use actual speed
                    Speed.defaultNormalSpeed(configDto), //TODO: use actual speed
                    fgps, sgps, configDto);

            if (dd.isPresent()) {
                Log.stderr("Duel move: " + dd.get());
                //TODO: simoultaneous cross and close false win test
                if (dd.get().alternativeFirstTurn == null) {
                    return dd.get().firstMove;//TODO: rather double check first move
                }
            }
        }

        //2. Otherwise - Random moves - score function
        //score function should take into account poisitioning
        //how to move to better position (that allows attack other player or take bonus)

        //TODO: saw - attack, defent from it
        Turn forbiddenTurnn = null;
        if(dd.isPresent()) {
            forbiddenTurnn = dd.get().forbiddenTurn().orElse(null);
        }
        previousGameState = gs;
        return bestMoveNoDuel(gs, forbiddenTurnn).firstMove;
    }

    public SimpleOutcome bestMoveNoDuel(GameState gs, Turn forbiddenFirstTurn) {

        List<GamePlan> gamePlans = GamePlanGenerator.allMovePlansOf(3, 5);
        gamePlans.addAll(GamePlanGenerator.allMovePlansOf(2, 10));
        gamePlans.addAll(GamePlanGenerator.allMovePlansOf(1, 20));

        if(forbiddenFirstTurn != null) {
            if (forbiddenFirstTurn == Turn.NONE) {
                gamePlans.removeIf(gp -> !gp.movePlan.containsKey(1));
            } else {
                gamePlans.removeIf(gp -> gp.movePlan.containsKey(1) && gp.movePlan.get(1) == forbiddenFirstTurn);
            }
        }

        List<SimpleOutcome> outcomes = gamePlans.stream()
                .map(gp -> Simulator.checkMovePath(gs, gp, 0))
                .collect(Collectors.toList());

        outcomes.sort(Comparator.comparingDouble(SimpleOutcome::ppct).reversed());//TODO: for debugging
        Log.stderr(outcomes.get(0).toString());
        return outcomes.stream().max(Comparator.comparingDouble(SimpleOutcome::ppct)).orElse(null);
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
