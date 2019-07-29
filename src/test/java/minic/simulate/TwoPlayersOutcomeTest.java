package minic.simulate;

import org.junit.Test;

import static org.junit.Assert.*;

public class TwoPlayersOutcomeTest {

    @Test
    public void compareTo1() {
        TwoPlayersOutcome tpo1 = new TwoPlayersOutcome();
        TwoPlayersOutcome tpo2 = new TwoPlayersOutcome();
        tpo2.firstWinsMicroTick = 132;

        assertEquals(0, tpo1.compareTo(tpo1));
        assertEquals(1, tpo1.compareTo(tpo2));
        assertEquals(-1, tpo2.compareTo(tpo1));
    }
}