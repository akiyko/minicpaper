package minic.strategy;

public class GameStrategyParams {
    public static final int DUEL_LEN_THRESHHOLD = 10;

    private GameStrategyParams() {

    }

    public static GameStrategyParams fromArgs(String[] args) {
        GameStrategyParams params = new GameStrategyParams();

        return params;
    }
}
