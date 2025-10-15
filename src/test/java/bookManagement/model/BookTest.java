package bookManagement.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pt.psoft.g1.psoftg1.authormanagement.model.Author;
import pt.psoft.g1.psoftg1.bookmanagement.model.Book;
import pt.psoft.g1.psoftg1.genremanagement.model.Genre;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BookTest {

    @Mock Genre genre;
    @Mock Author author1;
    @Mock Author author2;

    // Black Box Test
    @Test
    void constructsWithValidIsbn13_andStoresAllFields() {
        List<Author> authors = List.of(author1, author2);
        String isbn = "9780306406157";
        String title = "Clean Code";
        String desc = "A handbook of Agile software craftsmanship";
        String photo = "https://cdn.example.com/books/clean-code.jpg";

        Book book = new Book(isbn, title, desc, genre, authors, photo);

        assertNotNull(book.getIsbn());
        assertEquals(isbn, book.getIsbn().toString());

        assertNotNull(book.getTitle());
        assertEquals(title, book.getTitle().toString());

        assertNotNull(book.getDescription());
        assertEquals(desc, book.getDescription().toString());

        assertSame(genre, book.getGenre());
        assertEquals(authors, book.getAuthors());
        assertEquals(photo, book.getPhotoURI());
    }

    // Black Box Test
    @Test
    void constructsWithValidIsbn10() {
        String isbn10 = "0306406152";
        Book book = new Book(isbn10, "Title", "Desc", genre, List.of(), null);
        assertEquals(isbn10, book.getIsbn().toString());
    }

    // Black Box Test
    @Test
    void toStringDelegatesToTitleToString() {
        Book book = new Book("9780306406157", "Refactoring", "Desc", genre, List.of(), null);
        assertEquals(book.getTitle().toString(), book.toString());
    }

    // Black Box Test
    @Test
    void acceptsEmptyAuthorsList() {
        Book book = new Book("9780306406157", "Title", "Desc", genre, List.of(), null);
        assertNotNull(book.getAuthors());
        assertTrue(book.getAuthors().isEmpty());
    }


    // White Box Test
    @Test
    void authorsList_isStoredByReference_currentImplementation() {
        List<Author> authors = new ArrayList<>();
        authors.add(author1);

        Book book = new Book("9780306406157", "Title", "Desc", genre, authors, null);

        authors.add(author2);
        assertEquals(2, book.getAuthors().size());
    }

    // White Box Test
    @Test
    void invalidIsbn_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () ->
                new Book("INVALID", "Title", "Desc", genre, List.of(author1), null));
        assertThrows(IllegalArgumentException.class, () ->
                new Book("1234567890123", "Title", "Desc", genre, List.of(author1), null));
    }

    // White Box Test
    @Test
    void settersFromLombok_updateFields() {
        Book book = new Book("9780306406157", "Title", "Desc", genre, List.of(author1), "a");

        // Photo
        book.setPhotoURI("b");
        assertEquals("b", book.getPhotoURI());

        // Genre
        Genre newGenre = genre;
        book.setGenre(newGenre);
        assertSame(newGenre, book.getGenre());

        // Authors
        List<Author> newAuthors = List.of(author2);
        book.setAuthors(newAuthors);
        assertEquals(newAuthors, book.getAuthors());
    }

    // White Box Test
    @Test
    void allowsNullGenreAndNullAuthors_currentImplementation() {
        Book book = new Book("9780306406157", "Title", "Desc", null, null, null);
        assertNull(book.getGenre());
        assertNull(book.getAuthors());
    }
}
