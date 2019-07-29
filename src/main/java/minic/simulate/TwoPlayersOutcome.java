package minic.simulate;


public class TwoPlayersOutcome implements Comparable<TwoPlayersOutcome> {
    public boolean complete = false;
    public int firstCrossTraceOfSecondMicroTick = -1;
    public int secondCrossTraceOfFirstMicroTick = -1;
    public int collisionMicroTick = -1;

    public int firstWinsMicroTick = -1;
    public int secondWinsMicroTick = -1;
    //if cross each other trace at same tick without
    //better than loose, but need to avoid
    public int drawMicroTick = -1;

    public void calculateWinner() {
        if(collisionMicroTick < 0) {
            if (firstWinsMicroTick < 0) {
                if (firstCrossTraceOfSecondMicroTick > 0 && firstCrossTraceOfSecondMicroTick != secondCrossTraceOfFirstMicroTick) {
                    firstWinsMicroTick = firstCrossTraceOfSecondMicroTick;
                }
            }
            if (secondWinsMicroTick < 0) {
                if (secondCrossTraceOfFirstMicroTick > 0 && secondCrossTraceOfFirstMicroTick != firstCrossTraceOfSecondMicroTick) {
                    secondWinsMicroTick = secondCrossTraceOfFirstMicroTick;
                }
            }
        }
        if(firstWinsMicroTick > 0 && firstWinsMicroTick == secondWinsMicroTick) {
            drawMicroTick = firstWinsMicroTick;
            firstWinsMicroTick = -1;
            secondWinsMicroTick = -1;
        }

        if (collisionMicroTick < 0
                && firstCrossTraceOfSecondMicroTick > 0
                && firstCrossTraceOfSecondMicroTick == secondCrossTraceOfFirstMicroTick) {
            drawMicroTick = firstCrossTraceOfSecondMicroTick;
            firstWinsMicroTick = -1;
            secondWinsMicroTick = -1;
        }
    }


    /**
     * better move is less while compare
     */
    @Override
    public int compareTo(TwoPlayersOutcome o) {
        //both is win then return the fastest

        if (firstWinsMicroTick > 0 && o.firstWinsMicroTick > 0) {
            return Integer.compare(firstWinsMicroTick, o.firstWinsMicroTick);
        }
        if (secondWinsMicroTick > 0 && o.secondWinsMicroTick > 0) {
            return Integer.compare(o.secondWinsMicroTick, secondWinsMicroTick);
        }
        //only left is winning
        if (firstWinsMicroTick > 0 && o.firstWinsMicroTick < 0) {
            return -1;
        }
        //only right is winning
        if (firstWinsMicroTick < 0 && o.firstWinsMicroTick > 0) {
            return 1;
        }
        //right is loose, left is not
        if (secondWinsMicroTick < 0 && o.secondWinsMicroTick > 0) {
            return -1;
        }
        //right is loose, left is not
        if (secondWinsMicroTick > 0 && o.secondWinsMicroTick < 0) {
            return 1;
        }
        //left is loose, right is not
        if(secondWinsMicroTick < 0 && o.secondWinsMicroTick < 0) {
            return 0;
        }

        return 0;//no much difference
    }

    @Override
    public String toString() {
        return "TwoPlayersOutcome{" +
                "complete=" + complete +
                ", firstCrossTraceOfSecondMicroTick=" + firstCrossTraceOfSecondMicroTick +
                ", secondCrossTraceOfFirstMicroTick=" + secondCrossTraceOfFirstMicroTick +
                ", collisionMicroTick=" + collisionMicroTick +
                ", firstWinsMicroTick=" + firstWinsMicroTick +
                ", secondWinsMicroTick=" + secondWinsMicroTick +
                ", drawMicroTick=" + drawMicroTick +
                '}';
    }
}
