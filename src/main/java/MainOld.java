
import minic.*;
import minic.dto.ConfigDto;
import minic.dto.Direction;
import minic.dto.TickDto;
import minic.strategy.GamePlanGenerator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MainOld {
    public static String getRandom(String[] array) {
        int rnd = new Random().nextInt(array.length);
        return array[rnd];
    }

    public static void main(String args[]) throws IOException {
        String[] commands = {"left", "right", "up", "down"};
        BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
        ConfigDto configDto = (ConfigDto) JsonHelper.readNextObject(r);
        Converter.configParamsDto = configDto.params;
        long start = System.currentTimeMillis();
        while (true) {
            Object nextTick = JsonHelper.readNextObject(r);
            if (nextTick instanceof TickDto) {
                GameState gs = GameState.fromTick((TickDto) nextTick, configDto);

                String command = gs.playerDirection(0).map(Direction::toString).orElse("right");
                Direction playerDirection =  gs.playerDirection(0).orElse(Direction.right);

                List<GamePlan> gamePlans = GamePlanGenerator.allMovePlansOf(3,5);
                Collections.shuffle(gamePlans);
                for (GamePlan gamePlan : gamePlans) {
                    SimpleOutcome outcome = Simulator.checkMovePath(gs, gamePlan, 0);
                    if(outcome.valid) {
                        if(gamePlan.movePlan.containsKey(1)) {
                            command = playerDirection.turn(gamePlan.movePlan.get(1)).toString();

                        }
                        break;
                    }
                }

//                String command = Main.getRandom(commands);
                System.out.printf("{\"command\": \"%s\"}\n", command);

            }
            System.err.println((System.currentTimeMillis() - start) + "ms");
//            System.err.println(input);
//            System.err.println("==============================");

        }


//        try (JsonReader reader = new JsonReader(new BufferedReader(new InputStreamReader(System.in)))) {
//            while (true) {
//                reader.beginObject();
//
//
//                while (reader.hasNext()) {
//                    reader.skipValue(); //avoid some unhandle events
//                }
//
//                reader.endObject();
//                String command = Main.getRandom(commands);
//                System.out.printf("{\"command\": \"%s\"}\n", command);
//
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

    }
}
