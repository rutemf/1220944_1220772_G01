package authorManagement.model;

import org.junit.jupiter.api.Test;
import pt.psoft.g1.psoftg1.authormanagement.model.Author;
import pt.psoft.g1.psoftg1.authormanagement.model.AuthorNoSQL;
import pt.psoft.g1.psoftg1.authormanagement.model.Bio;
import pt.psoft.g1.psoftg1.shared.model.Name;

import static org.junit.jupiter.api.Assertions.*;

public class AuthorNoSQLTest {

    // Black Box Test
    @Test
    void testFromDomainMapsAllFields() {
        Author domain= new Author(46L, new Name("Lourenço Seruya"), new Bio("Mistery and crimes"));
        AuthorNoSQL authorNoSQL = AuthorNoSQL.fromDomain(domain);

        assertNotNull(authorNoSQL.getId(), "ID deve ser gerado");
        assertEquals(46L, authorNoSQL.getAuthorNumber());
        assertEquals("Lourenço Seruya", authorNoSQL.getName());
        assertEquals("Mistery and crimes", authorNoSQL.getBio());
    }

    // Black Box Test
    @Test
    void testToDomainRoundTripKeepsValues() {
        Author author= new Author(9L, new Name("Jeffrey Eugenides"), new Bio("Wrote Virgin Suicides"));

        AuthorNoSQL authorNoSQL = AuthorNoSQL.fromDomain(author);
        Author roundTrip = authorNoSQL.toDomain();

        assertEquals(9L, roundTrip.getAuthorNumber());
        assertEquals("Jeffrey Eugenides", roundTrip.getName().toString());
        assertEquals(author.getBio().toString(), roundTrip.getBio().toString());
    }

    // Black Box Test
    @Test
    void testIdsAreDifferentForDifferentRows(){
        Author a1 = new Author(1L, new Name("N1"), new Bio("B1"));
        Author a2 = new Author(2L, new Name("N2"), new Bio("B2"));

        AuthorNoSQL noSQL1 = AuthorNoSQL.fromDomain(a1);
        AuthorNoSQL noSQL2 = AuthorNoSQL.fromDomain(a2);

        assertNotNull(noSQL1.getId());
        assertNotNull(noSQL2.getId());
        assertNotEquals(noSQL1.getId(), noSQL2.getId(), "IDs distintos esperados para rows diferentes");
    }

    // White Box Test
    @Test
    void testConstructorGeneratesValidId(){

    }

    // White Box Test
    @Test
    void testFromDomainCopiesAuthorNumberOnly(){
        Author domain = new Author(144L, new Name("Name"), new Bio("Bio"));
        AuthorNoSQL noSQL = AuthorNoSQL.fromDomain(domain);

        assertEquals(144L, noSQL.getAuthorNumber());
        assertNotNull(noSQL.getId(), "ID é responsabilidade da camada NoSQL");
    }

}