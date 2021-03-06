package minic.dto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum Direction {
    left {
        @Override
        public Direction turn(Turn turn) {
            switch (turn) {
                case RIGHT:
                    return up;
                case LEFT:
                    return down;
                case NONE:
                    return left;
            }
            throw new IllegalStateException();
        }
    },
    right {
        @Override
        public Direction turn(Turn turn) {
            switch (turn) {
                case RIGHT:
                    return down;
                case LEFT:
                    return up;
                case NONE:
                    return right;
            }
            throw new IllegalStateException();
        }
    },
    up {
        @Override
        public Direction turn(Turn turn) {
            switch (turn){
                case RIGHT:
                    return right;
                case LEFT:
                    return left;
                case NONE:
                    return up;
            }
            throw new IllegalStateException();
        }
    },
    down {
        @Override
        public Direction turn(Turn turn) {
            switch (turn){
                case RIGHT:
                    return left;
                case LEFT:
                    return right;
                case NONE:
                    return down;
            }
            throw new IllegalStateException();
        }
    }, none {
        @Override
        public Direction turn(Turn turn) {
            throw new IllegalStateException();
        }
    };


    Direction() {
    }

    public Direction turn(Turn turn) {
        throw new UnsupportedOperationException();
    }


    public static Direction randomNotNone() {
        List<Direction> dirs = new ArrayList<>(Arrays.asList(Direction.values()));
        dirs.remove(none);
        Collections.shuffle(dirs);

        return dirs.get(0);
    }

}
