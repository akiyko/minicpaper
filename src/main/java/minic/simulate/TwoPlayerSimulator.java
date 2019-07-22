package minic.simulate;

import minic.*;
import minic.dto.ConfigDto;
import minic.dto.Direction;
import minic.dto.Turn;

public class TwoPlayerSimulator {
    //simplify the game assuming that both players exactly start on a cell
    //however use speed bonus properly
    //precondition: both players are alive
    public TwoPlayersOutcome simulate(GameState initial,
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
        while(!outcome.complete) {
            microTick ++;
            if(microTick % firstEveryMt == 0) {
                firstCellTick ++;
                //calculate first do 1 cellTick
                Turn turn = firstPlayerPlan.movePlan.get(firstCellTick);

                if (turn != null) {
                    firstPlayerDirection = firstPlayerDirection.turn(turn);
                }
                Simulator.advance1CellTick(gs, firstPlayerDirection, firstSimpleOutcome, firstCellTick, firstPlayerNum);
            }
            if(microTick % secondEveryMt == 0) {
                secondCellTick ++;
                //calculate second do 1 cellTick
                Turn turn = secondPlayerPlan.movePlan.get(secondCellTick);

                if (turn != null) {
                    secondPlayerDirection = secondPlayerDirection.turn(turn);
                }
                Simulator.advance1CellTick(gs, secondPlayerDirection, secondSimpleOutcome, secondCellTick, secondPlayerNum);
            }

            Position firstPos = firstSimpleOutcome.lastPlayerPosition;
            if(gs.at(firstPos).tracePlayerNum == secondPlayerNum) {
                outcome.complete = true;
                outcome.firstCrossTraceOfSecondMicroTick = microTick;
            }
            Position secondPos = secondSimpleOutcome.lastPlayerPosition;
            if(gs.at(secondPos).tracePlayerNum == firstPlayerNum) {
                outcome.complete = true;
                outcome.secondCrossTraceOfFirstMicroTick = microTick;
            }

            if(firstPos == secondPos) {
                outcome.collisionMicroTick = microTick;
            }
        }

        return outcome;
    }

    public Direction playerDirection(GameState gs, SimpleOutcome outcome, int playerNum) {
        Position playerPos = gs.findPlayer(playerNum).orElse(null);
        if(playerPos == null) {
            throw  new IllegalStateException("no player " + playerNum);
        }
        outcome.lastPlayerPosition = playerPos;
        return gs.at(playerPos).playerDirection;
    }

}
