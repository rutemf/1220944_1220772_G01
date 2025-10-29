package shared.model;

import org.junit.jupiter.api.Test;
import pt.psoft.g1.psoftg1.shared.model.StringUtilsCustom;

import static org.junit.jupiter.api.Assertions.*;

public class StringUtilsCustomTest {

    // Black Box Test
    @Test
    void testIsAlphanumericWithLettersAndNumbers() {
        assertTrue(StringUtilsCustom.isAlphanumeric("Book123"));
    }

    // Black Box Test
    @Test
    void testIsAlphanumericWithSpacesAndDashes() {
        assertTrue(StringUtilsCustom.isAlphanumeric("John Doe - Writer"));
    }

    // Black Box Test
    @Test
    void testIsAlphanumericWithAccentedCharacters() {
        assertTrue(StringUtilsCustom.isAlphanumeric("José Álvares"));
    }

    // Black Box Test
    @Test
    void testIsAlphanumericWithInvalidSymbols() {
        assertFalse(StringUtilsCustom.isAlphanumeric("Book@2025!"));
    }

    // Black Box Test
    @Test
    void testIsAlphanumericEmptyString() {
        assertTrue(StringUtilsCustom.isAlphanumeric(""));
    }

    // Black Box Test
    @Test
    void testIsAlphanumericNullStringThrowsException() {
        assertThrows(NullPointerException.class, () -> StringUtilsCustom.isAlphanumeric(null));
    }

    // Black Box Test
    @Test
    void testSanitizeHtmlRemovesUnsafeTags() {
        String input = "<script>alert('xss');</script><b>Bold</b>";
        String sanitized = StringUtilsCustom.sanitizeHtml(input);

        assertFalse(sanitized.contains("<script>"));
        assertTrue(sanitized.contains("<b>Bold</b>"));
    }

    // Black Box Test
    @Test
    void testSanitizeHtmlPlainTextUnchanged() {
        String input = "Normal text without HTML";
        String sanitized = StringUtilsCustom.sanitizeHtml(input);

        assertEquals(input, sanitized);
    }

    // White Box Test
    @Test
    void testIsAlphanumericPatternBoundaryCases() {
        assertTrue(StringUtilsCustom.isAlphanumeric("O'Connor"));
        assertTrue(StringUtilsCustom.isAlphanumeric("Jean-Luc"));
    }

    // White Box Test
    @Test
    void testSanitizeHtmlComplexNestedTags() {
        String input = "<div><b><i>Test</i></b><script>xss()</script></div>";
        String sanitized = StringUtilsCustom.sanitizeHtml(input);

        assertTrue(sanitized.contains("<b><i>Test</i></b>"));
        assertFalse(sanitized.contains("<script>"));
    }
}
