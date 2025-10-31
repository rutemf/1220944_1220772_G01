package userManagement.model;

import org.junit.jupiter.api.Test;
import pt.psoft.g1.psoftg1.usermanagement.model.Reader;
import pt.psoft.g1.psoftg1.usermanagement.dataschema.ReaderSQL;
import pt.psoft.g1.psoftg1.usermanagement.model.Role;

import java.lang.reflect.Constructor;

import static org.junit.jupiter.api.Assertions.*;

public class ReaderSQLTest {

    // Black Box Test
    @Test
    void testFromDomain() {
        Reader reader = Reader.newReader("miguel", "Password123!", "Miguel Silva");
        ReaderSQL sql = ReaderSQL.fromDomain(reader);

        assertEquals("miguel", sql.getUsername());
        assertNotNull(sql.getAuthorities());
        assertTrue(sql.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals(Role.READER)));
    }

    // Black Box Test
    @Test
    void testToDomain() {
        Reader reader = Reader.newReader("miguel", "Password123!", "Miguel Silva");
        ReaderSQL sql = ReaderSQL.fromDomain(reader);

        Reader domain = sql.toDomain();
        assertEquals("miguel", domain.getUsername());
        assertNotNull(domain.getAuthorities());
        assertTrue(domain.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals(Role.READER)));
        assertEquals("Miguel Silva", domain.getName().getName());
    }

    // White Box Test
    @Test
    void testProtectedConstructorWithReflection() throws Exception {
        Constructor<ReaderSQL> constructor = ReaderSQL.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        ReaderSQL sql = constructor.newInstance();

        assertNull(sql.getUsername());
        assertNull(sql.getPassword());
        assertNotNull(sql.getAuthorities());
        assertTrue(sql.getAuthorities().isEmpty());
    }

    // White Box Test
    @Test
    void testRoleAddedOnConstruction() {
        Reader reader = Reader.newReader("ana", "Pass123!", "Ana Silva");
        ReaderSQL sql = new ReaderSQL(reader);

        assertTrue(sql.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals(Role.READER)));
    }
}
