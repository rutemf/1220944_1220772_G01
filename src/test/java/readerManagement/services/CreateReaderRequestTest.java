package readerManagement.services;

import org.junit.jupiter.api.Test;
import org.springframework.web.multipart.MultipartFile;
import pt.psoft.g1.psoftg1.readermanagement.services.CreateReaderRequest;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class CreateReaderRequestTest {

    // Black Box Test
    @Test
    void testNoArgsConstructorAndSettersGetters() {
        CreateReaderRequest r = new CreateReaderRequest();

        r.setUsername("user@example.com");
        r.setPassword("Secret#123");
        r.setFullName("Jane Doe");
        r.setBirthDate("1990-01-02");
        r.setPhoneNumber("+351912345678");

        MultipartFile photo = mock(MultipartFile.class);
        r.setPhoto(photo);

        r.setGdpr(true);
        r.setMarketing(false);
        r.setThirdParty(true);

        r.setInterestList(Arrays.asList("Tech", "Books"));

        assertEquals("user@example.com", r.getUsername());
        assertEquals("Secret#123", r.getPassword());
        assertEquals("Jane Doe", r.getFullName());
        assertEquals("1990-01-02", r.getBirthDate());
        assertEquals("+351912345678", r.getPhoneNumber());
        assertSame(photo, r.getPhoto());

        assertTrue(r.getGdpr());
        assertFalse(r.getMarketing());
        assertTrue(r.getThirdParty());

        assertEquals(Arrays.asList("Tech", "Books"), r.getInterestList());
    }

    // Black Box Test
    @Test
    void testEqualsAndHashCode_Simple() {
        CreateReaderRequest a = new CreateReaderRequest();
        a.setUsername("user@example.com");
        a.setPassword("Secret#123");
        a.setFullName("Jane Doe");
        a.setBirthDate("1990-01-02");
        a.setPhoneNumber("+351912345678");
        a.setPhoto(null);
        a.setGdpr(true);
        a.setMarketing(false);
        a.setThirdParty(true);
        a.setInterestList(Arrays.asList("Tech", "Books"));

        CreateReaderRequest b = new CreateReaderRequest();
        b.setUsername("user@example.com");
        b.setPassword("Secret#123");
        b.setFullName("Jane Doe");
        b.setBirthDate("1990-01-02");
        b.setPhoneNumber("+351912345678");
        b.setPhoto(null);
        b.setGdpr(true);
        b.setMarketing(false);
        b.setThirdParty(true);
        b.setInterestList(Arrays.asList("Tech", "Books"));

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());

        CreateReaderRequest c = new CreateReaderRequest();
        c.setUsername("other@example.com");
        c.setPassword("Secret#123");
        c.setFullName("Jane Doe");
        c.setBirthDate("1990-01-02");
        c.setPhoneNumber("+351912345678");
        c.setPhoto(null);
        c.setGdpr(true);
        c.setMarketing(false);
        c.setThirdParty(true);
        c.setInterestList(Arrays.asList("Tech", "Books"));

        assertNotEquals(a, c);
    }
}
