package bookManagement.api;

import org.junit.jupiter.api.Test;
import pt.psoft.g1.psoftg1.bookmanagement.api.BookAverageLendingDurationView;
import pt.psoft.g1.psoftg1.bookmanagement.api.BookView;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class BookAverageLendingDurationViewTest {

    // Black Box Test
    @Test
    void testSettersAndGetters() {
        BookView bookView = new BookView();
        bookView.setTitle("Clean Code");
        bookView.setAuthors(List.of("Robert C. Martin"));
        bookView.setGenre("Programming");
        bookView.setDescription("A handbook of agile software craftsmanship.");
        bookView.setIsbn("978-0132350884");
        bookView.set_links(Map.of("self", "/api/v1/books/1"));

        BookAverageLendingDurationView view = new BookAverageLendingDurationView();

        view.setBook(bookView);
        view.setAverageLendingDuration(7.5);

        assertEquals(bookView, view.getBook());
        assertEquals(7.5, view.getAverageLendingDuration());
    }

    // Black Box Test
    @Test
    void testEqualsAndHashCode() {
        BookView book = new BookView();
        book.setTitle("Refactoring");
        book.setAuthors(List.of("Martin Fowler"));
        book.setGenre("Software Engineering");
        book.setDescription("Improving the Design of Existing Code");
        book.setIsbn("978-0201485677");
        book.set_links(Map.of("self", "/api/v1/books/2"));

        BookAverageLendingDurationView view1 = new BookAverageLendingDurationView();
        view1.setBook(book);
        view1.setAverageLendingDuration(10.0);

        BookAverageLendingDurationView view2 = new BookAverageLendingDurationView();
        view2.setBook(book);
        view2.setAverageLendingDuration(10.0);

        assertEquals(view1, view2);
        assertEquals(view1.hashCode(), view2.hashCode());
    }

    // Black Box Test
    @Test
    void testNotEqualsDifferentValues() {
        BookView book1 = new BookView();
        book1.setTitle("Book A");
        book1.setAuthors(List.of("Author A"));
        book1.setGenre("Fiction");
        book1.setIsbn("ISBN-A");
        book1.set_links(Map.of("self", "/api/v1/books/A"));

        BookView book2 = new BookView();
        book2.setTitle("Book B");
        book2.setAuthors(List.of("Author B"));
        book2.setGenre("Non-Fiction");
        book2.setIsbn("ISBN-B");
        book2.set_links(Map.of("self", "/api/v1/books/B"));

        BookAverageLendingDurationView view1 = new BookAverageLendingDurationView();
        view1.setBook(book1);
        view1.setAverageLendingDuration(5.0);

        BookAverageLendingDurationView view2 = new BookAverageLendingDurationView();
        view2.setBook(book2);
        view2.setAverageLendingDuration(12.0);

        assertNotEquals(view1, view2);
    }

    // Black Box Test
    @Test
    void testToStringContainsFields() {
        BookView book = new BookView();
        book.setTitle("Effective Java");
        book.setAuthors(List.of("Joshua Bloch"));
        book.setGenre("Programming");
        book.setIsbn("978-0134685991");
        book.set_links(Map.of("self", "/api/v1/books/5"));

        BookAverageLendingDurationView view = new BookAverageLendingDurationView();
        view.setBook(book);
        view.setAverageLendingDuration(8.3);

        String result = view.toString();

        assertTrue(result.contains("Effective Java"));
        assertTrue(result.contains("8.3"));
    }
}
