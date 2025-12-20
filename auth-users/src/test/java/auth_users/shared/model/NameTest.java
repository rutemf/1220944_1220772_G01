package auth_users.shared.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class NameTest {

    @Test
    void ensureNameMustNotBeNull() {
        assertThrows(IllegalArgumentException.class, () -> new Name(null));
    }

    @Test
    void ensureNameMustNotBeBlank() {
        assertThrows(IllegalArgumentException.class, () -> new Name(""));
    }

    @Test
    void ensureNameMustOnlyBeAlphanumeric() {
        assertThrows(IllegalArgumentException.class, () -> new Name("Ricardo!"));
    }

    @Test
    void ensureNameMustNotBeOversize() {
        assertThrows(IllegalArgumentException.class, () -> new Name("ABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHIJKLMNOPQRSTUVWXYZ" +
        "ABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHIJKLMNOPQRSTUVWXYZ"));
    }

    @Test
    void ensureNameIsSet() {
        final var name = new Name("Test Name");
        assertEquals("Test Name", name.toString());
    }

    @Test
    void ensureNameIsChanged() {
        final var name = new Name("Test Name");
        name.setName("Test Second Name");
        assertEquals("Test Second Name", name.toString());
    }
}
