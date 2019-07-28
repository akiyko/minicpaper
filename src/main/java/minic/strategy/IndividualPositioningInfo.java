package minic.strategy;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class IndividualPositioningInfo {
    public final Map<Integer, DirectionTo> toOtherPlayers = new HashMap<>();

    public Map.Entry<Integer, DirectionTo> nearestEnemy() {
        return toOtherPlayers.entrySet().stream()
                .min(Comparator.comparing(Map.Entry::getValue)).orElse(null);
    }


}
