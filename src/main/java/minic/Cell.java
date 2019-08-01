package minic;

import minic.dto.BonusType;
import minic.dto.Direction;

public class Cell implements Cloneable{
    public int playernum = -1; //0 for me
    public int terrPlayerNum = -1;
    public int tracePlayerNum = -1;
    public BonusType bonus = null;
    public Direction playerDirection = null;
    public int[] realXy = null;

    public static Cell empty() {
        return new Cell();
    }

    @Override
    public Cell clone() {
        try {
            Cell res = (Cell) super.clone();
            if(realXy != null) {
                res.realXy = new int[]{realXy[0], realXy[1]};
            }
            return res;
        } catch (CloneNotSupportedException e) {
            throw new IllegalStateException(e);
        }
    }
}
