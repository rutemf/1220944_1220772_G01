package genreManagement.model;

import org.junit.jupiter.api.Test;
import pt.psoft.g1.psoftg1.genremanagement.model.Genre;
import pt.psoft.g1.psoftg1.genremanagement.dataschema.GenreSQL;

import static org.junit.jupiter.api.Assertions.*;

public class GenreSQLTest {

    // Black Box Test
    @Test
    void testFromDomainCreatesCorrectGenreSQL() {
        Genre domain = new Genre("Horror");
        GenreSQL genreSQL = GenreSQL.fromDomain(domain);

        assertNotNull(genreSQL.getId());
        assertEquals("Horror", genreSQL.getGenre());
    }

    // Black Box Test
    @Test
    void testToDomainReturnsEquivalentGenre() {
        Genre domain = new Genre("Fantasy");
        GenreSQL genreSQL = GenreSQL.fromDomain(domain);

        Genre converted = genreSQL.toDomain();

        assertEquals(domain.getGenre(), converted.getGenre());
    }

    // White Box Test
    @Test
    void testConstructorGeneratesValidId() {
        Genre domain = new Genre("Sci-Fi");
        GenreSQL genreSQL = new GenreSQL(domain);

        String id = genreSQL.getId();

        assertNotNull(id);
        assertTrue(id.matches("^[A-Za-z0-9+/#]+"), "ID should match base65 format");
    }

    // White Box Test
    @Test
    void testConstructorCopiesGenreCorrectly() {
        Genre domain = new Genre("Drama");
        GenreSQL genreSQL = new GenreSQL(domain);

        assertEquals("Drama", genreSQL.getGenre());
    }
}
