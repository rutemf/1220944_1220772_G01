package readerManagement.api;

import org.junit.jupiter.api.Test;
import pt.psoft.g1.psoftg1.readermanagement.api.ReaderQuoteView;

import static org.junit.jupiter.api.Assertions.*;

public class ReaderQuoteViewTest {

    // Black Box Test
    @Test
    void testGettersAndSetters() {
        ReaderQuoteView rq = new ReaderQuoteView();
        rq.setReaderNumber("R-10");
        rq.setFullName("Ada Lovelace");
        rq.setQuote("Imagination is the Discovering Faculty.");

        assertEquals("R-10", rq.getReaderNumber());
        assertEquals("Ada Lovelace", rq.getFullName());
        assertEquals("Imagination is the Discovering Faculty.", rq.getQuote());
    }

    // Black Box Test
    @Test
    void testEqualsAndHashCode_SameValues() {
        ReaderQuoteView a = new ReaderQuoteView();
        a.setReaderNumber("R-10");
        a.setQuote("Stay curious");

        ReaderQuoteView b = new ReaderQuoteView();
        b.setReaderNumber("R-10");
        b.setQuote("Stay curious");

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }

    // Black Box Test
    @Test
    void testNotEquals_WhenQuoteDiffers() {
        ReaderQuoteView a = new ReaderQuoteView();
        a.setReaderNumber("R-10");
        a.setQuote("Stay curious");

        ReaderQuoteView b = new ReaderQuoteView();
        b.setReaderNumber("R-10");
        b.setQuote("Different quote");

        assertNotEquals(a, b);
    }

    // Black Box Test
    @Test
    void testNotEquals_WhenSuperclassFieldDiffers() {
        ReaderQuoteView a = new ReaderQuoteView();
        a.setReaderNumber("R-10");
        a.setQuote("Stay curious");

        ReaderQuoteView b = new ReaderQuoteView();
        b.setReaderNumber("R-11");
        b.setQuote("Stay curious");

        assertNotEquals(a, b);
    }

    // Black Box Test
    @Test
    void testNotEquals_NullAndDifferentType() {
        ReaderQuoteView a = new ReaderQuoteView();
        a.setReaderNumber("R-10");
        a.setQuote("Stay curious");

        assertNotEquals(null, a);
    }
}
