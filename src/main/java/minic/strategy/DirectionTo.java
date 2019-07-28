package minic.strategy;

import minic.dto.Direction;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class DirectionTo implements Comparable<DirectionTo> {
    public int right = 0;
    public int left = 0;
    public int up = 0;
    public int down = 0;

    public List<Direction> directionsTo() {
        List<Direction> result = new ArrayList<>();
        if(right > 0) {
            result.add(Direction.right);
        }
        if(left > 0) {
            result.add(Direction.left);
        }

        if(up > 0) {
            result.add(Direction.up);
        }
        if(down > 0) {
            result.add(Direction.down);
        }

        return result;
    }

    public int len() {
        return right + left + up + down;
    }

   @Override
    public int compareTo(DirectionTo o) {
        return Integer.compare(len(), o.len());
    }
}
