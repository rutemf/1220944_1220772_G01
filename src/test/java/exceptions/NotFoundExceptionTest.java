package exceptions;

import org.junit.jupiter.api.Test;
import pt.psoft.g1.psoftg1.exceptions.NotFoundException;

import java.net.MalformedURLException;

import static org.junit.jupiter.api.Assertions.*;

class NotFoundExceptionTest {

    // Black Box Test
    @Test
    void testConstructorWithMessage() {
        NotFoundException ex = new NotFoundException("Custom message");
        assertEquals("Custom message", ex.getMessage());
    }

    // Black Box Test
    @Test
    void testConstructorWithMessageAndCause() {
        MalformedURLException cause = new MalformedURLException("Bad URL");
        NotFoundException ex = new NotFoundException("Error occurred", cause);

        assertEquals("Error occurred", ex.getMessage());
        assertEquals(cause, ex.getCause());
    }

    // Black Box Test
    @Test
    void testConstructorWithClassAndLongId() {
        NotFoundException ex = new NotFoundException(String.class, 42L);
        assertEquals("Entity String with id 42 not found", ex.getMessage());
    }

    // Black Box Test
    @Test
    void testConstructorWithClassAndStringId() {
        NotFoundException ex = new NotFoundException(Integer.class, "ABC123");
        assertEquals("Entity Integer with id ABC123 not found", ex.getMessage());
    }
}
