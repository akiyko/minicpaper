package minic;

public class SimpleOutcome {
    //invalid if 100% death
    public boolean valid = true;

    //when calculation have ended
    public int completeCellTick = -1;

    public int crossMyTraceCellTick = -1;
    public int crossBorderCellTick = -1;

    public int finishOnMyTerrCellTick = -1;
//    public int leftMyTerrCellTick = -1;

    public int territoryConsumed = -1;

    public Position lastPlayerPosition = null;

    public int nitroBonusCellTick = -1;
    public int slowBonusCellTick = -1;
    public int sawBonusCellTick = -1;
    public int cellsTaken = -1;
    public int enemyCellsTaken = -1;
}
