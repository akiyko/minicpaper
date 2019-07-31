package minic;

import com.google.gson.Gson;
import minic.dto.ConfigDto;
import minic.dto.TickDto;
import minic.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

public class JsonHelper {
    static Gson gson = new Gson();

    public static String readNextJson(BufferedReader r) throws IOException {
        String line;
        String json = "";
        while((line = r.readLine()) != null) {
            json += line + "\n";
            if(isValidJson(json)) {
                return json;
            }

        }
        return json;
    }

    public static boolean isValidJson(String Json) {
        try {
            Object jsonObjType = gson.fromJson(Json, Object.class).getClass();
            return !jsonObjType.equals(String.class);
        } catch (com.google.gson.JsonSyntaxException ex) {
            return false;
        }
    }

    public static ConfigDto readConfig(Reader r) {
        return gson.fromJson(r, ConfigDto.class);
    }

    public static TickDto readTick(Reader r) {
        return gson.fromJson(r, TickDto.class);
    }

    public static Object readNextObject(BufferedReader r) throws IOException {
        String json = readNextJson(r);
        Log.stderr(json);
        if(json.contains("start_game")) {
            return readConfig(new StringReader(json));
        } else if(json.contains("tick")) {
            return readTick(new StringReader(json));
        } else {
            return null;
        }
    }
}

