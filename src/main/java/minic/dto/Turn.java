package minic.dto;

public enum  Turn {
    RIGHT, LEFT;

    public static Turn of(int i) {
        switch (i) {
            case 0: return LEFT;
            case 1: return RIGHT;
        }

        throw new IllegalArgumentException(String.valueOf(i));
    }
}
