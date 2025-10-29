package userManagement.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.psoft.g1.psoftg1.usermanagement.model.Reader;
import pt.psoft.g1.psoftg1.usermanagement.model.Role;

import java.lang.reflect.Constructor;

import static org.junit.jupiter.api.Assertions.*;

public class ReaderTest {

    private Reader reader;

    @BeforeEach
    void setup() {
        reader = new Reader("miguel", "Password123!");
    }

    // Black Box Test
    @Test
    void testReaderCreationAddsReaderRole() {
        assertEquals("miguel", reader.getUsername());
        assertTrue(reader.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals(Role.READER)));
    }

    // Black Box Test
    @Test
    void testNewReaderFactorySetsName() {
        Reader r = Reader.newReader("ana", "Pass123!", "Ana Silva");
        assertEquals("ana", r.getUsername());
        assertEquals("Ana Silva", r.getName().getName());
        assertTrue(r.getAuthorities().stream().anyMatch(role -> role.getAuthority().equals(Role.READER)));
    }

    // White Box Test
    @Test
    void testProtectedConstructorWithReflection() throws Exception {
        Constructor<Reader> constructor = Reader.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        Reader r = constructor.newInstance();

        assertNull(r.getUsername());
        assertNull(r.getPassword());
        assertNotNull(r.getAuthorities());
    }

    // White Box Test
    @Test
    void testSetters() {
        reader.setUsername("newUser");
        reader.setPassword("NewPass123!");
        assertEquals("newUser", reader.getUsername());
        assertNotEquals("NewPass123!", reader.getPassword(), "Password should be encoded");
    }
}
