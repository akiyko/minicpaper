import com.google.gson.Gson;

import java.io.Reader;

public class JsonHelper {
    public static ConfigDto readConfig(Reader r) {
        Gson gson = new Gson();

        return gson.fromJson(r, ConfigDto.class);
    }
}

