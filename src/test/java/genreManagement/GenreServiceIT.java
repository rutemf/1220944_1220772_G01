package genreManagement;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import pt.psoft.g1.psoftg1.LibraryManagementApplication;
import pt.psoft.g1.psoftg1.genremanagement.model.Genre;
import pt.psoft.g1.psoftg1.genremanagement.services.GenreService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = LibraryManagementApplication.class)
@ActiveProfiles({"sql", "open"})
@Transactional
public class GenreServiceIT {

    @Autowired
    private GenreService genreService;

    @Test
    void contextLoads() {
        assertNotNull(genreService);
    }

    @Test
    void testFindAllGenres() {
        List<Genre> genres = (List<Genre>) genreService.findAll();

        assertNotNull(genres);
        assertFalse(genres.isEmpty());
    }

    @Test
    void testFindGenreById() {
        Genre genre = genreService.findAll().iterator().next();
        Optional<Genre> found = genreService.findByString(genre.getGenre());

        assertTrue(found.isPresent(), "Genre should be present");
        assertEquals(genre.getGenre(), found.get().getGenre());
    }

    @Test
    void testSave() {
        Genre genre = new Genre("Musical");
        Genre savedGenre = genreService.save(genre);

        assertNotNull(savedGenre, "Saved genre should not be null");
        assertEquals(genre.getGenre(), savedGenre.getGenre(), "Genre name should match");
    }
}
