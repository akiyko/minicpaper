


public class Position {
    public final int i;
    public final int j;

    Position(int i, int j) {
        this.i = i;
        this.j = j;
    }

    public static Position of(int i, int j) {
        return new Position(i, j);
    }

//    public static Position of(int i, int j) {
//        return new Position(i, j);
//    }


}
