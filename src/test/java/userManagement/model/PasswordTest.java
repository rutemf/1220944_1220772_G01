package userManagement.model;

import org.junit.jupiter.api.Test;
import pt.psoft.g1.psoftg1.usermanagement.model.Password;

import static org.junit.jupiter.api.Assertions.*;

public class PasswordTest {

    // Black Box Test
    @Test
    void testValidPasswordCreation() {
        Password pwd = new Password("Abcdef1!");
        assertNotNull(pwd);
    }

    // Black Box Test
    @Test
    void testUpdatePasswordValid() {
        Password pwd = new Password("Abcdef1!");
        assertDoesNotThrow(() -> pwd.updatePassword("Xyz12345$"));
    }

    // White Box Test
    @Test
    void testPasswordTooShortThrows() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> new Password("Ab1!"));
        assertEquals("The password must contain at least 8 characters, 1 upper case letter, and 1 number or special character.",
                ex.getMessage());
    }

    // White Box Test
    @Test
    void testPasswordWithoutUppercaseThrows() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> new Password("abcdef1!"));
        assertEquals("The password must contain at least 8 characters, 1 upper case letter, and 1 number or special character.",
                ex.getMessage());
    }

    // White Box Test
    @Test
    void testPasswordWithoutNumberOrSpecialThrows() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> new Password("Abcdefgh"));
        assertEquals("The password must contain at least 8 characters, 1 upper case letter, and 1 number or special character.",
                ex.getMessage());
    }

    // White Box Test
    @Test
    void testNullPasswordThrows() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> new Password(null));
        assertEquals("The password must contain at least 8 characters, 1 upper case letter, and 1 number or special character.",
                ex.getMessage());
    }

    // White Box Test
    @Test
    void testUpdatePasswordInvalidPattern() {
        Password pwd = new Password("Abcdef1!");

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> pwd.updatePassword("abcdefg1"));
        assertEquals("The password must contain at least 8 characters, 1 upper case letter, and 1 number or special character.",
                ex.getMessage());
    }
}
