package userManagement.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import pt.psoft.g1.psoftg1.shared.model.Name;
import pt.psoft.g1.psoftg1.usermanagement.model.Role;
import pt.psoft.g1.psoftg1.usermanagement.model.User;
import pt.psoft.g1.psoftg1.usermanagement.model.UserSQL;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Constructor;
import java.util.Set;

public class UserSQLTest {

    private BCryptPasswordEncoder encoder;
    private User domainUser;

    @BeforeEach
    void setup() {
        encoder = new BCryptPasswordEncoder();
        Role admin = new Role(Role.ADMIN);
        domainUser = new User("john.smith@example.com", "Password123!", new Name("John Smith"), Set.of(admin));
    }

    // Black Box Test
    @Test
    void testFromDomainMapsBasicFields() {
        UserSQL sql = UserSQL.fromDomain(domainUser);

        assertNotNull(sql.getId(), "ID deve ser gerado");
        assertFalse(sql.getId().isEmpty(), "ID não deve ser vazio");

        assertEquals("john.smith@example.com", sql.getUsername());
        assertNotEquals("Password123!", sql.getPassword());
        assertTrue(sql.getPassword().startsWith("$2a$") || sql.getPassword().startsWith("$2b$"), "Password deve em BCrypt");

        assertEquals("John Smith", sql.getName());
        assertTrue(sql.isEnabled(), "Utilizador deve vir enabled");

        assertEquals(1, sql.getAuthorities().size());
        assertTrue(sql.getAuthorities().contains(new Role(Role.ADMIN)));
    }

    // Black Box Test
    @Test
    void testToDomainRoundTripKeepsSemantics() {
        UserSQL sql = UserSQL.fromDomain(domainUser);
        User back = sql.toDomain();

        assertEquals(domainUser.getUsername(), back.getUsername(), "Username deve manter-se");
        assertEquals("John Smith", back.getName().toString(), "Nome deve manter-se");
        assertEquals(
                domainUser.getAuthorities(),
                back.getAuthorities(),
                "Authorities devem manter-se no round-trip"
        );

        assertTrue(encoder.matches("Password123!", back.getPassword()), "Hash deve validar a password original");
    }

    // Black Box Test
    @Test
    void testIdGenerationIsDistinct() {
        UserSQL a = UserSQL.fromDomain(domainUser);
        UserSQL b = UserSQL.fromDomain(domainUser);
        assertNotEquals(a.getId(), b.getId(), "Duas instâncias distintas devem ter IDs distintos");
    }

    // White Box Test
    @Test
    void testJPAConstructorDefaults() throws Exception {
        Constructor<UserSQL> c = UserSQL.class.getDeclaredConstructor();
        c.setAccessible(true);
        UserSQL empty = c.newInstance();

        assertNull(empty.getId());
        assertNull(empty.getUsername());
        assertNull(empty.getPassword());
        assertNull(empty.getName());

        assertNotNull(empty.getAuthorities(), "Authorities deve ser inicializado");
        assertTrue(empty.getAuthorities().isEmpty(), "Authorities deve iniciar vazio");

        assertTrue(empty.isEnabled(), "Flag enabled tem default = true");
        assertNull(empty.getCreatedAt());
        assertNull(empty.getModifiedAt());
        assertNull(empty.getCreatedBy());
        assertNull(empty.getModifiedBy());
    }

    // White Box Test
    @Test
    void testUserDetailsStatusReflectsEnabled() {
        UserSQL sql = UserSQL.fromDomain(domainUser);

        assertTrue(sql.isAccountNonExpired());
        assertTrue(sql.isAccountNonLocked());
        assertTrue(sql.isCredentialsNonExpired());
        assertTrue(sql.isEnabled());

        sql.setEnabled(false);
        assertFalse(sql.isAccountNonExpired());
        assertFalse(sql.isAccountNonLocked());
        assertFalse(sql.isCredentialsNonExpired());
        assertFalse(sql.isEnabled());
    }

    // White Box Test
    @Test
    void testAuthoritiesAreDefensiveCopiedOnConstruction() {
        Role admin = new Role(Role.ADMIN);
        Role librarian = new Role(Role.LIBRARIAN);

        User mutableDomain = new User("alice@example.com", "Password123!", new Name("Alice"), Set.of(admin));
        UserSQL sql = UserSQL.fromDomain(mutableDomain);

        mutableDomain.addAuthority(librarian);

        assertEquals(1, sql.getAuthorities().size(), "Tamanho deve manter-se");
        assertTrue(sql.getAuthorities().contains(admin));
        assertFalse(sql.getAuthorities().contains(librarian));
    }

    // White Box Test
    @Test
    void testFromDomainFactoryIsEquivalentToConstructor() {
        UserSQL viaFactory = UserSQL.fromDomain(domainUser);
        UserSQL viaCtor = new UserSQL(domainUser);

        assertNotNull(viaFactory.getId());
        assertNotNull(viaCtor.getId());
        assertEquals(viaFactory.getUsername(), viaCtor.getUsername());
        assertEquals(viaFactory.getPassword(), viaCtor.getPassword());
        assertEquals(viaFactory.getName(), viaCtor.getName());
        assertEquals(viaFactory.getAuthorities(), viaCtor.getAuthorities());
    }

    // White Box Test
    @Test
    void testToDomainPreservesAuthoritiesExactly() {
        Role r1 = new Role(Role.ADMIN);
        Role r2 = new Role(Role.LIBRARIAN);
        User d = new User("bob@example.com", "Password123!", new Name("Bob"), Set.of(r1, r2));

        UserSQL sql = UserSQL.fromDomain(d);
        User back = sql.toDomain();

        assertEquals(Set.of(r1, r2), back.getAuthorities(), "Authorities devem manter");
    }
}
