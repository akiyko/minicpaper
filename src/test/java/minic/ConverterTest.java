package minic;

import org.junit.Test;

import static org.junit.Assert.*;

public class ConverterTest {

    @Test
    public void testXtoI() throws Exception {

        for (int x = 0; x < 930; x++) {
            System.out.println(x + " : " + Converter.xToI(x));

        }

    }
}