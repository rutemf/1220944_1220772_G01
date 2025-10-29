package external.services;

import org.junit.jupiter.api.Test;
import pt.psoft.g1.psoftg1.external.service.HistoricalEventsResponse;

import static org.junit.jupiter.api.Assertions.*;

public class HistoricalEventsResponseTest {

    // Black Box Test
    @Test
    void allArgsConstructor_setsFields_andGettersReturnValues() {
        HistoricalEventsResponse r = new HistoricalEventsResponse("Bug do milénio", "1999", "12", "31");

        assertEquals("1999", r.getYear());
        assertEquals("12", r.getMonth());
        assertEquals("31", r.getDay());
        assertEquals("Bug do milénio", r.getEvent());
    }

    // Black Box Test
    @Test
    void setters_work() {
        HistoricalEventsResponse r = new HistoricalEventsResponse("2000", "01", "01", "Novo milénio");

        r.setYear("2001");
        r.setMonth("02");
        r.setDay("03");
        r.setEvent("Outro evento");

        assertEquals("2001", r.getYear());
        assertEquals("02", r.getMonth());
        assertEquals("03", r.getDay());
        assertEquals("Outro evento", r.getEvent());
    }

    // Black Box Test
    @Test
    void equals_and_hashCode_basedOnFields() {
        HistoricalEventsResponse a = new HistoricalEventsResponse("1066", "10", "14", "Batalha de Hastings");
        HistoricalEventsResponse b = new HistoricalEventsResponse("1066", "10", "14", "Batalha de Hastings");
        HistoricalEventsResponse c = new HistoricalEventsResponse("1215", "06", "15", "Magna Carta");

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
        assertNotEquals(a, c);
        assertNotEquals(a.hashCode(), c.hashCode());
    }

    // Black Box Test
    @Test
    void toString_containsFieldValues() {
        HistoricalEventsResponse r = new HistoricalEventsResponse("1969", "07", "20", "Alunagem");
        String s = r.toString();

        assertTrue(s.contains("1969"));
        assertTrue(s.contains("07"));
        assertTrue(s.contains("20"));
        assertTrue(s.contains("Alunagem"));
    }
}
