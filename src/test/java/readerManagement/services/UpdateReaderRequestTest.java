package readerManagement.services;

import org.junit.jupiter.api.Test;
import org.springframework.web.multipart.MultipartFile;
import pt.psoft.g1.psoftg1.readermanagement.services.UpdateReaderRequest;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class UpdateReaderRequestTest {

    // Black Box Test
    @Test
    void testNoArgsConstructorDefaultsAndSettersGetters() {
        UpdateReaderRequest r = new UpdateReaderRequest();

        assertNull(r.getNumber());
        assertNull(r.getUsername());
        assertNull(r.getPassword());
        assertNull(r.getFullName());
        assertNull(r.getBirthDate());
        assertNull(r.getPhoneNumber());
        assertNull(r.getInterestList());
        assertNull(r.getPhoto());

        assertFalse(r.getMarketing());
        assertFalse(r.getThirdParty());

        r.setNumber("R-123");
        r.setUsername("user@example.com");
        r.setPassword("Secret#123");
        r.setFullName("Jane Doe");
        r.setBirthDate("1990-01-02");
        r.setPhoneNumber("+351912345678");
        r.setInterestList(Arrays.asList("Tech", "Books"));
        MultipartFile photo = mock(MultipartFile.class);
        r.setPhoto(photo);

        r.setMarketing(true);
        r.setThirdParty(true);

        assertEquals("R-123", r.getNumber());
        assertEquals("user@example.com", r.getUsername());
        assertEquals("Secret#123", r.getPassword());
        assertEquals("Jane Doe", r.getFullName());
        assertEquals("1990-01-02", r.getBirthDate());
        assertEquals("+351912345678", r.getPhoneNumber());
        assertEquals(Arrays.asList("Tech", "Books"), r.getInterestList());
        assertSame(photo, r.getPhoto());

        assertTrue(r.getMarketing());
        assertTrue(r.getThirdParty());
    }

    // Black Box Test
    @Test
    void testEqualsAndHashCode_Simple() {
        UpdateReaderRequest a = new UpdateReaderRequest();
        a.setNumber("R-123");
        a.setUsername("user@example.com");
        a.setPassword("Secret#123");
        a.setFullName("Jane Doe");
        a.setBirthDate("1990-01-02");
        a.setPhoneNumber("+351912345678");
        a.setInterestList(Arrays.asList("Tech", "Books"));
        a.setPhoto(null);
        a.setMarketing(true);
        a.setThirdParty(false);

        UpdateReaderRequest b = new UpdateReaderRequest();
        b.setNumber("R-123");
        b.setUsername("user@example.com");
        b.setPassword("Secret#123");
        b.setFullName("Jane Doe");
        b.setBirthDate("1990-01-02");
        b.setPhoneNumber("+351912345678");
        b.setInterestList(Arrays.asList("Tech", "Books"));
        b.setPhoto(null);
        b.setMarketing(true);
        b.setThirdParty(false);

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());

        UpdateReaderRequest c = new UpdateReaderRequest();
        c.setNumber("R-123");
        c.setUsername("OTHER@example.com");
        c.setPassword("Secret#123");
        c.setFullName("Jane Doe");
        c.setBirthDate("1990-01-02");
        c.setPhoneNumber("+351912345678");
        c.setInterestList(Arrays.asList("Tech", "Books"));
        c.setPhoto(null);
        c.setMarketing(true);
        c.setThirdParty(false);

        assertNotEquals(a, c);
    }

    // Black Box Test
    @Test
    void testEquals_WithNullables() {
        UpdateReaderRequest a = new UpdateReaderRequest();
        a.setNumber("R-Nulls");
        a.setUsername(null);
        a.setPassword(null);
        a.setFullName(null);
        a.setBirthDate(null);
        a.setPhoneNumber(null);
        a.setInterestList(null);
        a.setPhoto(null);
        a.setMarketing(false);
        a.setThirdParty(false);

        UpdateReaderRequest b = new UpdateReaderRequest();
        b.setNumber("R-Nulls");
        b.setUsername(null);
        b.setPassword(null);
        b.setFullName(null);
        b.setBirthDate(null);
        b.setPhoneNumber(null);
        b.setInterestList(null);
        b.setPhoto(null);
        b.setMarketing(false);
        b.setThirdParty(false);

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }
}
