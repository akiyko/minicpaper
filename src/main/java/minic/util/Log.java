package minic.util;


public class Log {
    public static final boolean enabled = true;

    public static void stderr(String message) {
        if (enabled) {
            System.err.println(message);
        }
    }

}
