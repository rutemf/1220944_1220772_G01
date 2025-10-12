package userManagement.model;

import org.junit.jupiter.api.Test;
import pt.psoft.g1.psoftg1.usermanagement.model.Role;

import static org.junit.jupiter.api.Assertions.*;

public class RoleTest {

    // Black Box Test
    @Test
    void testGetAuthorityReturnsCorrectValue() {
        Role role = new Role("LIBRARIAN");
        assertEquals("LIBRARIAN", role.getAuthority(), "Should return the authority passed in constructor");
    }

    // Black Box Test
    @Test
    void testRoleConstantsValues() {
        assertEquals("ADMIN", Role.ADMIN);
        assertEquals("LIBRARIAN", Role.LIBRARIAN);
        assertEquals("READER", Role.READER);
    }

    // White Box Test
    @Test
    void testEqualsAndHashCode() {
        Role r1 = new Role("ADMIN");
        Role r2 = new Role("ADMIN");
        Role r3 = new Role("LIBRARIAN");

        assertEquals(r1, r2);
        assertEquals(r1.hashCode(), r2.hashCode());
        assertNotEquals(r1, r3);
    }

    // White Box Test
    @Test
    void testToStringContainsAuthorityValue() {
        Role role = new Role("READER");
        assertTrue(role.toString().contains("READER"));
    }

    // White Box Test
    @Test
    void testNullAuthorityAllowed() {
        Role role = new Role(null);
        assertNull(role.getAuthority(), "Should allow null authorities since no validation is enforced");
    }
}
