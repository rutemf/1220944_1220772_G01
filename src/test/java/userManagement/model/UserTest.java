package userManagement.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import pt.psoft.g1.psoftg1.shared.model.Name;
import pt.psoft.g1.psoftg1.usermanagement.model.Role;
import pt.psoft.g1.psoftg1.usermanagement.model.User;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    private User user;

    @BeforeEach
    void setup() {
        user = new User("john_doe", "Password123!");
    }

    // Black Box Test
    @Test
    void testUsernameIsStoredCorrectly() {
        assertEquals("john_doe", user.getUsername());
    }

    // Black Box Test
    @Test
    void testPasswordIsEncoded() {
        assertNotEquals("password123", user.getPassword(), "Password should be stored in encoded form");
        assertTrue(user.getPassword().startsWith("$2a$") || user.getPassword().startsWith("$2b$"),
                "Encoded password should use BCrypt format");
    }

    // Black Box Test
    @Test
    void testEncodedPasswordMatchesOriginal() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        assertTrue(encoder.matches("Password123!", user.getPassword()), "Encoded password must match original value");
    }

    // Black Box Test
    @Test
    void testNoArgsConstructor() {
        User emptyUser = new User();
        assertNull(emptyUser.getUsername());
        assertNull(emptyUser.getPassword());
        assertNotNull(emptyUser.getAuthorities());
        assertTrue(emptyUser.getAuthorities().isEmpty());
    }

    // Black Box Test
    @Test
    void testSetUserEnabled() {
        user.setEnabled(false);
        assertFalse(user.isEnabled(), "User should be disabled after setting enabled to false");
    }

    // White Box Test
    @Test
    void testAddAuthorityAddsRole() {
        Role role = new Role(Role.LIBRARIAN);
        user.addAuthority(role);
        assertTrue(user.getAuthorities().contains(role), "Role should be added to authorities set");
    }

    // White Box Test
    @Test
    void testConstructorAddsAuthoritiesIfProvided() {
        Role role = new Role(Role.ADMIN);
        User userWithRoles = new User("maria", "Password123!", new Name("Maria Santos"), Set.of(role));
        assertTrue(userWithRoles.getAuthorities().contains(role));
    }

    // White Box Test
    @Test
    void testConstructorHandlesNullAuthorities() {
        assertDoesNotThrow(() -> new User("ana", "Password123!", new Name("Ana Costa"), null));
    }

    // White Box Test
    @Test
    void testSetPasswordReEncodesNewPassword() {
        String oldEncoded = user.getPassword();
        user.setPassword("Password231!");
        assertNotEquals(oldEncoded, user.getPassword(), "Password should be re-encoded when changed");
    }

    // White Box Test
    @Test
    void testAccountStatusMethods() {
        assertTrue(user.isAccountNonExpired());
        assertTrue(user.isAccountNonLocked());
        assertTrue(user.isCredentialsNonExpired());
        assertTrue(user.isEnabled());
    }

    // White Box Test
    @Test
    void testNameIsStoredCorrectly() {
        Name name = new Name("JohnSmith");
        User userWithName = new User("jsmith", "Password123!", name, null);

        assertEquals("JohnSmith", userWithName.getName().getName());
        assertEquals("JohnSmith", userWithName.getName().toString());
    }
}
