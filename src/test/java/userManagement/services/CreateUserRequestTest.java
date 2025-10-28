package userManagement.services;

import org.junit.jupiter.api.Test;
import pt.psoft.g1.psoftg1.usermanagement.services.CreateUserRequest;

import static org.junit.jupiter.api.Assertions.*;

public class CreateUserRequestTest {

    @Test
    void testNoArgsConstructorAndSetters() {
        CreateUserRequest user = new CreateUserRequest();

        user.setUsername("user@example.com");
        user.setPassword("123456");
        user.setName("John Doe");
        user.setRole("ADMIN");

        assertEquals("user@example.com", user.getUsername());
        assertEquals("123456", user.getPassword());
        assertEquals("John Doe", user.getName());
        assertEquals("ADMIN", user.getRole());
        assertNotNull(user.getAuthorities());
        assertTrue(user.getAuthorities().isEmpty());
    }

    @Test
    void testAllArgsConstructor() {
        CreateUserRequest user = new CreateUserRequest("user@example.com", "Jane Doe", "pass123");

        assertEquals("user@example.com", user.getUsername());
        assertEquals("Jane Doe", user.getName());
        assertEquals("pass123", user.getPassword());
        assertNotNull(user.getAuthorities());
        assertTrue(user.getAuthorities().isEmpty());
    }

    @Test
    void testEqualsAndHashCode() {
        CreateUserRequest u1 = new CreateUserRequest("a@b.com", "John", "pw");
        CreateUserRequest u2 = new CreateUserRequest("a@b.com", "John", "pw");

        assertEquals(u1, u2);
        assertEquals(u1.hashCode(), u2.hashCode());
    }

    @Test
    void testNotEqualsDifferentUsernames() {
        CreateUserRequest u1 = new CreateUserRequest("a@b.com", "John", "pw");
        CreateUserRequest u2 = new CreateUserRequest("c@d.com", "John", "pw");

        assertNotEquals(u1, u2);
    }

    @Test
    void testAuthoritiesModification() {
        CreateUserRequest user = new CreateUserRequest("a@b.com", "John", "pw");
        user.getAuthorities().add("ROLE_USER");

        assertTrue(user.getAuthorities().contains("ROLE_USER"));
        assertEquals(1, user.getAuthorities().size());
    }

    @Test
    void testToStringContainsImportantFields() {
        CreateUserRequest user = new CreateUserRequest("a@b.com", "John", "pw");
        user.setRole("ADMIN");

        String str = user.toString();
        assertTrue(str.contains("a@b.com"));
        assertTrue(str.contains("John"));
        assertTrue(str.contains("ADMIN"));
    }
}
