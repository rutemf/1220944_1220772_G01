package bookManagement.model;

import org.junit.jupiter.api.Test;
import pt.psoft.g1.psoftg1.bookmanagement.model.Title;

import java.lang.reflect.Constructor;

import static org.junit.jupiter.api.Assertions.*;

public class TitleTest {

    // Black Box Test
    @Test
    void testValidTitleIsAccepted() {
        Title title = new Title("Clean Code");
        assertEquals("Clean Code", title.toString());
    }

    // Black Box Test
    @Test
    void testToStringReturnsCorrectTitle() {
        Title title = new Title("Domain-Driven Design");
        assertEquals("Domain-Driven Design", title.toString());
    }

    // White Box Test
    @Test
    void testTitleCannotBeNull() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> new Title(null));
        assertEquals("Title cannot be null", ex.getMessage());
    }

    // White Box Test
    @Test
    void testTitleCannotBeBlank() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> new Title("   "));
        assertEquals("Title cannot be blank", ex.getMessage());
    }

    // White Box Test
    @Test
    void testTitleTooLongThrows() {
        String longTitle = "a".repeat(129);
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> new Title(longTitle));
        assertEquals("Title has a maximum of 128 characters", ex.getMessage());
    }

    // White Box Test
    @Test
    void testSetTitleNullPath() {
        Title title = new Title("Initial");
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> title.setTitle(null));
        assertEquals("Title cannot be null", ex.getMessage());
    }

    // White Box Test
    @Test
    void testSetTitleBlankPath() {
        Title title = new Title("Initial");
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> title.setTitle("  "));
        assertEquals("Title cannot be blank", ex.getMessage());
    }

    // White Box Test
    @Test
    void testSetTitleTooLongPath() {
        Title title = new Title("Short");
        String tooLong = "x".repeat(200);
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> title.setTitle(tooLong));
        assertEquals("Title has a maximum of 128 characters", ex.getMessage());
    }

    // White Box Test
    @Test
    void testSetTitleValidPath() {
        Title title = new Title("Initial");
        assertDoesNotThrow(() -> title.setTitle("  Refactoring  "));
        assertEquals("Refactoring", title.toString());
    }

    // White Box Test
    @Test
    void testProtectedConstructor() throws Exception {
        Constructor<Title> constructor = Title.class.getDeclaredConstructor();
        constructor.setAccessible(true);

        Title title = constructor.newInstance();

        assertNull(title.toString());
    }
}
