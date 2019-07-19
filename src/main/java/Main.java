import com.google.gson.stream.JsonReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.Random;

public class Main {
    public static String getRandom(String[] array) {
        int rnd = new Random().nextInt(array.length);
        return array[rnd];
    }

    public static void main(String args[]) {
        String[] commands = {"left", "right", "up", "down"};
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String input = scanner.next();
            System.err.println(input);
//            System.err.println("==============================");
            String command = Main.getRandom(commands);
            if(input.contains("type")) {
                System.out.printf("{\"command\": \"%s\"}\n", command);
            }
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
