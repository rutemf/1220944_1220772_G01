package authorManagement.api;

import org.junit.jupiter.api.Test;
import pt.psoft.g1.psoftg1.authormanagement.api.AuthorCoAuthorBooksView;
import pt.psoft.g1.psoftg1.authormanagement.api.AuthorView;
import pt.psoft.g1.psoftg1.authormanagement.api.CoAuthorView;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class AuthorCoAuthorBooksViewTest {

    private AuthorView createAuthor() {
        AuthorView author = new AuthorView();
        author.setAuthorNumber(1L);
        author.setName("Agatha Christie");
        author.setBio("Famous English novelist");
        author.setPhoto("photo_url.jpg");
        author.get_links().put("self", "/api/v1/authors/1");
        return author;
    }

    private CoAuthorView createCoAuthor(String name) {
        CoAuthorView coAuthor = new CoAuthorView(
                name,
                Map.of("self", "/api/v1/coauthors/" + name.toLowerCase().replace(" ", "")),
                List.of()
        );
        return coAuthor;
    }

    // Black Box Test
    @Test
    void testAllArgsConstructorAndGetters() {
        AuthorView author = createAuthor();
        List<CoAuthorView> coauthors = List.of(createCoAuthor("John Doe"), createCoAuthor("Jane Smith"));

        AuthorCoAuthorBooksView view = new AuthorCoAuthorBooksView(author, coauthors);

        assertEquals(author, view.getAuthor());
        assertEquals(coauthors, view.getCoauthors());
        assertEquals(2, view.getCoauthors().size());
    }

    // Black Box Test
    @Test
    void testSettersAndMutability() {
        AuthorCoAuthorBooksView view = new AuthorCoAuthorBooksView(null, null);

        AuthorView author = createAuthor();
        List<CoAuthorView> coauthors = List.of(createCoAuthor("Emily Brontë"));

        view.setAuthor(author);
        view.setCoauthors(coauthors);

        assertEquals(author, view.getAuthor());
        assertEquals(coauthors, view.getCoauthors());
        assertEquals("Emily Brontë", view.getCoauthors().get(0).getName());
    }

    // Black Box Test
    @Test
    void testEqualsAndHashCode() {
        AuthorView author = createAuthor();
        List<CoAuthorView> coauthors = List.of(createCoAuthor("John Doe"));

        AuthorCoAuthorBooksView view1 = new AuthorCoAuthorBooksView(author, coauthors);
        AuthorCoAuthorBooksView view2 = new AuthorCoAuthorBooksView(author, coauthors);

        assertEquals(view1, view2);
        assertEquals(view1.hashCode(), view2.hashCode());
    }

    // Black Box Test
    @Test
    void testToStringContainsFields() {
        AuthorCoAuthorBooksView view = new AuthorCoAuthorBooksView(createAuthor(), List.of(createCoAuthor("John Doe")));

        String result = view.toString();

        assertTrue(result.contains("Agatha Christie"));
        assertTrue(result.contains("John Doe"));
        assertTrue(result.contains("coauthors"));
    }
}
