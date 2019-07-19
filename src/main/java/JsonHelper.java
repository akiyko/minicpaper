import com.google.gson.Gson;

import java.io.Reader;

public class JsonHelper {
    static Gson gson = new Gson();

    public static ConfigDto readConfig(Reader r) {
        return gson.fromJson(r, ConfigDto.class);
    }

    public static TickDto readTick(Reader r) {
        return gson.fromJson(r, TickDto.class);
    }
}

