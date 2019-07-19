import org.junit.Test;

import java.io.*;

public class JsonHelperTest {
    @Test
    public void testReadConfig() {
        try(Reader r = readFile("configsample1.json")) {
            ConfigDto configDto = JsonHelper.readConfig(r);

            System.out.println(configDto);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    static Reader readFile(String file) {
        return new BufferedReader(new InputStreamReader(ClassLoader.getSystemClassLoader().getResourceAsStream(file)));
    }
}