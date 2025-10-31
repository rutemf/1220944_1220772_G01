package auth.api;

import org.junit.jupiter.api.Test;
import pt.psoft.g1.psoftg1.auth.api.AuthRequest;

import static org.junit.jupiter.api.Assertions.*;

public class AuthRequestTest {

    // Black Box Test
    @Test
    void testNoArgsConstructorAndSetters() {
        AuthRequest request = new AuthRequest();
        request.setUsername("user@example.com");
        request.setPassword("securePassword");

        assertEquals("user@example.com", request.getUsername());
        assertEquals("securePassword", request.getPassword());
    }

    // Black Box Test
    @Test
    void testAllArgsConstructor() {
        AuthRequest request = new AuthRequest("user@example.com", "securePassword");

        assertEquals("user@example.com", request.getUsername());
        assertEquals("securePassword", request.getPassword());
    }
}