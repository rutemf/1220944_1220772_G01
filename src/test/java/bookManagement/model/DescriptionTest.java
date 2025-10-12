package bookManagement.model;

import org.junit.jupiter.api.Test;
import pt.psoft.g1.psoftg1.bookmanagement.model.Description;
import pt.psoft.g1.psoftg1.shared.model.StringUtilsCustom;

import static org.junit.jupiter.api.Assertions.*;

public class DescriptionTest {

    // Black Box Test
    @Test
    void testValidDescriptionIsAccepted() {
        Description description = new Description("A valid description.");
        assertEquals(StringUtilsCustom.sanitizeHtml("A valid description."), description.toString());
    }

    // Black Box Test
    @Test
    void testNullDescriptionSetsToNull() {
        Description description = new Description(null);
        assertNull(description.toString());
    }

    // Black Box Test
    @Test
    void testBlankDescriptionSetsToNull() {
        Description description = new Description("   ");
        assertNull(description.toString());
    }

    // Black Box Test
    @Test
    void testToStringReturnsSanitizedDescription() {
        String input = "<b>Nice</b> description.";
        Description description = new Description(input);
        assertEquals(StringUtilsCustom.sanitizeHtml(input), description.toString());
    }

    // White Box Test
    @Test
    void testTooLongDescriptionThrows() {
        String longText = "a".repeat(4097);
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> new Description(longText));
        assertEquals("Description has a maximum of 4096 characters", ex.getMessage());
    }

    // White Box Test
    @Test
    void testSetDescriptionNullPath() {
        Description description = new Description("initial");
        description.setDescription(null);
        assertNull(description.toString());
    }

    // White Box Test
    @Test
    void testSetDescriptionBlankPath() {
        Description description = new Description("initial");
        description.setDescription("   ");
        assertNull(description.toString());
    }

    // White Box Test
    @Test
    void testSetDescriptionValidPath() {
        Description description = new Description("initial");
        assertDoesNotThrow(() -> description.setDescription("Clean text"));
        assertEquals(StringUtilsCustom.sanitizeHtml("Clean text"), description.toString());
    }

    // White Box Test
    @Test
    void testSetDescriptionTooLongPath() {
        Description description = new Description("ok");
        String longDesc = "x".repeat(5000);
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> description.setDescription(longDesc));
        assertEquals("Description has a maximum of 4096 characters", ex.getMessage());
    }
}
