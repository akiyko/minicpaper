package minic.simulate;

import minic.dto.Turn;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * First move and outcome
 */
public class DuelDecision {
    public final DuelDecisionType type;

    public Turn firstMove;
    public final Map<Turn, TwoPlayersOutcome> secondPlayerBestOptions = new HashMap<>();
    public TwoPlayersOutcome outcome;

    public DuelDecision(DuelDecisionType type) {
        this.type = type;
    }

    public Optional<Turn> forbiddenTurn() {
        EnumSet<Turn> es = EnumSet.allOf(Turn.class);
        es.remove(firstMove);
//        if(alternativeFirstTurn != null) {
//            es.remove(alternativeFirstTurn);
//            return Optional.of(es.iterator().next());
//        }

        return Optional.empty();
    }

    @Override
    public String toString() {
        return "DuelDecision{" +
                "type=" + type +
                ", firstMove=" + firstMove +
                ", secondPlayerBestOptions=" + secondPlayerBestOptions +
                ", outcome=" + outcome +
                '}';
    }
}
