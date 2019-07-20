package minic.strategy;

import minic.GamePlan;
import minic.dto.Direction;

import java.util.*;

public class GamePlanGenerator {
//    public static List<GamePlan> allOfThree(EnumSet<Direction> directions, int everyUpTo) {
//
//
//    }

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
