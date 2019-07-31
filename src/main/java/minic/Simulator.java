package minic;

import minic.dto.BonusType;
import minic.dto.Direction;
import minic.dto.Turn;
import minic.simulate.FillResult;
import minic.simulate.FollowTraceResult;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class Simulator {
    public static SimpleOutcome checkMovePath(GameState initial, GamePlan gamePlan, int playerNum) {
        GameState gs = initial.clone();

        int cellTick = 0;
        SimpleOutcome outcome = new SimpleOutcome();
        outcome.firstMove = Optional.ofNullable(gamePlan.movePlan.get(1)).orElse(Turn.NONE);
        outcome.gp = gamePlan;

        Position playerPos = gs.findPlayer(playerNum).orElse(null);
        if (playerPos == null) {
            outcome.completeCellTick = 0;
            outcome.valid = false;
            return outcome;
        }
        outcome.lastPlayerPosition = playerPos;
        Direction playerDirection = gs.at(playerPos).playerDirection;
        while (outcome.completeCellTick < 0) {
            cellTick++;

            Turn turn = gamePlan.movePlan.get(cellTick);

            if (turn != null) {
                playerDirection = playerDirection.turn(turn);
            }

            advance1CellTick(gs, playerDirection, outcome, cellTick, playerNum, true);
        }
        return outcome;
    }

    public static void advance1CellTick(GameState gs, Direction direction, SimpleOutcome simpleOutcome, int cellTick, int playernum, boolean doFill) {
        Optional<Position> nextCellOpt = simpleOutcome.lastPlayerPosition.advance(direction);
        if (!nextCellOpt.isPresent()) {
            simpleOutcome.crossBorderCellTick = cellTick;
            simpleOutcome.completeCellTick = cellTick;
            simpleOutcome.valid = false;
            return; //termination operation
        } else {
            Position nextCell = nextCellOpt.get();
            if (gs.at(nextCell).tracePlayerNum == playernum) {//cross my trace
                simpleOutcome.crossMyTraceCellTick = cellTick;
                simpleOutcome.completeCellTick = cellTick;
                simpleOutcome.valid = false;
                return; //termination operation
            } else {
                gs.updateCell(simpleOutcome.lastPlayerPosition, c -> {
                    c.playernum = -1;
                    if (c.terrPlayerNum != playernum) {
                        c.tracePlayerNum = playernum;
                    }
                    c.playerDirection = null;
                });
                gs.updateCell(nextCell, c -> {
                    c.playernum = playernum;
//                    if (c.terrPlayerNum != playernum) {//MISTAKE (with the voice of lord Jaraxus)
//                        c.tracePlayerNum = playernum;
//                    }
                    c.playerDirection = direction;
                    takeBonuses(gs, gs.at(nextCell), simpleOutcome, cellTick);
                });
                if (gs.at(simpleOutcome.lastPlayerPosition).terrPlayerNum != playernum
                        && gs.at(nextCell).terrPlayerNum == playernum && simpleOutcome.finishOnMyTerrCellTick == -1) {
                    simpleOutcome.finishOnMyTerrCellTick = cellTick;
                    simpleOutcome.completeCellTick = cellTick;
                    if(doFill) {
                        Set<Position> enclosed = gs.findEnclosed(simpleOutcome.lastPlayerPosition, playernum);
                        for (Position enclosedPosition : enclosed) {
                            if(gs.at(enclosedPosition).terrPlayerNum >=0 ) {
                                simpleOutcome.enemyCellsTaken++;
                            } else {
                                simpleOutcome.cellsTaken++;
                            }
                            if(gs.at(enclosedPosition).bonus != null) {
                                if(gs.at(enclosedPosition).bonus != BonusType.saw) {
                                    simpleOutcome.takeBonus(gs.at(enclosedPosition).bonus, cellTick);
                                }
                            }
                        }

                    }
                }
                simpleOutcome.lastPlayerPosition = nextCell;
            }
        }
    }


    public static void takeBonuses(GameState gs, Cell nextCell, SimpleOutcome simpleOutcome, int cellTick) {
        if(nextCell.bonus != null) {
            simpleOutcome.takeBonus(nextCell.bonus, cellTick);
        }
    }

//    public static void fillClosedPath(GameState gs, Position nextCell, SimpleOutcome simpleOutcome, int cellTick, int playernum) {
//
//    }
//
//    public static boolean tryFillStartingFrom(GameState gs, Position nextCell, SimpleOutcome simpleOutcome, int cellTick, int playernum) {
//
//    }


}
