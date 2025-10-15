package bookManagement.model;

import org.junit.jupiter.api.Test;
import pt.psoft.g1.psoftg1.bookmanagement.model.Isbn;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class IsbnTest {

    // Black Box Test
    @Test
    void validIsbn10_constructsSuccessfully() {
        Isbn isbn = new Isbn("0306406152");
        assertEquals("0306406152", isbn.toString());
    }

    // Black Box Test
    @Test
    void validIsbn13_constructsSuccessfully() {
        Isbn isbn = new Isbn("9780306406157");
        assertEquals("9780306406157", isbn.toString());
    }

    // Black Box Test
    @Test
    void validIsbn10_withUppercaseX_checkDigit_constructsSuccessfully() {
        Isbn isbn = new Isbn("048665088X");
        assertEquals("048665088X", isbn.toString());
    }

    // Black Box Test
    @Test
    void toString_returnsRawStoredValue() {
        Isbn isbn13 = new Isbn("9780306406157");
        assertEquals("9780306406157", isbn13.toString());

        Isbn isbn10 = new Isbn("048665088X");
        assertEquals("048665088X", isbn10.toString());
    }

    // Black Box Test
    @Test
    void equalsAndHashCode_basedOnIsbnString() {
        Isbn a1 = new Isbn("9780306406157");
        Isbn a2 = new Isbn("9780306406157");
        Isbn b  = new Isbn("0306406152");

        assertEquals(a1, a2);
        assertEquals(a2, a1);

        Isbn a3 = new Isbn("9780306406157");
        assertEquals(a1, a2);
        assertEquals(a2, a3);
        assertEquals(a1, a3);

        assertNotEquals(a1, b);

        Set<Isbn> set = new HashSet<>();
        set.add(a1);
        assertTrue(set.contains(a2));
        assertFalse(set.contains(b));
    }

    // White Box Test
    @Test
    void lowercaseXInIsbn10_isRejected() {
        assertThrows(IllegalArgumentException.class, () -> new Isbn("048665088x"));
    }

    // White Box Test
    @Test
    void isbn10_invalidChecksum_throws() {
        assertThrows(IllegalArgumentException.class, () -> new Isbn("0306406153"));
    }

    // White Box Test
    @Test
    void isbn13_invalidChecksum_throws() {
        assertThrows(IllegalArgumentException.class, () -> new Isbn("9780306406158"));
    }

    // White Box Test
    @Test
    void nonDigitCharacters_areRejected() {
        assertThrows(IllegalArgumentException.class, () -> new Isbn("978-0306406157"));
        assertThrows(IllegalArgumentException.class, () -> new Isbn("978 0306406157"));
    }

    // White Box Test
    @Test
    void nullIsbn_throwsWithClearMessage() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> new Isbn(null));
        assertEquals("Isbn cannot be null", ex.getMessage());
    }

    // White Box Test
    @Test
    void lengthNeither10Nor13_isRejected() {
        assertThrows(IllegalArgumentException.class, () -> new Isbn("12345678901"));
        assertThrows(IllegalArgumentException.class, () -> new Isbn("123456789012"));
        assertThrows(IllegalArgumentException.class, () -> new Isbn("12345678901234"));
    }

    // White Box Test
    @Test
    void isbn13_checksumBranchWhereComputedTenBecomesZero_isHandled() {
        Isbn isbn = new Isbn("9780000000040");
        assertEquals("9780000000040", isbn.toString());
    }

    // White Box Test
    @Test
    void isbn10_onlyAllowsXAsLastCharacter() {
        assertThrows(IllegalArgumentException.class, () -> new Isbn("12345678X9"));
    }

    // White Box Test
    @Test
    void noArgsConstructor_existsAndIsProtected() throws Exception {
        Constructor<Isbn> ctor = Isbn.class.getDeclaredConstructor();
        int mods = ctor.getModifiers();
        assertTrue(Modifier.isProtected(mods), "O construtor no-args deve ser protected");
    }
}
