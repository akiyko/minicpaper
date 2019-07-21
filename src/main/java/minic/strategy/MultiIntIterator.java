package minic.strategy;

import java.util.Arrays;
import java.util.Iterator;

public class MultiIntIterator implements Iterator<Integer[]> {

    public final Integer[] bounds;
    public final Integer[] current;

    public MultiIntIterator(Integer[] bounds) {
        this.bounds = bounds;
        current = new Integer[bounds.length];
        for (int i = 0; i < bounds.length; i++) {
            current[i] = 0;
        }
        current[0] = -1;
    }

    @Override
    public Integer[] next() {
        if (current[0] < bounds[0] - 1) {
            current[0]++;
            return current;
        } else {
            current[0] = 0;
            for (int i = 1; i < bounds.length; i++) {
                if (current[i] < bounds[i] - 1) {
                    current[i]++;
                    return current;
                } else {
                    current[i] = 0;
                }
            }
        }
        throw new IllegalStateException("Broken impl:" + Arrays.toString(current));
    }

    @Override
    public boolean hasNext() {

        for (int i = 0; i < bounds.length; i++) {
            if (current[i] < bounds[i] - 1) {
                return true;
            }
        }
        return false;
    }

}
