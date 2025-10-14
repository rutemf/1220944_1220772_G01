package genreManagement.model;

import org.junit.jupiter.api.Test;
import pt.psoft.g1.psoftg1.genremanagement.model.Genre;

import static org.junit.jupiter.api.Assertions.*;

public class GenreTest {

    // Black Box Test
    @Test
    void shouldCreateGenreSuccessfully() {
        Genre genre = new Genre("Action");
        assertEquals("Action", genre.toString());
    }

    // Black Box Test
    @Test
    void shouldUpdateGenreSuccessfully() {
        Genre genre = new Genre("Action");
        genre.setGenre("Drama");
        assertEquals("Drama", genre.toString());
    }

    // White Box Test
    @Test
    void shouldThrowExceptionForNullGenre() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> new Genre(null));
        assertEquals("Genre cannot be null.", e.getMessage());
    }

    // White Box Test
    @Test
    void shouldThrowExceptionForBlankGenre() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> new Genre("  "));
        assertEquals("Genre cannot be blank.", e.getMessage());
    }

    // White Box Test
    @Test
    void shouldThrowExceptionForTooLongGenre() {
        String longGenre = "A".repeat(101);
        Exception e = assertThrows(IllegalArgumentException.class, () -> new Genre(longGenre));
        assertEquals("Genre has a maximum of 100 characters.", e.getMessage());
    }
}
