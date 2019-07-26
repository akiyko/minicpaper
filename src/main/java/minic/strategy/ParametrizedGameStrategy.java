package minic.strategy;


import minic.GameState;
import minic.dto.Turn;
import minic.simulate.TwoPlayerSimulator;

public class ParametrizedGameStrategy {
    public final GameStrategyParams params;

    public ParametrizedGameStrategy(GameStrategyParams params) {
        this.params = params;
    }

    public Turn nextMove(GameState gs) {


        return null;
    }

}
