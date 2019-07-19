/**
 * @author akiyko
 * @since 7/19/2019.
 */
public class Cell implements Cloneable{
    public int playernum = -1; //0 for me
    public int terrPlayerNum = -1;
    public int tracePlayerNum = -1;
    public BonusType bonus = null;
    public Direction playerDirection = null;

    public static Cell empty() {
        return new Cell();
    }

    @Override
    public Cell clone() {
        try {
            return (Cell) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new IllegalStateException(e);
        }
    }
}
