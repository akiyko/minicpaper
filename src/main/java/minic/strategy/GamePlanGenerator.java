package minic.strategy;

import minic.GamePlan;
import minic.dto.Direction;
import minic.dto.Turn;

import java.util.*;

public class GamePlanGenerator {
//    public static List<GamePlan> allOfThree(EnumSet<Direction> directions, int everyUpTo) {
//
//
//    }

    public static List<GamePlan> allMovePlansOf(int len, int maxFwdInclusive) {

        List<GamePlan> result = new ArrayList<>();
        MultiIntIterator turnsIterator = new MultiIntIterator(intArrayofLen(2, len));

        while (turnsIterator.hasNext()) {
            Integer[] turnsSequence = turnsIterator.next();
            MultiIntIterator fwdIterator = new MultiIntIterator(intArrayofLen(maxFwdInclusive, len));

            while (fwdIterator.hasNext()) {
                Integer[] fwdSequence = fwdIterator.next();

                Map<Integer, Turn> movePlan = new TreeMap<>();


                int position = 0;
                for (int i = 0; i < len; i++) {
                    position += fwdSequence[i] + 1; //0 menas {1 -> turn}; 0 0 0 menas 1,2,3 turns
                    movePlan.put(position, Turn.of(turnsSequence[i]));
                }

                GamePlan gp = new GamePlan();
                gp.movePlan = movePlan;

                result.add(gp);
            }
        }

        return result;
    }

    static Integer[] intArrayofLen(int val, int length) {
        Integer[] result = new Integer[length];

        for (int i = 0; i < result.length; i++) {
            result[i] = val;
        }

        return result;
    }

    //[1,2,3] [1,2,3] [1,2,3]
    public static <T> List<List<T>> allCombinations(List<List<T>> lst) {
//        LEFT RIGHT
        return null;
    }

    public static <T> List<List<T>> allPermutations(Collection<T> lst) {
        return allPermutations(Collections.<T>emptyList(), lst);
    }

    static <T> List<List<T>> allPermutations(List<T> prePermutation, Collection<T> lst) {
        if (lst.isEmpty()) {
            return Collections.emptyList();
        }
        if (lst.size() == 1) {
            List<T> res = new LinkedList<>(prePermutation);
            res.add(lst.iterator().next());

            return Collections.singletonList(res);
        }

        List<List<T>> result = new ArrayList<>();

        for (T t : lst) {
            List<T> rest = new LinkedList<>(lst);
            rest.remove(t);
            List<T> permutationContinued = new LinkedList<>(prePermutation);
            permutationContinued.add(t);

            result.addAll(allPermutations(permutationContinued, rest));
        }


        return result;
    }

}
