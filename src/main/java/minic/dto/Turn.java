package minic.dto;

public enum  Turn {
    RIGHT, LEFT, NONE;

    public static Turn of(int i) {
        switch (i) {
            case 0: return LEFT;
            case 1: return RIGHT;
            case -1: return NONE;
        }

        throw new IllegalArgumentException(String.valueOf(i));
    }
}
