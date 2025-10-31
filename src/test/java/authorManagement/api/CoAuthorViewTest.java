package authorManagement.api;

import org.junit.jupiter.api.Test;
import pt.psoft.g1.psoftg1.authormanagement.api.CoAuthorView;
import pt.psoft.g1.psoftg1.bookmanagement.api.BookShortView;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CoAuthorViewTest {

    private BookShortView createBook(String title, String isbn) {
        BookShortView b = new BookShortView();
        b.setTitle(title);
        b.setIsbn(isbn);
        return b;
    }

    // Black Box Test
    @Test
    void testAllArgsConstructorAndGetters() {
        Map<String, Object> links = Map.of("self", "/api/v1/coauthors/1");
        List<BookShortView> books = List.of(
            createBook("Book One", "ISBN-ONE"),
            createBook("Book Two", "ISBN-TWO")
        );

        CoAuthorView coAuthor = new CoAuthorView("John Doe", links, books);

        assertEquals("John Doe", coAuthor.getName());
        assertEquals(links, coAuthor.get_links());
        assertEquals(books, coAuthor.getBooks());

        assertNotNull(coAuthor.get_links());
        assertNotNull(coAuthor.getBooks());
        assertEquals(2, coAuthor.getBooks().size());
    }

    // Black Box Test
    @Test
    void testSettersAndMutability() {
        CoAuthorView coAuthor = new CoAuthorView(null, null, null);

        coAuthor.setName("Jane Smith");
        coAuthor.set_links(Map.of("self", "/api/v1/coauthors/2"));
        coAuthor.setBooks(List.of(createBook("Another Book", "ISBN-3")));

        assertEquals("Jane Smith", coAuthor.getName());
        assertEquals("/api/v1/coauthors/2", coAuthor.get_links().get("self"));
        assertEquals(1, coAuthor.getBooks().size());
        assertEquals("Another Book", coAuthor.getBooks().get(0).getTitle());
    }

    // Black Box Test
    @Test
    void testEqualsAndHashCode() {
        Map<String, Object> links = Map.of("self", "/api/v1/coauthors/1");
        List<BookShortView> books = List.of(createBook("Book One", "ISBN-1"));

        CoAuthorView coAuthor1 = new CoAuthorView("Alice", links, books);
        CoAuthorView coAuthor2 = new CoAuthorView("Alice", links, books);

        assertEquals(coAuthor1, coAuthor2);
        assertEquals(coAuthor1.hashCode(), coAuthor2.hashCode());
    }

    // Black Box Test
    @Test
    void testToStringContainsFields() {
        CoAuthorView coAuthor = new CoAuthorView("Bob", Map.of(), List.of());
        String str = coAuthor.toString();

        assertTrue(str.contains("Bob"));
        assertTrue(str.contains("books"));
        assertTrue(str.contains("_links"));
    }
}
