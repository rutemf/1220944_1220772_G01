package bookManagement.api;

import org.junit.jupiter.api.Test;
import pt.psoft.g1.psoftg1.bookmanagement.api.BookCountView;
import pt.psoft.g1.psoftg1.bookmanagement.api.BookView;

import static org.junit.jupiter.api.Assertions.*;

public class BookCountViewTest {

    private BookView createBookView() {
        BookView bookView = new BookView();
        bookView.setIsbn("978-0132350884");
        bookView.setTitle("Clean Code");
        return bookView;
    }

    // Black Box Test
    @Test
    void testSettersAndGetters() {
        BookCountView view = new BookCountView();
        BookView book = createBookView();

        view.setBookView(book);
        view.setLendingCount(10L);

        assertEquals(book, view.getBookView());
        assertEquals(10L, view.getLendingCount());
    }

    // Black Box Test
    @Test
    void testEqualsAndHashCode() {
        BookView book = createBookView();

        BookCountView view1 = new BookCountView();
        view1.setBookView(book);
        view1.setLendingCount(5L);

        BookCountView view2 = new BookCountView();
        view2.setBookView(book);
        view2.setLendingCount(5L);

        assertEquals(view1, view2);
        assertEquals(view1.hashCode(), view2.hashCode());
    }

    // Black Box Test
    @Test
    void testNotEqualsDifferentValues() {
        BookView book1 = createBookView();

        BookView book2 = new BookView();
        book2.setIsbn("978-0321125217");
        book2.setTitle("Domain-Driven Design");

        BookCountView view1 = new BookCountView();
        view1.setBookView(book1);
        view1.setLendingCount(10L);

        BookCountView view2 = new BookCountView();
        view2.setBookView(book2);
        view2.setLendingCount(10L);

        assertNotEquals(view1, view2);
    }

    // Black Box Test
    @Test
    void testToStringContainsFields() {
        BookCountView view = new BookCountView();
        BookView book = createBookView();

        view.setBookView(book);
        view.setLendingCount(7L);

        String str = view.toString();

        assertTrue(str.contains("Clean Code"));
        assertTrue(str.contains("978-0132350884"));
        assertTrue(str.contains("7"));
        assertTrue(str.contains("bookView"));
        assertTrue(str.contains("lendingCount"));
    }
}
