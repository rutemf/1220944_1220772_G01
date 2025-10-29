package genreManagement.model;

import org.junit.jupiter.api.Test;
import pt.psoft.g1.psoftg1.genremanagement.model.Genre;
import pt.psoft.g1.psoftg1.genremanagement.model.GenreNoSQL;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GenreNoSQLTest {

    // Black Box Test
    @Test
    void testFromDomainCreatesCorrectGenreSQL() {
        Genre domain = new Genre("Romantic Comedy");
        GenreNoSQL genreNoSQL = GenreNoSQL.fromDomain(domain);

        assertNotNull(genreNoSQL.getId());
        assertEquals("Romantic Comedy", genreNoSQL.getGenre());
    }

    // Black Box Test
    @Test
    void testToDomainReturnsEquivalentGenre() {
        Genre domain = new Genre("Fantasy");
        GenreNoSQL genreNoSQL = GenreNoSQL.fromDomain(domain);

        Genre converted = genreNoSQL.toDomain();

        assertEquals(domain.getGenre(), converted.getGenre());
    }

    // White Box Test
    @Test
    void testConstructorGeneratesValidId() {
        Genre domain = new Genre("Sci-Fi");
        GenreNoSQL genreNoSQL = new GenreNoSQL(domain);

        String id = genreNoSQL.getId();

        assertNotNull(id);
        assertTrue(id.matches("\\d{13}-[0-9a-fA-F]{6}"), "ID should match format: timestamp-randomHex(6 digits)");
    }

    // White Box Test
    @Test
    void testConstructorCopiesGenreCorrectly() {
        Genre domain = new Genre("Drama");
        GenreNoSQL genreNoSQL = new GenreNoSQL(domain);

        assertEquals("Drama", genreNoSQL.getGenre());
    }

}
