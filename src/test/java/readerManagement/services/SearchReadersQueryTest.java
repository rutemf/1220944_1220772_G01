package readerManagement.services;

import org.junit.jupiter.api.Test;
import pt.psoft.g1.psoftg1.readermanagement.services.SearchReadersQuery;

import static org.junit.jupiter.api.Assertions.*;

public class SearchReadersQueryTest {

    // Black Box Test
    @Test
    void noArgsConstructor_andSetters_work() {
        SearchReadersQuery q = new SearchReadersQuery();
        assertNull(q.getName());
        assertNull(q.getPhoneNumber());
        assertNull(q.getEmail());

        q.setName("Ana");
        q.setPhoneNumber("912345678");
        q.setEmail("ana@example.com");

        assertEquals("Ana", q.getName());
        assertEquals("912345678", q.getPhoneNumber());
        assertEquals("ana@example.com", q.getEmail());
    }

    // Black Box Test
    @Test
    void allArgsConstructor_setsFields() {
        SearchReadersQuery q = new SearchReadersQuery("Bruno", "923456789", "bruno@example.com");

        assertEquals("Bruno", q.getName());
        assertEquals("923456789", q.getPhoneNumber());
        assertEquals("bruno@example.com", q.getEmail());
    }

    // Black Box Test
    @Test
    void equals_and_hashCode() {
        SearchReadersQuery a = new SearchReadersQuery("Carla", "934567890", "carla@example.com");
        SearchReadersQuery b = new SearchReadersQuery("Carla", "934567890", "carla@example.com");
        SearchReadersQuery c = new SearchReadersQuery("Diana", "945678901", "diana@example.com");

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());

        assertNotEquals(a, c);
        assertNotEquals(a.hashCode(), c.hashCode());
    }

    // Black Box Test
    @Test
    void toString_containsFieldValues() {
        SearchReadersQuery q = new SearchReadersQuery("Eva", "956789012", "eva@example.com");
        String s = q.toString();

        assertTrue(s.contains("Eva"));
        assertTrue(s.contains("956789012"));
        assertTrue(s.contains("eva@example.com"));
    }
}
