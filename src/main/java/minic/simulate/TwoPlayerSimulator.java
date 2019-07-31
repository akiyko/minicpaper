package minic.simulate;

import minic.*;
import minic.dto.ConfigDto;
import minic.dto.Direction;
import minic.dto.Turn;

import java.util.*;

public class TwoPlayerSimulator {
    public static Optional<DuelDecision> findWinningDuelTurn(GameState initial,
                                                             int firstPlayerNum, int secondPlayerNum,
                                                             Speed firstPlayerSpeed,
                                                             Speed secondPlayerSpeed,
                                                             List<GamePlan> firstPlayerPlans,
                                                             List<GamePlan> secondPlayerPlans,
                                                             ConfigDto configDto) {
        TwoPlayersOutcome[][] outcomes = new TwoPlayersOutcome[firstPlayerPlans.size()][];
        for (int i = 0; i < outcomes.length; i++) {
            outcomes[i] = new TwoPlayersOutcome[secondPlayerPlans.size()];
        }

        for (int i = 0; i < firstPlayerPlans.size(); i++) {
            for (int j = 0; j < secondPlayerPlans.size(); j++) {
                outcomes[i][j] = simulate(initial, firstPlayerNum, secondPlayerNum,
                        firstPlayerSpeed, secondPlayerSpeed,
                        firstPlayerPlans.get(i), secondPlayerPlans.get(j), configDto);
            }
        }

        //winning turn = there is W MovePlan for each second player MovePlan
        Map<Turn, Set<Integer>> firstPlayerTurns = GamePlan.groupByFirstMoveIndices(firstPlayerPlans);

        Turn bestWinningTurn = null;
        TwoPlayersOutcome bestWinningTurnWorstOutcome = null;
        Map<Turn, TwoPlayersOutcome> worstCases = new HashMap<>();
        for (Turn firstTurn : Turn.values()) {
            if (!firstPlayerTurns.containsKey(firstTurn)) {
                continue;
            }
            //look for second player move that don't have winning responses
            TwoPlayersOutcome worst = null;
            for (int j = 0; j < secondPlayerPlans.size(); j++) {
                TwoPlayersOutcome best = null;
                for (Integer i : firstPlayerTurns.get(firstTurn)) {
                    if (best == null) {
                        best = outcomes[i][j];
                    } else {
                        best = best.compareTo(outcomes[i][j]) < 0
                                ? best : outcomes[i][j];
                    }
                }
                if (worst == null) {
                    worst = best;
                } else {
//                    if(worst.compareTo(best) > 0 && best.compareTo(worst) > 0) {
//                        throw new IllegalStateException();
//                    }
                    worst = worst.compareTo(best) > 0
                            ? worst : best;
                }
            }
            //if worst is still winning then return it

            if (worst.firstWinsMicroTick > 0) {
                if (bestWinningTurn == null || worst.compareTo(bestWinningTurnWorstOutcome) < 0) {
                    bestWinningTurn = firstTurn;
                    bestWinningTurnWorstOutcome = worst;
                }
            }
            worstCases.put(firstTurn, worst);

        }
        if (bestWinningTurn != null && bestWinningTurnWorstOutcome != null) {
            DuelDecision dd = new DuelDecision();
            dd.firstMove = bestWinningTurn;
            dd.outcome = bestWinningTurnWorstOutcome;

            return Optional.of(dd);
        }
        //TODO: draw is loose for both
        //best not losing turn
        worstCases.entrySet().removeIf(e -> e.getValue().secondWinsMicroTick > 0);
        if(worstCases.size() == 1 || worstCases.size() == 2) {//there is a turn leading to defeat
            if(worstCases.size() == 1) {
                DuelDecision dd = new DuelDecision();
                dd.firstMove = worstCases.entrySet().iterator().next().getKey();
                dd.outcome = worstCases.entrySet().iterator().next().getValue();

                return Optional.of(dd);
            } else if(worstCases.size() == 2) {
                DuelDecision dd = new DuelDecision();
                List<Map.Entry<Turn, TwoPlayersOutcome>> twoNotLoosingTurns = new ArrayList<>(worstCases.entrySet());
                dd.firstMove = twoNotLoosingTurns.get(0).getKey();
                dd.alternativeFirstTurn = twoNotLoosingTurns.get(1).getKey();
                dd.outcome = twoNotLoosingTurns.get(0).getValue();

                return Optional.of(dd);
//                return worstCases.entrySet().stream()
//                        .min(Comparator.comparing(Map.Entry::getValue))
//                        .map(e -> {
//                    DuelDecision dd = new DuelDecision();
//                    dd.firstMove = e.getKey();
//                    dd.outcome = e.getValue();
//
//                    return dd;
//                });
            }
        }

        //TODO: not loosing move

        return Optional.empty(); //no guaranteed win
    }




    //TODO: shift - who will be first in equal situation?
    //TODO: maybe use micro tick shift for second player?
    //TODO: short moves, finish on terr close
    //TODO:
    //simplify the game assuming that both players exactly start on a cell
    //however use speed bonus properly
    //precondition: both players are alive
    public static TwoPlayersOutcome simulate(GameState initial,
                                             int firstPlayerNum,
                                             int secondPlayerNum,
                                             Speed firstPlayerSpeed,
                                             Speed secondPlayerSpeed,
                                             GamePlan firstPlayerPlan,
                                             GamePlan secondPlayerPlan,
                                             ConfigDto configDto) {


        TwoPlayersOutcome outcome = new TwoPlayersOutcome();
        int microTick = 0;
        GameState gs = initial.clone();

        SimpleOutcome firstSimpleOutcome = new SimpleOutcome();
        SimpleOutcome secondSimpleOutcome = new SimpleOutcome();

        Direction firstPlayerDirection = playerDirection(gs, firstSimpleOutcome, firstPlayerNum);
        Direction secondPlayerDirection = playerDirection(gs, secondSimpleOutcome, secondPlayerNum);

        int firstEveryMt = configDto.params.width / firstPlayerSpeed.currentSpeed;
        int secondEveryMt = configDto.params.width / secondPlayerSpeed.currentSpeed;

        int firstCellTick = 0;
        int secondCellTick = 0;

        Position firstPrevPosition = firstSimpleOutcome.lastPlayerPosition;
        Position secondPrevPosition = secondSimpleOutcome.lastPlayerPosition;
        while (!outcome.complete) {
            boolean firstMoveThisTick = false;
            boolean secondMoveThisTick = false;
            microTick++;
            if (microTick % firstEveryMt == 0) {
                firstCellTick++;
                firstMoveThisTick = true;
                //calculate first do 1 cellTick
                Turn turn = firstPlayerPlan.movePlan.get(firstCellTick);

                if (turn != null) {
                    firstPlayerDirection = firstPlayerDirection.turn(turn);
                }
                Simulator.advance1CellTick(gs, firstPlayerDirection, firstSimpleOutcome, firstCellTick, firstPlayerNum, false);

            }
            if (microTick % secondEveryMt == 0) {
                secondCellTick++;
                secondMoveThisTick = true;
                //calculate second do 1 cellTick
                Turn turn = secondPlayerPlan.movePlan.get(secondCellTick);

                if (turn != null) {
                    secondPlayerDirection = secondPlayerDirection.turn(turn);
                }
                Simulator.advance1CellTick(gs, secondPlayerDirection, secondSimpleOutcome, secondCellTick, secondPlayerNum, false);
            }
            if (!firstMoveThisTick && !secondMoveThisTick) {
                continue;
            }
            if(!firstSimpleOutcome.valid && !secondSimpleOutcome.valid) {
                outcome.valid = false;
                outcome.complete = true;
                break;
            }
            if(!firstSimpleOutcome.valid && secondSimpleOutcome.valid) {
                outcome.complete = true;
                outcome.secondWinsMicroTick = microTick;
                break;
            }
            if(firstSimpleOutcome.valid && !secondSimpleOutcome.valid) {
                outcome.complete = true;
                outcome.firstWinsMicroTick = microTick;
                break;
            }
            if(firstSimpleOutcome.completeCellTick>0 || secondSimpleOutcome.completeCellTick>0) {
                outcome.complete = true;
                break;
            }

            //TODO: handle if one of players move is not valid!!!
            Position firstPos = firstSimpleOutcome.lastPlayerPosition;
            Position secondPos = secondSimpleOutcome.lastPlayerPosition;

            //TODO: cross at the sime time other completes???
            if (gs.at(firstPos).tracePlayerNum == secondPlayerNum) {
                outcome.complete = true;
                outcome.firstCrossTraceOfSecondMicroTick = microTick;
            }
            if (gs.at(secondPos).tracePlayerNum == firstPlayerNum) {
                outcome.complete = true;
                outcome.secondCrossTraceOfFirstMicroTick = microTick;
            }

            if (firstPos.equals(secondPos) || firstPos.equals(secondPrevPosition) || secondPos.equals(firstPrevPosition)) {
                outcome.collisionMicroTick = microTick;
                outcome.complete = true;
                int firstTraceLen = 0;
                int secondTraceLen = 0;
                for (int i = 0; i < gs.cells.length; i++) {
                    for (int j = 0; j < gs.cells[i].length; j++) {
                        if (gs.cells[i][j].tracePlayerNum == firstPlayerNum) {
                            firstTraceLen++;
                        } else if (gs.cells[i][j].tracePlayerNum == secondPlayerNum) {
                            secondTraceLen++;
                        }
                    }
                }
                if (firstTraceLen < secondTraceLen) {
                    outcome.firstWinsMicroTick = microTick;
                } else if (firstTraceLen > secondTraceLen) {
                    outcome.secondWinsMicroTick = microTick;
                } else {
                    outcome.drawMicroTick = microTick;
                }
            }
            if (firstMoveThisTick) {
                firstPrevPosition = firstPos;
            }
            if (secondMoveThisTick) {
                secondPrevPosition = secondPos;
            }
            if (firstSimpleOutcome.completeCellTick > 0) {
                outcome.complete = true;
                if (!firstSimpleOutcome.valid) {
                    outcome.secondWinsMicroTick = microTick;
                }
            }
            if (secondSimpleOutcome.completeCellTick > 0) {
                outcome.complete = true;
                if (!secondSimpleOutcome.valid) {
                    outcome.firstWinsMicroTick = microTick;
                }
            }
        }
        outcome.calculateWinner();

        return outcome;
    }

    public static Direction playerDirection(GameState gs, SimpleOutcome outcome, int playerNum) {
        Position playerPos = gs.findPlayer(playerNum).orElse(null);
        if (playerPos == null) {
            throw new IllegalStateException("no player " + playerNum);
        }
        outcome.lastPlayerPosition = playerPos;
        return gs.at(playerPos).playerDirection;
    }

}
