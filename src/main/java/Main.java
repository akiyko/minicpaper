
import minic.dto.ConfigDto;
import minic.Converter;
import minic.JsonHelper;
import minic.dto.TickDto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

public class Main {
    public static String getRandom(String[] array) {
        int rnd = new Random().nextInt(array.length);
        return array[rnd];
    }

    public static void main(String args[]) throws IOException {
        String[] commands = {"left", "right", "up", "down"};
        BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
        ConfigDto configDto = (ConfigDto) JsonHelper.readNextObject(r);
        Converter.configParamsDto = configDto.params;
        while (true) {
            Object nextTick = JsonHelper.readNextObject(r);
            if (nextTick instanceof TickDto) {
                String command = Main.getRandom(commands);
                System.out.printf("{\"command\": \"%s\"}\n", command);

            }
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
