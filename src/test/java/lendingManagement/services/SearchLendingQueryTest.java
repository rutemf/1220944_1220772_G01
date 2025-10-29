package lendingManagement.services;

import org.junit.jupiter.api.Test;
import org.springframework.format.annotation.DateTimeFormat;
import pt.psoft.g1.psoftg1.lendingmanagement.services.SearchLendingQuery;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

public class SearchLendingQueryTest {

    // Black Box Test
    @Test
    void noArgsConstructor_andSetters_work() {
        SearchLendingQuery q = new SearchLendingQuery();
        assertNull(q.getReaderNumber());
        assertNull(q.getIsbn());
        assertNull(q.getReturned());
        assertNull(q.getStartDate());
        assertNull(q.getEndDate());

        q.setReaderNumber("2025/2");
        q.setIsbn("9789720706386");
        q.setReturned(Boolean.FALSE);
        q.setStartDate("2025-10-01");
        q.setEndDate("2025-10-31");

        assertEquals("2025/2", q.getReaderNumber());
        assertEquals("9789720706386", q.getIsbn());
        assertEquals(Boolean.FALSE, q.getReturned());
        assertEquals("2025-10-01", q.getStartDate());
        assertEquals("2025-10-31", q.getEndDate());
    }

    // Black Box Test
    @Test
    void allArgsConstructor_setsFields() {
        SearchLendingQuery q = new SearchLendingQuery(
                "2025/3",
                "9789720706386",
                Boolean.TRUE,
                "2025-10-01",
                "2025-10-15"
        );

        assertEquals("2025/3", q.getReaderNumber());
        assertEquals("9789720706386", q.getIsbn());
        assertEquals(Boolean.TRUE, q.getReturned());
        assertEquals("2025-10-01", q.getStartDate());
        assertEquals("2025-10-15", q.getEndDate());
    }

    // Black Box Test
    @Test
    void equals_and_hashCode() {
        SearchLendingQuery a = new SearchLendingQuery("R1", "ISBN1", true, "2025-01-01", "2025-01-31");
        SearchLendingQuery b = new SearchLendingQuery("R1", "ISBN1", true, "2025-01-01", "2025-01-31");
        SearchLendingQuery c = new SearchLendingQuery("R2", "ISBN1", true, "2025-01-01", "2025-01-31");

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
        assertNotEquals(a, c);
    }

    // Black Box Test
    @Test
    void toString_containsFieldValues() {
        SearchLendingQuery q = new SearchLendingQuery("R9", "ISBN9", false, "2025-05-10", "2025-05-20");
        String s = q.toString();
        assertTrue(s.contains("R9"));
        assertTrue(s.contains("ISBN9"));
        assertTrue(s.contains("2025-05-10"));
        assertTrue(s.contains("2025-05-20"));
    }

    // Black Box Test
    @Test
    void dateTimeFormat_annotations_present_withPattern() throws NoSuchFieldException {
        Field start = SearchLendingQuery.class.getDeclaredField("startDate");
        Field end = SearchLendingQuery.class.getDeclaredField("endDate");

        DateTimeFormat startAnn = start.getAnnotation(DateTimeFormat.class);
        DateTimeFormat endAnn = end.getAnnotation(DateTimeFormat.class);

        assertNotNull(startAnn, "startDate deve ter @DateTimeFormat");
        assertNotNull(endAnn, "endDate deve ter @DateTimeFormat");

        assertEquals("yyyy-MM-dd", startAnn.pattern());
        assertEquals("yyyy-MM-dd", endAnn.pattern());
    }
}
