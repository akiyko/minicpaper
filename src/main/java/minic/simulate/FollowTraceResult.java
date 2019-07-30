package minic.simulate;


import minic.Position;

import java.util.HashSet;
import java.util.Set;

public class FollowTraceResult {
    public final Set<Position> traceCells = new HashSet<>();
    public final Set<Position> neighborNonTerritoryCells = new HashSet<>();

}
