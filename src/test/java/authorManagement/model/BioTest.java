package authorManagement.model;

import org.junit.jupiter.api.Test;
import pt.psoft.g1.psoftg1.authormanagement.model.Bio;
import pt.psoft.g1.psoftg1.shared.model.StringUtilsCustom;

import static org.junit.jupiter.api.Assertions.*;

public class BioTest {

    // Black Box Test
    @Test
    void testValidBioCreation() {
        Bio bio = new Bio("Software engineer passionate about clean code.");
        assertEquals("Software engineer passionate about clean code.", bio.toString());
    }

    // Black Box Test
    @Test
    void testSetBioUpdatesValue() {
        Bio bio = new Bio("Original bio");
        bio.setBio("Updated bio");
        assertEquals("Updated bio", bio.getBio());
    }

    // Black Box Test
    @Test
    void testToStringReturnsBio() {
        Bio bio = new Bio("This is a test bio");
        assertEquals("This is a test bio", bio.toString());
    }

    // White Box Test
    @Test
    void testBioCannotBeNull() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> new Bio(null));
        assertEquals("Bio cannot be null", ex.getMessage());
    }

    // White Box Test
    @Test
    void testBioCannotBeBlank() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> new Bio("  "));
        assertEquals("Bio cannot be blank", ex.getMessage());
    }

    // White Box Test
    @Test
    void testBioTooLong() {
        String longBio = "a".repeat(4097);
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> new Bio(longBio));
        assertEquals("Bio has a maximum of 4096 characters", ex.getMessage());
    }

    // White Box Test
    @Test
    void testBioIsSanitized() {
        String htmlBio = "<b>Hello</b> world!";
        Bio bio = new Bio(htmlBio);

        String sanitized = StringUtilsCustom.sanitizeHtml(htmlBio);
        assertEquals(sanitized, bio.getBio());
    }

    // White Box Test
    @Test
    void testSetBioInvalidValueThrows() {
        Bio bio = new Bio("Valid bio");

        IllegalArgumentException ex1 = assertThrows(IllegalArgumentException.class, () -> bio.setBio(null));
        assertEquals("Bio cannot be null", ex1.getMessage());

        IllegalArgumentException ex2 = assertThrows(IllegalArgumentException.class, () -> bio.setBio(" "));
        assertEquals("Bio cannot be blank", ex2.getMessage());
    }
}