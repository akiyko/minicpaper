package minic;

import minic.dto.ConfigDto;
import minic.dto.TickDto;
import org.junit.Test;

import java.io.*;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class JsonHelperTest {
    public static ConfigDto configDto;
    static {
        try(Reader r = readFile("configsample1.json")) {
            configDto = JsonHelper.readConfig(r);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static GameState readGameState(String configFile, String tickFile) {
        try(Reader r = readFile(configFile)) {
            ConfigDto configDto = JsonHelper.readConfig(r);
            try(Reader r2 = readFile(tickFile)) {
                TickDto tickDto = JsonHelper.readTick(r2);

                return GameState.fromTick(tickDto, configDto);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        throw new IllegalStateException(configFile + "/" + tickFile);
    }

    @Test
    public void testReadConfig() {
        try(Reader r = readFile("configsample1.json")) {
            ConfigDto configDto = JsonHelper.readConfig(r);

            System.out.println(configDto);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testReadTick() {
        try(Reader r = readFile("ticksample1.json")) {
            TickDto tickDto = JsonHelper.readTick(r);

            GameState gameState = GameState.fromTick(tickDto, configDto);

            System.out.println(tickDto);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testReadInput() {
        try(BufferedReader r = readFile("gametracesample1.txt")) {
            ConfigDto c = (ConfigDto) JsonHelper.readNextObject(r);
            TickDto t = (TickDto) JsonHelper.readNextObject(r);
            t = (TickDto) JsonHelper.readNextObject(r);
            t = (TickDto) JsonHelper.readNextObject(r);



        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testIsValidJson() {
        assertFalse(JsonHelper.isValidJson("sdfsdfs {}"));
        assertTrue(JsonHelper.isValidJson("{}"));
    }



    static BufferedReader readFile(String file) {
        return new BufferedReader(new InputStreamReader(ClassLoader.getSystemClassLoader().getResourceAsStream(file)));
    }
}