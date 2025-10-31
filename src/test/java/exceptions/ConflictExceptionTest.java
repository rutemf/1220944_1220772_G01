package exceptions;

import org.junit.jupiter.api.Test;
import pt.psoft.g1.psoftg1.exceptions.ConflictException;

import java.net.MalformedURLException;

import static org.junit.jupiter.api.Assertions.*;

public class ConflictExceptionTest {

    // Black Box Test
    @Test
    void testConstructorWithMessage() {
        ConflictException ex = new ConflictException("Conflict occurred");
        assertEquals("Conflict occurred", ex.getMessage());
        assertNull(ex.getCause());
    }

    // Black Box Test
    @Test
    void testConstructorWithMessageAndCause() {
        MalformedURLException cause = new MalformedURLException("Malformed URL");
        ConflictException ex = new ConflictException("Conflict with URL", cause);

        assertEquals("Conflict with URL", ex.getMessage());
        assertEquals(cause, ex.getCause());
    }

    // Black Box Test
    @Test
    void testConstructorWithClassAndLongId() {
        ConflictException ex = new ConflictException(String.class, 123L);
        assertEquals("Entity String with id 123 not found", ex.getMessage());
        assertNull(ex.getCause());
    }

    // Black Box Test
    @Test
    void testConstructorWithClassAndStringId() {
        ConflictException ex = new ConflictException(String.class, "abc");
        assertEquals("Entity String with id abc not found", ex.getMessage());
        assertNull(ex.getCause());
    }
}
