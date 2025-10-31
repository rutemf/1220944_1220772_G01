package userManagement.model;

import org.junit.jupiter.api.Test;
import pt.psoft.g1.psoftg1.usermanagement.model.Reader;
import pt.psoft.g1.psoftg1.usermanagement.dataschema.ReaderNoSQL;
import pt.psoft.g1.psoftg1.usermanagement.model.Role;

import java.lang.reflect.Constructor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ReaderNoSQLTest {

    // Black Box Test
    @Test
    void testFromDomain() {
        Reader reader = Reader.newReader("nelly", "Password123!", "Nelly Furtado");
        ReaderNoSQL noSQL = ReaderNoSQL.fromDomain(reader);

        assertEquals("nelly", noSQL.getUsername());
        assertNotNull(noSQL.getAuthorities());
        assertTrue(noSQL.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals(Role.READER)));
    }

    // Black Box Test
    @Test
    void testToDomain() {
        Reader reader = Reader.newReader("britney", "Password123!", "Britney Spears");
        ReaderNoSQL noSQL = ReaderNoSQL.fromDomain(reader);

        Reader domain = noSQL.toDomain();
        assertEquals("britney", domain.getUsername());
        assertNotNull(domain.getAuthorities());
        assertTrue(domain.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals(Role.READER)));
        assertEquals("Britney Spears", domain.getName().getName());
    }

    // White Box Test
    @Test
    void testProtectedConstructorWithReflection() throws Exception {
        Constructor<ReaderNoSQL> constructor = ReaderNoSQL.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        ReaderNoSQL noSQL = constructor.newInstance();

        assertNull(noSQL.getUsername());
        assertNull(noSQL.getPassword());
        assertNotNull(noSQL.getAuthorities());
        assertTrue(noSQL.getAuthorities().isEmpty());
    }

    // White Box Test
    @Test
    void testRoleAddedOnConstruction() {
        Reader reader = Reader.newReader("beyonce", "Pass123!", "Bey Once");
        ReaderNoSQL noSQL = new ReaderNoSQL(reader);

        assertTrue(noSQL.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals(Role.READER)));
    }

}
