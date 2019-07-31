package minic;

import minic.dto.BonusType;
import minic.dto.Turn;

public class SimpleOutcome {
    public Turn firstMove = null;
    public GamePlan gp = null;

    //invalid if 100% death
    public boolean valid = true;

    //when calculation have ended
    public int completeCellTick = -1;

    public int crossMyTraceCellTick = -1;
    public int crossBorderCellTick = -1;

    public int finishOnMyTerrCellTick = -1;
//    public int leftMyTerrCellTick = -1;


    public Position lastPlayerPosition = null;

    public int nitroBonusCellTick = -1;
    public int slowBonusCellTick = -1;
    public int sawBonusCellTick = -1;
    public int cellsTaken = 0;
    public int enemyCellsTaken = 0;

    /**
     * points per cell tick
     */
    public double ppct() {
        if (completeCellTick > 0) {
            return (enemyCellsTaken * 5 + cellsTaken) / (double) completeCellTick
                    + nitroAddition()
                    + sawAddition()
                    + slowFine();
        } else {
            return -1;
        }
    }

    public double nitroAddition() {
        return nitroBonusCellTick < 0
                ? 0
                : 1 / (2 + nitroBonusCellTick);
    }
    public double sawAddition() {
        return sawBonusCellTick < 0
                ? 0
                : 1 / (2 + sawBonusCellTick);

    }

    public double slowFine() {
        return slowBonusCellTick < 0
                ? 0
                : -1 / (2 + slowBonusCellTick);

    }


    public void takeBonus(BonusType bonus, int cellTick) {
        switch (bonus) {
            case n:
                if (nitroBonusCellTick == -1) {
                    nitroBonusCellTick = cellTick;
                }
                break;
            case s:
                if (slowBonusCellTick == -1) {
                    slowBonusCellTick = cellTick;
                }
                break;
            case saw:
                if (sawBonusCellTick == -1) {
                    sawBonusCellTick = cellTick;
                }
                break;
        }
    }

    @Override
    public String toString() {
        return "SimpleOutcome{" +
                "firstMove=" + firstMove +
                ", valid=" + valid +
                ", completeCellTick=" + completeCellTick +
                ", crossMyTraceCellTick=" + crossMyTraceCellTick +
                ", crossBorderCellTick=" + crossBorderCellTick +
                ", finishOnMyTerrCellTick=" + finishOnMyTerrCellTick +
                ", lastPlayerPosition=" + lastPlayerPosition +
                ", nitroBonusCellTick=" + nitroBonusCellTick +
                ", slowBonusCellTick=" + slowBonusCellTick +
                ", sawBonusCellTick=" + sawBonusCellTick +
                ", cellsTaken=" + cellsTaken +
                ", enemyCellsTaken=" + enemyCellsTaken +
                '}';
    }
}
