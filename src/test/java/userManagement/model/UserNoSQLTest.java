package userManagement.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import pt.psoft.g1.psoftg1.shared.model.Name;
import pt.psoft.g1.psoftg1.usermanagement.model.Role;
import pt.psoft.g1.psoftg1.usermanagement.model.User;
import pt.psoft.g1.psoftg1.usermanagement.dataschema.UserNoSQL;

import java.lang.reflect.Constructor;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserNoSQLTest {

    private BCryptPasswordEncoder encoder;
    private User domainUser;

    @BeforeEach
    void setup() {
        encoder = new BCryptPasswordEncoder();
        Role admin = new Role(Role.ADMIN);
        domainUser = new User("carrie.bradshaw@example.com", "Password123!", new Name("Carrie Bradshaw"), Set.of(admin));
    }

    // Black Box Test
    @Test
    void testFromDomainMapsBasicFields() {
        UserNoSQL noSQL = UserNoSQL.fromDomain(domainUser);

        assertNotNull(noSQL.getId(), "ID deve ser gerado");
        assertFalse(noSQL.getId().isEmpty(), "ID não deve ser vazio");

        assertEquals("carrie.bradshaw@example.com", noSQL.getUsername());
        assertNotEquals("Password123!", noSQL.getPassword());
        assertTrue(noSQL.getPassword().startsWith("$2a$") || noSQL.getPassword().startsWith("$2b$"), "Password deve em BCrypt");

        assertEquals("Carrie Bradshaw", noSQL.getName());
        assertTrue(noSQL.isEnabled(), "Utilizador deve vir enabled");

        assertEquals(1, noSQL.getAuthorities().size());
        assertTrue(noSQL.getAuthorities().contains(new Role(Role.ADMIN)));
    }

    // Black Box Test
    @Test
    void testToDomainRoundTripKeepsSemantics() {
        UserNoSQL noSQL = UserNoSQL.fromDomain(domainUser);
        User back = noSQL.toDomain();

        assertEquals(domainUser.getUsername(), back.getUsername(), "Username deve manter-se");
        assertEquals("Carrie Bradshaw", back.getName().toString(), "Nome deve manter-se");
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
        UserNoSQL a = UserNoSQL.fromDomain(domainUser);
        UserNoSQL b = UserNoSQL.fromDomain(domainUser);
        assertNotEquals(a.getId(), b.getId(), "Duas instâncias distintas devem ter IDs distintos");
    }

    // White Box Test
    @Test
    void testConstructorDefaults() throws Exception {
        Constructor<UserNoSQL> c = UserNoSQL.class.getDeclaredConstructor();
        c.setAccessible(true);
        UserNoSQL empty = c.newInstance();

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
        UserNoSQL noSQL = UserNoSQL.fromDomain(domainUser);

        assertTrue(noSQL.isAccountNonExpired());
        assertTrue(noSQL.isAccountNonLocked());
        assertTrue(noSQL.isCredentialsNonExpired());
        assertTrue(noSQL.isEnabled());

        noSQL.setEnabled(false);
        assertFalse(noSQL.isAccountNonExpired());
        assertFalse(noSQL.isAccountNonLocked());
        assertFalse(noSQL.isCredentialsNonExpired());
        assertFalse(noSQL.isEnabled());
    }

    // White Box Test
    @Test
    void testAuthoritiesAreDefensiveCopiedOnConstruction() {
        Role admin = new Role(Role.ADMIN);
        Role librarian = new Role(Role.LIBRARIAN);

        User mutableDomain = new User("alice@example.com", "Password123!", new Name("Alice"), Set.of(admin));
        UserNoSQL sql = UserNoSQL.fromDomain(mutableDomain);

        mutableDomain.addAuthority(librarian);

        assertEquals(1, sql.getAuthorities().size(), "Tamanho deve manter-se");
        assertTrue(sql.getAuthorities().contains(admin));
        assertFalse(sql.getAuthorities().contains(librarian));
    }

    // White Box Test
    @Test
    void testFromDomainFactoryIsEquivalentToConstructor() {
        UserNoSQL viaFactory = UserNoSQL.fromDomain(domainUser);
        UserNoSQL viaCtor = new UserNoSQL(domainUser);

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

        UserNoSQL noSQL = UserNoSQL.fromDomain(d);
        User back = noSQL.toDomain();

        assertEquals(Set.of(r1, r2), back.getAuthorities(), "Authorities devem manter");
    }
}
