
import minic.*;
import minic.dto.ConfigDto;
import minic.dto.TickDto;
import minic.dto.Turn;
import minic.strategy.GameStrategyParams;
import minic.strategy.ParametrizedGameStrategy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {


    public static void main(String args[]) throws IOException {
        BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
        ConfigDto configDto = (ConfigDto) JsonHelper.readNextObject(r);
        Converter.configParamsDto = configDto.params;

        GameStrategyParams params = GameStrategyParams.fromArgs(args);
        ParametrizedGameStrategy strategy = new ParametrizedGameStrategy(params);

        while (true) {
            Object nextTick = JsonHelper.readNextObject(r);
            if(nextTick == null) {
                break;
            }
            if (nextTick instanceof TickDto) {
                GameState gs = GameState.fromTick((TickDto) nextTick, configDto);

                Turn move = strategy.nextMove(gs, configDto);

                String command = gs.playerDirection(0).get().turn(move).toString();

                System.out.printf("{\"command\": \"%s\"}\n", command);



            }
        }
    }
}
