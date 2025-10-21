package authorManagement;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import pt.psoft.g1.psoftg1.authormanagement.model.Author;
import pt.psoft.g1.psoftg1.authormanagement.services.AuthorService;
import pt.psoft.g1.psoftg1.authormanagement.services.CreateAuthorRequest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = pt.psoft.g1.psoftg1.PsoftG1Application.class)
@ActiveProfiles({"sql", "open"})
@Transactional
public class AuthorServiceIT {

    @Autowired
    private AuthorService authorService;

    @Test
    void contextLoads() {
        assertNotNull(authorService);
    }

    @Test
    void testFindAllAuthors() {
        List<Author> authors = (List<Author>) authorService.findAll();

        assertNotNull(authors);
        assertFalse(authors.isEmpty());
    }

    @Test
    void testFindByAuthorNumber() {
        Author author = authorService.findAll().iterator().next();
        Optional<Author> found = authorService.findByAuthorNumber(author.getAuthorNumber());

        assertTrue(found.isPresent(), "Author should be present");
        assertEquals(author.getAuthorNumber(), found.get().getAuthorNumber());
        assertEquals(author.getName(), found.get().getName());
        assertEquals(author.getBio().getBio(), found.get().getBio().getBio());
    }

    @Test
    void testCreate() {
        CreateAuthorRequest createAuthorRequest = new CreateAuthorRequest("MiguelTest", "Test de Author", null, null);
        Author savedAuthor = authorService.create(createAuthorRequest);

        assertNotNull(savedAuthor, "Saved author should not be null");
        assertEquals(createAuthorRequest.getName(), savedAuthor.getName().toString(), "Author name should match");
        assertEquals(createAuthorRequest.getBio(), savedAuthor.getBio().toString(), "Author bio should match");
    }
}
