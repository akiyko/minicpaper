package minic.simulate;

import minic.dto.Turn;

/**
 * First move and outcome
 */
public class DuelDecision {
    public Turn firstMove;
    public TwoPlayersOutcome outcome;

    @Override
    public String toString() {
        return "DuelDecision{" +
                "firstMove=" + firstMove +
                ", outcome=" + outcome +
                '}';
    }
}
