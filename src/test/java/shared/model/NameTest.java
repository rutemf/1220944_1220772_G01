package shared.model;

import org.junit.jupiter.api.Test;
import pt.psoft.g1.psoftg1.shared.model.Name;

import static org.junit.jupiter.api.Assertions.*;

public class NameTest {

    // Black Box Test
    @Test
    void testValidName() {
        Name name = new Name("JohnDoe123");
        assertEquals("JohnDoe123", name.toString());
    }

    // Black Box Test
    @Test
    void testToStringReturnsName() {
        Name name = new Name("Alice");
        assertEquals("Alice", name.toString());
    }

    // Black Box Test
    @Test
    void testSetNameWithValidInput() {
        Name name = new Name("ValidName");
        name.setName("AnotherName");
        assertEquals("AnotherName", name.getName());
    }

    // White Box Test
    @Test
    void testNullNameThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new Name(null));
        assertEquals("Name cannot be null", exception.getMessage());
    }

    // White Box Test
    @Test
    void testBlankNameThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new Name("   "));
        assertEquals("Name cannot be blank, nor only white spaces", exception.getMessage());
    }

    // White Box Test
    @Test
    void testNonAlphanumericNameThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new Name("John@Doe"));
        assertEquals("Name can only contain alphanumeric characters", exception.getMessage());
    }

    // White Box Test
    @Test
    void testSetNameToNullPath() {
        Name name = new Name("Initial");
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> name.setName(null));
        assertEquals("Name cannot be null", exception.getMessage());
    }

    // White Box Test
    @Test
    void testSetNameBlankPath() {
        Name name = new Name("Initial");
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> name.setName("  "));
        assertEquals("Name cannot be blank, nor only white spaces", exception.getMessage());
    }

    // White Box Test
    @Test
    void testSetNameNonAlphanumericPath() {
        Name name = new Name("Initial");
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> name.setName("Test@123"));
        assertEquals("Name can only contain alphanumeric characters", exception.getMessage());
    }
}
