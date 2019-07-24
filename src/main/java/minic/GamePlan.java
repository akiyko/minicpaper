package minic;

import minic.dto.Turn;

import java.util.*;

public class GamePlan {
    public int playerNum = 0;
    public Map<Integer, Turn> movePlan = new TreeMap<>();

    public Turn firstMove() {
        return Optional.ofNullable(movePlan.get(1)).orElse(Turn.NONE);
    }

    public static Map<Turn, Set<Integer>> groupByFirstMoveIndices(List<GamePlan> gamePlans) {
        Map<Turn, Set<Integer>> result = new HashMap<>();

        for (int i = 0; i < gamePlans.size(); i++) {
            Turn firstMove = gamePlans.get(i).firstMove();
            result.putIfAbsent(firstMove, new HashSet<>());
            result.get(firstMove).add(i);
        }

        return result;
    }
}
