package exceptions;

import org.junit.jupiter.api.Test;
import pt.psoft.g1.psoftg1.exceptions.FileStorageException;

import static org.junit.jupiter.api.Assertions.*;

public class FileStorageExceptionTest {

    // Black Box Test
    @Test
    void testConstructorWithMessage() {
        FileStorageException ex = new FileStorageException("File could not be stored");
        assertEquals("File could not be stored", ex.getMessage());
        assertNull(ex.getCause());
    }

    // Black Box Test
    @Test
    void testConstructorWithMessageAndCause() {
        Throwable cause = new IllegalArgumentException("Invalid file format");
        FileStorageException ex = new FileStorageException("Failed to store file", cause);

        assertEquals("Failed to store file", ex.getMessage());
        assertEquals(cause, ex.getCause());
    }
}
