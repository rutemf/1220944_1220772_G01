package readerManagement.model;

import org.junit.jupiter.api.Test;
import pt.psoft.g1.psoftg1.readermanagement.model.PhoneNumber;
import static org.junit.jupiter.api.Assertions.*;

public class PhoneNumberTest {

    // Black Box Test
    @Test
    void testValidPhoneStartingWith9IsAccepted() {
        PhoneNumber p = new PhoneNumber("912345678");
        assertEquals("912345678", p.toString());
        assertEquals("912345678", p.getPhoneNumber());
    }

    // Black Box Test
    @Test
    void testValidPhoneStartingWith2IsAccepted() {
        PhoneNumber p = new PhoneNumber("212345678");
        assertEquals("212345678", p.toString());
        assertEquals("212345678", p.getPhoneNumber());
    }

    // Black Box Test
    @Test
    void testToStringReturnsRawNumber() {
        PhoneNumber p = new PhoneNumber("923456781");
        assertEquals("923456781", p.toString());
    }

    // Black Box Test
    @Test
    void testEqualsAndHashCodeBasedOnValue() {
        PhoneNumber a = new PhoneNumber("912345678");
        PhoneNumber b = new PhoneNumber("912345678");
        PhoneNumber c = new PhoneNumber("212345678");

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
        assertNotEquals(a, c);
    }

    // White Box Test
    @Test
    void testNonDigitCharactersCurrentlyAllowedIfLengthAndStartPass() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> new PhoneNumber("9a2345678")
        );
        assertEquals("Phone number is not valid: 9a2345678", ex.getMessage());
    }

    // White Box Test
    @Test
    void testInvalidStartDigitThrows() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> new PhoneNumber("712345678"));
        assertEquals("Phone number is not valid: 712345678", ex.getMessage());
    }

    // White Box Test
    @Test
    void testTooShortThrows() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> new PhoneNumber("91234567"));
        assertEquals("Phone number is not valid: 91234567", ex.getMessage());
    }

    // White Box Test
    @Test
    void testTooLongThrows() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> new PhoneNumber("9123456789"));
        assertEquals("Phone number is not valid: 9123456789", ex.getMessage());
    }
}
