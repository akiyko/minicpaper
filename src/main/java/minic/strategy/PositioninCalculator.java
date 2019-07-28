package minic.strategy;

import minic.GameState;
import minic.Position;

import java.util.Map;

public class PositioninCalculator {
    public static PositioningInfo calculatePositioning(GameState gs) {
        //so far only for 0 (me)
        PositioningInfo positioningInfo = new PositioningInfo();

        positioningInfo.positioning.put(0, forExistingPlayer(gs, 0));

        return positioningInfo;
    }

    public static IndividualPositioningInfo forExistingPlayer(GameState gs, int playerNum) {
        IndividualPositioningInfo result = new IndividualPositioningInfo();
        Position p0 = gs.playersInitialPos.get(playerNum);

        for (Map.Entry<Integer, Position> positionEntry : gs.playersInitialPos.entrySet()) {
            if(!positionEntry.getKey().equals(playerNum)) {
                result.toOtherPlayers.put(positionEntry.getKey(), PositioninCalculator.between(p0, positionEntry.getValue()));
            }
        }

        return result;
    }

    public static DirectionTo between(Position p1, Position p2) {
        DirectionTo dt = new DirectionTo();

        if(p2.i > p1.i) {
            dt.right = p2.i - p1.i;
        } else {
            dt.left = p1.i - p2.i;
        }

        if(p2.j > p1.j) {
            dt.up = p2.j - p1.j;
        } else {
            dt.down = p1.j - p2.j;
        }

        return dt;
    }

}
