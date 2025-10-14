package authorManagement.model;

import org.junit.jupiter.api.Test;
import pt.psoft.g1.psoftg1.authormanagement.model.Bio;
import pt.psoft.g1.psoftg1.shared.model.Name;
import pt.psoft.g1.psoftg1.authormanagement.model.Author;
import pt.psoft.g1.psoftg1.authormanagement.model.AuthorSQL;

import static org.junit.jupiter.api.Assertions.*;

public class AuthorSQLTest {

    // Black Box Test
    @Test
    void testFromDomainMapsAllFields() {
        Author domain = new Author(42L, new Name("Ada Lovelace"), new Bio("First programmer"));
        AuthorSQL sql = AuthorSQL.fromDomain(domain);

        assertNotNull(sql.getId(), "ID deve ser gerado");
        assertEquals(42L, sql.getAuthorNumber());
        assertEquals("Ada Lovelace", sql.getName());
        assertEquals("First programmer", sql.getBio());
    }

    // Black Box Test
    @Test
    void testToDomainRoundTripKeepsValues() {
        Author original = new Author(7L, new Name("Grace Hopper"), new Bio("COBOL & compilers"));

        AuthorSQL sql = AuthorSQL.fromDomain(original);
        Author roundTrip = sql.toDomain();

        assertEquals(7L, roundTrip.getAuthorNumber());
        assertEquals("Grace Hopper", roundTrip.getName().toString());
        assertEquals(original.getBio().toString(), roundTrip.getBio().toString());
    }

    // Black Box Test
    @Test
    void testIdsAreDifferentForDifferentRows() {
        Author a1 = new Author(1L, new Name("N1"), new Bio("B1"));
        Author a2 = new Author(2L, new Name("N2"), new Bio("B2"));

        AuthorSQL s1 = AuthorSQL.fromDomain(a1);
        AuthorSQL s2 = AuthorSQL.fromDomain(a2);

        assertNotNull(s1.getId());
        assertNotNull(s2.getId());
        assertNotEquals(s1.getId(), s2.getId(), "IDs distintos esperados para rows diferentes");
    }

    // White Box Test
    @Test
    void testConstructorGeneratesValidId() {
        Author domain = new Author(111L, new Name("Name"), new Bio("Bio"));
        AuthorSQL authorSQL = new AuthorSQL(domain);

        String id = authorSQL.getId();

        assertNotNull(id);
        assertTrue(id.matches("^[A-Za-z0-9+/#]+"), "ID should match base65 format");
    }

    // White Box Test
    @Test
    void testFromDomainCopiesAuthorNumberOnly() {
        Author domain = new Author(123L, new Name("Name"), new Bio("Bio"));
        AuthorSQL sql = AuthorSQL.fromDomain(domain);

        assertEquals(123L, sql.getAuthorNumber());
        assertNotNull(sql.getId(), "ID é responsabilidade da camada SQL");
    }
}
