package bookManagement.api;

import org.junit.jupiter.api.Test;
import pt.psoft.g1.psoftg1.bookmanagement.api.BookView;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class BookViewTest {

    // Black Box Test
    @Test
    void testSettersAndGetters() {
        BookView book = new BookView();

        book.setTitle("Clean Architecture");
        book.setAuthors(List.of("Robert C. Martin"));
        book.setGenre("Software Engineering");
        book.setDescription("A guide to software structure and design.");
        book.setIsbn("978-0134494166");
        book.set_links(Map.of("self", "/api/v1/books/1"));

        assertEquals("Clean Architecture", book.getTitle());
        assertEquals(List.of("Robert C. Martin"), book.getAuthors());
        assertEquals("Software Engineering", book.getGenre());
        assertEquals("A guide to software structure and design.", book.getDescription());
        assertEquals("978-0134494166", book.getIsbn());
        assertEquals("/api/v1/books/1", book.get_links().get("self"));
    }

    // Black Box Test
    @Test
    void testDefaultLinksInitialization() {
        BookView book = new BookView();

        assertNotNull(book.get_links(), "_links should be initialized");
        assertTrue(book.get_links().isEmpty(), "_links should start empty");
    }

    // Black Box Test
    @Test
    void testEqualsAndHashCode() {
        BookView book1 = new BookView();
        book1.setTitle("Refactoring");
        book1.setAuthors(List.of("Martin Fowler"));
        book1.setGenre("Software Engineering");
        book1.setDescription("Improving the Design of Existing Code");
        book1.setIsbn("978-0201485677");
        book1.set_links(Map.of("self", "/api/v1/books/2"));

        BookView book2 = new BookView();
        book2.setTitle("Refactoring");
        book2.setAuthors(List.of("Martin Fowler"));
        book2.setGenre("Software Engineering");
        book2.setDescription("Improving the Design of Existing Code");
        book2.setIsbn("978-0201485677");
        book2.set_links(Map.of("self", "/api/v1/books/2"));

        assertEquals(book1, book2);
        assertEquals(book1.hashCode(), book2.hashCode());
    }

    // Black Box Test
    @Test
    void testNotEqualsDifferentValues() {
        BookView book1 = new BookView();
        book1.setTitle("Clean Code");
        book1.setAuthors(List.of("Robert C. Martin"));
        book1.setGenre("Programming");
        book1.setDescription("A handbook of agile software craftsmanship.");
        book1.setIsbn("978-0132350884");
        book1.set_links(Map.of("self", "/api/v1/books/3"));

        BookView book2 = new BookView();
        book2.setTitle("Design Patterns");
        book2.setAuthors(List.of("Erich Gamma", "Richard Helm", "Ralph Johnson", "John Vlissides"));
        book2.setGenre("Software Design");
        book2.setDescription("Elements of Reusable Object-Oriented Software");
        book2.setIsbn("978-0201633610");
        book2.set_links(Map.of("self", "/api/v1/books/4"));

        assertNotEquals(book1, book2);
    }

    // Black Box Test
    @Test
    void testToStringContainsFields() {
        BookView book = new BookView();
        book.setTitle("Effective Java");
        book.setAuthors(List.of("Joshua Bloch"));
        book.setGenre("Programming");
        book.setDescription("Best practices for the Java platform.");
        book.setIsbn("978-0134685991");
        book.set_links(Map.of("self", "/api/v1/books/5"));

        String result = book.toString();

        assertTrue(result.contains("Effective Java"));
        assertTrue(result.contains("Joshua Bloch"));
        assertTrue(result.contains("Programming"));
        assertTrue(result.contains("978-0134685991"));
        assertTrue(result.contains("_links"));
    }
}
