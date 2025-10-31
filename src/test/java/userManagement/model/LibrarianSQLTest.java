package userManagement.model;

import org.junit.jupiter.api.Test;
import pt.psoft.g1.psoftg1.usermanagement.model.Librarian;
import pt.psoft.g1.psoftg1.usermanagement.dataschema.LibrarianSQL;
import pt.psoft.g1.psoftg1.usermanagement.model.Role;

import java.lang.reflect.Constructor;

import static org.junit.jupiter.api.Assertions.*;

public class LibrarianSQLTest {

    // Black Box Test
    @Test
    void testFromDomain() {
        Librarian librarian = Librarian.newLibrarian("miguel", "Password123!", "Miguel Silva");
        LibrarianSQL sql = LibrarianSQL.fromDomain(librarian);

        assertEquals("miguel", sql.getUsername());
        assertNotNull(sql.getAuthorities());
        assertTrue(sql.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals(Role.LIBRARIAN)));
    }

    // Black Box Test
    @Test
    void testToDomain() {
        Librarian librarian = Librarian.newLibrarian("miguel", "Password123!", "Miguel Silva");
        LibrarianSQL sql = LibrarianSQL.fromDomain(librarian);

        Librarian domain = sql.toDomain();
        assertEquals("miguel", domain.getUsername());
        assertNotNull(domain.getAuthorities());
        assertTrue(domain.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals(Role.LIBRARIAN)));
        assertEquals("Miguel Silva", domain.getName().getName());
    }

    // White Box Test
    @Test
    void testProtectedConstructorWithReflection() throws Exception {
        Constructor<LibrarianSQL> constructor = LibrarianSQL.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        LibrarianSQL sql = constructor.newInstance();

        assertNull(sql.getUsername());
        assertNull(sql.getPassword());
        assertNotNull(sql.getAuthorities());
        assertTrue(sql.getAuthorities().isEmpty());
    }
}
