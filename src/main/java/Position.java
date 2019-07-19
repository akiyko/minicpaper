import java.util.Objects;
import java.util.Optional;

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

    Optional<Position> advance(Direction direction) {
        switch (direction) {
            case left:
                if (i > 0) {
                    return Optional.of(Position.of(i - 1, j));
                } else {
                    return Optional.empty();
                }
            case right:
                if (i < Converter.configParamsDto.x_cells_count - 1) {
                    return Optional.of(Position.of(i + 1, j));
                } else {
                    return Optional.empty();
                }
            case up:
                if (j < Converter.configParamsDto.y_cells_count - 1) {
                    return Optional.of(Position.of(i, j + 1));
                } else {
                    return Optional.empty();
                }
            case down:
                if (j > 0) {
                    return Optional.of(Position.of(i, j - 1));
                } else {
                    return Optional.empty();
                }
        }

        return Optional.empty();
    }

//    public static Position of(int i, int j) {
//        return new Position(i, j);
//    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return i == position.i &&
                j == position.j;
    }

    @Override
    public int hashCode() {
        return Objects.hash(i, j);
    }

    @Override
    public String toString() {
        return "{" + i + ", " + j + '}';
    }
}
