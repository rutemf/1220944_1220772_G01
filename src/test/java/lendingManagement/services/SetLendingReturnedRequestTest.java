package lendingManagement.services;

import org.junit.jupiter.api.Test;
import pt.psoft.g1.psoftg1.lendingmanagement.services.SetLendingReturnedRequest;

import static org.junit.jupiter.api.Assertions.*;

public class SetLendingReturnedRequestTest {

    // Black Box Test
    @Test
    void testNoArgsConstructorAndSetterGetter() {
        SetLendingReturnedRequest request = new SetLendingReturnedRequest();

        request.setCommentary("Book returned in good condition");

        assertEquals("Book returned in good condition", request.getCommentary());
    }

    // Black Box Test
    @Test
    void testAllArgsConstructor() {
        SetLendingReturnedRequest request = new SetLendingReturnedRequest("Returned with slight delay");

        assertEquals("Returned with slight delay", request.getCommentary());
    }

    // Black Box Test
    @Test
    void testEqualsAndHashCode() {
        SetLendingReturnedRequest r1 = new SetLendingReturnedRequest("All good");
        SetLendingReturnedRequest r2 = new SetLendingReturnedRequest("All good");

        assertEquals(r1, r2);
        assertEquals(r1.hashCode(), r2.hashCode());
    }

    // Black Box Test
    @Test
    void testNotEqualsDifferentCommentary() {
        SetLendingReturnedRequest r1 = new SetLendingReturnedRequest("Returned late");
        SetLendingReturnedRequest r2 = new SetLendingReturnedRequest("Returned on time");

        assertNotEquals(r1, r2);
    }

    // Black Box Test
    @Test
    void testToStringContainsCommentary() {
        SetLendingReturnedRequest request = new SetLendingReturnedRequest("Slightly damaged cover");
        String str = request.toString();

        assertTrue(str.contains("Slightly damaged cover"));
        assertTrue(str.contains("commentary"));
    }

    // Black Box Test
    @Test
    void testCommentarySizeConstraintManually() {
        String longCommentary = "x".repeat(1024);
        SetLendingReturnedRequest request = new SetLendingReturnedRequest(longCommentary);

        assertEquals(1024, request.getCommentary().length());
    }
}
