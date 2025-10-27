package userManagement.model;

import org.junit.jupiter.api.Test;
import pt.psoft.g1.psoftg1.usermanagement.model.Librarian;
import pt.psoft.g1.psoftg1.usermanagement.model.LibrarianNoSQL;
import pt.psoft.g1.psoftg1.usermanagement.model.LibrarianSQL;
import pt.psoft.g1.psoftg1.usermanagement.model.Role;

import java.lang.reflect.Constructor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LibrarianNoSQLTest {

    // Black Box Test
    @Test
    void testFromDomain() {
        Librarian librarian = Librarian.newLibrarian("paris", "Password123!", "Paris Hilton");
        LibrarianNoSQL noSQL = LibrarianNoSQL.fromDomain(librarian);

        assertEquals("paris", noSQL.getUsername());
        assertNotNull(noSQL.getAuthorities());
        assertTrue(noSQL.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals(Role.LIBRARIAN)));
    }

    // Black Box Test
    @Test
    void testToDomain() {
        Librarian librarian = Librarian.newLibrarian("rihanna", "Password123!", "Rihanna Fenty");
        LibrarianNoSQL noSql = LibrarianNoSQL.fromDomain(librarian);

        Librarian domain = noSql.toDomain();
        assertEquals("rihanna", domain.getUsername());
        assertNotNull(domain.getAuthorities());
        assertTrue(domain.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals(Role.LIBRARIAN)));
        assertEquals("Rihanna Fenty", domain.getName().getName());
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
