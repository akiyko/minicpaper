
import minic.*;
import minic.dto.ConfigDto;
import minic.dto.TickDto;
import minic.dto.Turn;
import minic.strategy.GameStrategyParams;
import minic.strategy.ParametrizedGameStrategy;
import minic.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {

//
//        public static String getRandom(String[] array) {
//            int rnd = new Random().nextInt(array.length);
//            return array[rnd];
//        }
//
//
//        public static void main(String args[]) {
//            Queue<String> commands = new LinkedList<>();
//            commands.addAll(Arrays.asList("left", "down", "left", "down", "left", "down", "left", "down", "left", "up"));
//
//
//            Scanner scanner = new Scanner(System.in);
//            while (true) {
//                String input = scanner.nextLine();
//                if(input.contains("end")) {
//                    break;
//                }
//                if(!input.contains("tick")) {
//                    continue;
//                }
//
//
//                System.err.println(input);
//                if(input.contains("\"tick_num\": 2")) {
//                    continue;
//                }
//
//                String command = commands.poll();
//                System.err.println("Command:" + command);
//                System.out.printf("{\"command\": \"%s\"}\n", command);
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//
//            }
//    }


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
                if(((TickDto) nextTick).params.tick_num == 2) {
                    continue;
                }
                GameState gs = GameState.fromTick((TickDto) nextTick, configDto);

                Turn move = strategy.nextMove(gs, configDto);
                Log.stderr(move.toString());
                String command = gs.playerDirection(0).get().turn(move).toString();
                Log.stderr("Command:" + command);
                System.out.printf("{\"command\": \"%s\"}\n", command);



            }
        }
    }
}
