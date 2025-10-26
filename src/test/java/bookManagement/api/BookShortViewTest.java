package bookManagement.api;

import org.junit.jupiter.api.Test;
import pt.psoft.g1.psoftg1.bookmanagement.api.BookShortView;

import static org.junit.jupiter.api.Assertions.*;

public class BookShortViewTest {

    // Black Box Test
    @Test
    void testSettersAndGetters() {
        BookShortView book = new BookShortView();
        book.setTitle("Clean Code");
        book.setIsbn("978-0132350884");
        book.set_links("/api/v1/books/1");

        assertEquals("Clean Code", book.getTitle());
        assertEquals("978-0132350884", book.getIsbn());
        assertEquals("/api/v1/books/1", book.get_links());
    }

    // Black Box Test
    @Test
    void testEqualsAndHashCode() {
        BookShortView book1 = new BookShortView();
        book1.setTitle("Refactoring");
        book1.setIsbn("978-0201485677");
        book1.set_links("/api/v1/books/2");

        BookShortView book2 = new BookShortView();
        book2.setTitle("Refactoring");
        book2.setIsbn("978-0201485677");
        book2.set_links("/api/v1/books/2");

        assertEquals(book1, book2);
        assertEquals(book1.hashCode(), book2.hashCode());
    }

    // Black Box Test
    @Test
    void testNotEqualsDifferentValues() {
        BookShortView book1 = new BookShortView();
        book1.setTitle("Clean Code");
        book1.setIsbn("978-0132350884");
        book1.set_links("/api/v1/books/1");

        BookShortView book2 = new BookShortView();
        book2.setTitle("Domain-Driven Design");
        book2.setIsbn("978-0321125217");
        book2.set_links("/api/v1/books/2");

        assertNotEquals(book1, book2);
    }

    // Black Box Test
    @Test
    void testToStringContainsFields() {
        BookShortView book = new BookShortView();
        book.setTitle("Effective Java");
        book.setIsbn("978-0134685991");
        book.set_links("/api/v1/books/3");

        String str = book.toString();

        assertTrue(str.contains("Effective Java"));
        assertTrue(str.contains("978-0134685991"));
        assertTrue(str.contains("/api/v1/books/3"));
        assertTrue(str.contains("title"));
        assertTrue(str.contains("isbn"));
        assertTrue(str.contains("_links"));
    }
}
