import java.util.Optional;

public class Simulator {
    public static SimpleOutcome checkMovePath(GameState initial, GamePlan gamePlan, int playerNum) {
        GameState gs = initial.clone();

        int cellTick = 0;
        SimpleOutcome outcome = new SimpleOutcome();

        Position playerPos = gs.findPlayer(playerNum).orElse(null);
        if(playerPos == null) {
            outcome.completeCellTick=0;
            outcome.valid = false;
            return outcome;
        }
        outcome.lastPlayerPosition = playerPos;
        Direction playerDirection = gs.at(playerPos).playerDirection;
        while (outcome.completeCellTick < 0) {
            cellTick++;

            Direction turnedDirection = gamePlan.movePlan.get(cellTick);
            if (turnedDirection != null) {
                if (playerDirection == turnedDirection) {
                    //useless move
                    outcome.valid = false;
                    outcome.completeCellTick = cellTick;
                    return outcome;
                } else {
                    playerDirection = turnedDirection;
                }

            }

            advance1CellTick(gs, playerDirection, outcome, cellTick, playerNum);
        }
        return outcome;
    }

    public static void advance1CellTick(GameState gs, Direction direction, SimpleOutcome simpleOutcome, int cellTick, int playernum) {
        Optional<Position> nextCellOpt = simpleOutcome.lastPlayerPosition.advance(direction);
        if (!nextCellOpt.isPresent()) {
            simpleOutcome.crossBorderCellTick = cellTick;
            simpleOutcome.completeCellTick = cellTick;
            simpleOutcome.valid = false;
            return; //termination operation
        } else {
            Position nextCell = nextCellOpt.get();
            if (gs.at(nextCell).tracePlayerNum == playernum) {
                simpleOutcome.crossMyTraceCellTick = cellTick;
                simpleOutcome.completeCellTick = cellTick;
                simpleOutcome.valid = false;
                return; //termination operation
            } else {
                gs.updateCell(simpleOutcome.lastPlayerPosition, c -> {
                    c.playernum = -1;
                    if(c.terrPlayerNum != playernum) {
                        c.tracePlayerNum = playernum;
                    }
                    c.playerDirection = null;
                });
                gs.updateCell(nextCell, c -> {
                    c.playernum = playernum;
                    if(c.terrPlayerNum != playernum) {
                        c.tracePlayerNum = playernum;
                    }
                    c.playerDirection = direction;
                });
                if(gs.at(simpleOutcome.lastPlayerPosition).terrPlayerNum != playernum
                        && gs.at(nextCell).terrPlayerNum == playernum && simpleOutcome.finishOnMyTerrCellTick == -1) {
                    simpleOutcome.finishOnMyTerrCellTick = cellTick;
                }
                simpleOutcome.lastPlayerPosition = nextCell;
            }
        }
    }
}
