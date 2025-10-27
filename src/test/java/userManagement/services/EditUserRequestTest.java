package userManagement.services;

import org.junit.jupiter.api.Test;
import pt.psoft.g1.psoftg1.usermanagement.services.EditUserRequest;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class EditUserRequestTest {

    // Black Box Test
    @Test
    void noArgsConstructor_andSetters_work() {
        EditUserRequest r = new EditUserRequest();
        assertNull(r.getName());
        assertNull(r.getUsername());
        assertNull(r.getPassword());
        assertNull(r.getAuthorities());

        Set<String> auth = new HashSet<>(Set.of("ADMIN", "USER"));
        r.setName("Ana");
        r.setUsername("ana01");
        r.setPassword("secret123");
        r.setAuthorities(auth);

        assertEquals("Ana", r.getName());
        assertEquals("ana01", r.getUsername());
        assertEquals("secret123", r.getPassword());
        assertEquals(auth, r.getAuthorities());
    }

    // Black Box Test
    @Test
    void allArgsConstructor_setsFields() {
        Set<String> auth = new HashSet<>(Set.of("ADMIN"));
        EditUserRequest r = new EditUserRequest("Bruno", "bruno", "p@ss", auth);

        assertEquals("Bruno", r.getName());
        assertEquals("bruno", r.getUsername());
        assertEquals("p@ss", r.getPassword());
        assertEquals(auth, r.getAuthorities());
    }

    // Black Box Test
    @Test
    void equals_and_hashCode_basedOnFields() {
        Set<String> auth = new HashSet<>(Set.of("USER"));
        EditUserRequest a = new EditUserRequest("Carla", "carla", "pw", auth);
        EditUserRequest b = new EditUserRequest("Carla", "carla", "pw", new HashSet<>(Set.of("USER")));
        EditUserRequest c = new EditUserRequest("Carla", "carla2", "pw", auth);

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());

        assertNotEquals(a, c);
        assertNotEquals(a.hashCode(), c.hashCode());
    }

    // Black Box Test
    @Test
    void toString_containsFieldValues() {
        EditUserRequest r = new EditUserRequest("Diana", "diana", "pw123", new HashSet<>(Set.of("ADMIN")));
        String s = r.toString();

        assertTrue(s.contains("Diana"));
        assertTrue(s.contains("diana"));
        assertTrue(s.contains("ADMIN"));
        assertTrue(s.contains("pw123"));
    }
}
