package minic.dto;

public enum Direction {
    left {
        @Override
        public Direction turn(Turn turn) {
            switch (turn) {
                case RIGHT:
                    return up;
                case LEFT:
                    return down;
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
            }
            throw new IllegalStateException();
        }
    };


    Direction() {
    }

    public Direction turn(Turn turn) {
        throw new UnsupportedOperationException();
    }
}