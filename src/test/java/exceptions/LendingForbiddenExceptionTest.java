package exceptions;

import org.junit.jupiter.api.Test;
import pt.psoft.g1.psoftg1.exceptions.LendingForbiddenException;

import static org.junit.jupiter.api.Assertions.*;

public class LendingForbiddenExceptionTest {

    // Black Box Test
    @Test
    void testConstructorWithMessage() {
        LendingForbiddenException ex = new LendingForbiddenException("Lending not allowed");
        assertEquals("Lending not allowed", ex.getMessage());
        assertNull(ex.getCause());
    }

    // Black Box Test
    @Test
    void testConstructorWithMessageAndCause() {
        Throwable cause = new IllegalStateException("Some cause");
        LendingForbiddenException ex = new LendingForbiddenException("Lending failed", cause);

        assertEquals("Lending failed", ex.getMessage());
        assertEquals(cause, ex.getCause());
    }
}
