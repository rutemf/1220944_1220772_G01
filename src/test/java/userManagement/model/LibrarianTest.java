package userManagement.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.psoft.g1.psoftg1.usermanagement.model.Librarian;
import pt.psoft.g1.psoftg1.usermanagement.model.Role;

import java.lang.reflect.Constructor;

import static org.junit.jupiter.api.Assertions.*;

public class LibrarianTest {

    private Librarian librarian;

    @BeforeEach
    void setup() {
        librarian = new Librarian("miguel", "Password123!");
    }

    // Black Box Test
    @Test
    void testLibrarianCreationAddsLibrarianRole() {
        assertEquals("miguel", librarian.getUsername());
        assertTrue(librarian.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals(Role.LIBRARIAN)));
    }

    // Black Box Test
    @Test
    void testNewLibrarianFactorySetsName() {
        Librarian l = Librarian.newLibrarian("ana", "Pass123!", "Ana Silva");
        assertEquals("ana", l.getUsername());
        assertEquals("Ana Silva", l.getName().getName());
        assertTrue(l.getAuthorities().stream().anyMatch(role -> role.getAuthority().equals(Role.LIBRARIAN)));
    }

    // White Box Test
    @Test
    void testProtectedConstructorWithReflection() throws Exception {
        Constructor<Librarian> constructor = Librarian.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        Librarian l = constructor.newInstance();

        assertNull(l.getUsername());
        assertNull(l.getPassword());
        assertNotNull(l.getAuthorities());
    }

    // White Box Test
    @Test
    void testSetters() {
        librarian.setUsername("newUser");
        librarian.setPassword("NewPass123!");
        assertEquals("newUser", librarian.getUsername());
        assertNotEquals("NewPass123!", librarian.getPassword(), "Password should be encoded");
    }
}
