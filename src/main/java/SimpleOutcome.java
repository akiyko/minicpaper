

public class SimpleOutcome {
    //invalid if 100% death
    public boolean valid = true;

    //when calculation have ended
    int completeCellTick = -1;

    int crossMyTraceCellTick = -1;
    int crossBorderCellTick = -1;

    public int finishOnMyTerrCellTick = -1;
//    public int leftMyTerrCellTick = -1;

    public int territoryConsumed = -1;

    public Position lastPlayerPosition = null;
}
