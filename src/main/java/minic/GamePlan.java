package minic;

import minic.dto.Turn;

import java.util.Map;
import java.util.TreeMap;

public class GamePlan {
    public int playerNum = 0;
    public Map<Integer, Turn> movePlan = new TreeMap<>();


}
