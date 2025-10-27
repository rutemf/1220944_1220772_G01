package readerManagement.api;

import org.junit.jupiter.api.Test;
import pt.psoft.g1.psoftg1.readermanagement.api.ReaderView;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class ReaderViewTest {

    // Black Box Test
    @Test
    void testGettersAndSetters() {
        ReaderView r = new ReaderView();
        r.setReaderNumber("R-001");
        r.setEmail("user@example.com");
        r.setFullName("Jane Doe");
        r.setBirthDate("1990-01-02");
        r.setPhoneNumber("+351912345678");
        r.setPhoto("photo.jpg");
        r.setGdprConsent(true);
        r.setMarketingConsent(false);
        r.setThirdPartySharingConsent(true);
        r.setInterestList(new ArrayList<>(Arrays.asList("Tech", "Books")));

        assertEquals("R-001", r.getReaderNumber());
        assertEquals("user@example.com", r.getEmail());
        assertEquals("Jane Doe", r.getFullName());
        assertEquals("1990-01-02", r.getBirthDate());
        assertEquals("+351912345678", r.getPhoneNumber());
        assertEquals("photo.jpg", r.getPhoto());
        assertTrue(r.isGdprConsent());
        assertFalse(r.isMarketingConsent());
        assertTrue(r.isThirdPartySharingConsent());
        assertEquals(Arrays.asList("Tech", "Books"), r.getInterestList());
    }

    // Black Box Test
    @Test
    void testEqualsAndHashCode_SameValues() {
        ReaderView a = new ReaderView();
        a.setReaderNumber("R-001");
        a.setEmail("user@example.com");
        a.setFullName("Jane Doe");
        a.setBirthDate("1990-01-02");
        a.setPhoneNumber("+351912345678");
        a.setPhoto("photo.jpg");
        a.setGdprConsent(true);
        a.setMarketingConsent(false);
        a.setThirdPartySharingConsent(true);
        a.setInterestList(new ArrayList<>(Arrays.asList("Tech", "Books")));

        ReaderView b = new ReaderView();
        b.setReaderNumber("R-001");
        b.setEmail("user@example.com");
        b.setFullName("Jane Doe");
        b.setBirthDate("1990-01-02");
        b.setPhoneNumber("+351912345678");
        b.setPhoto("photo.jpg");
        b.setGdprConsent(true);
        b.setMarketingConsent(false);
        b.setThirdPartySharingConsent(true);
        b.setInterestList(new ArrayList<>(Arrays.asList("Tech", "Books")));

        ReaderView c = new ReaderView();
        c.setReaderNumber("R-001");
        c.setEmail("user@example.com");
        c.setFullName("Jane Doe");
        c.setBirthDate("1990-01-02");
        c.setPhoneNumber("+351912345678");
        c.setPhoto("photo.jpg");
        c.setGdprConsent(true);
        c.setMarketingConsent(false);
        c.setThirdPartySharingConsent(true);
        c.setInterestList(new ArrayList<>(Arrays.asList("Tech", "Books")));

        assertEquals(a, b);
        assertEquals(b, a);

        assertEquals(b, c);
        assertEquals(a, c);

        assertEquals(a.hashCode(), b.hashCode());
        assertEquals(b.hashCode(), c.hashCode());
    }

    // Black Box Test
    @Test
    void testEqualsHandlesNulls() {
        ReaderView a = new ReaderView();
        a.setReaderNumber(null);
        a.setEmail(null);
        a.setFullName(null);
        a.setBirthDate(null);
        a.setPhoneNumber(null);
        a.setPhoto(null);
        a.setInterestList(null);

        ReaderView b = new ReaderView();
        b.setReaderNumber(null);
        b.setEmail(null);
        b.setFullName(null);
        b.setBirthDate(null);
        b.setPhoneNumber(null);
        b.setPhoto(null);
        b.setInterestList(null);

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }

    // Black Box Test
    @Test
    void testNotEquals_NullAndDifferentType() {
        ReaderView a = new ReaderView();
        a.setReaderNumber("R-001");

        assertNotEquals(null, a);
    }
}
