package minic.util;


public class Log {
    public static final boolean enabled = false;

    public static void stderr(String message) {
        if (enabled) {
            System.err.println(message);
        }
    }

}
