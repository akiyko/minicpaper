package minic.simulate;

import minic.dto.Turn;

import java.util.EnumSet;
import java.util.Optional;

/**
 * First move and outcome
 */
public class DuelDecision {
    public Turn firstMove;
    public Turn alternativeFirstTurn;
    public TwoPlayersOutcome outcome;

    public Optional<Turn> forbiddenTurn() {
        EnumSet<Turn> es = EnumSet.allOf(Turn.class);
        es.remove(firstMove);
        if(alternativeFirstTurn != null) {
            es.remove(alternativeFirstTurn);
            return Optional.of(es.iterator().next());
        }

        return Optional.empty();
    }

    @Override
    public String toString() {
        return "DuelDecision{" +
                "firstMove=" + firstMove +
                "alternativeFirstTurn=" + alternativeFirstTurn +
                ", outcome=" + outcome +
                '}';
    }
}
