package shared.model;

import org.junit.jupiter.api.Test;
import pt.psoft.g1.psoftg1.shared.model.ForbiddenName;

import static org.junit.jupiter.api.Assertions.*;

public class ForbiddenNameTest {

    // Black Box Test
    @Test
    void testEmptyStringInput() {
        ForbiddenName name = new ForbiddenName("");
        assertEquals("", name.getForbiddenName());
    }

    // Black Box Test
    @Test
    void testNullInput() {
        ForbiddenName name = new ForbiddenName(null);
        assertNull(name.getForbiddenName());
    }

    // Black Box Test
    @Test
    void testObjectsAreIndependent() {
        ForbiddenName name1 = new ForbiddenName("admin");
        ForbiddenName name2 = new ForbiddenName("admin");

        assertNotSame(name1, name2);
        assertEquals(name1.getForbiddenName(), name2.getForbiddenName());
    }

    // Black Box Test
    @Test
    void testObjectIndependenceAfterMutation() {
        ForbiddenName name1 = new ForbiddenName("admin");
        ForbiddenName name2 = new ForbiddenName("admin");

        name1.setForbiddenName("root");

        assertEquals("root", name1.getForbiddenName());
        assertEquals("admin", name2.getForbiddenName());
    }

    // White Box Test
    @Test
    void testConstructorInitializesField() {
        ForbiddenName name = new ForbiddenName("admin");
        assertEquals("admin", name.getForbiddenName());
    }

    // White Box Test
    @Test
    void testSetterUpdatesValue() {
        ForbiddenName name = new ForbiddenName("root");
        name.setForbiddenName("superuser");
        assertEquals("superuser", name.getForbiddenName());
    }

    // White Box Test
    @Test
    void testGetterReturnsCurrentValue() {
        ForbiddenName name = new ForbiddenName("guest");
        assertEquals("guest", name.getForbiddenName());
    }
}
