package bookManagement;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import pt.psoft.g1.psoftg1.PsoftG1Application;
import pt.psoft.g1.psoftg1.authormanagement.model.Author;
import pt.psoft.g1.psoftg1.bookmanagement.model.Book;
import pt.psoft.g1.psoftg1.bookmanagement.services.BookService;
import pt.psoft.g1.psoftg1.bookmanagement.services.UpdateBookRequest;
import pt.psoft.g1.psoftg1.genremanagement.model.Genre;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = PsoftG1Application.class)
@ActiveProfiles({"sql", "open"})
@Transactional
public class BookServiceIT {

    @Autowired
    private BookService bookService;

    @Test
    void contextLoads() {
        assertNotNull(bookService);
    }

    @Test
    void testSave() {
        List<Author> listAuthors = new ArrayList<>();

        Book book = new Book("9789124372644", "Test Book", "Test Author", new Genre("Test Genre"), listAuthors, null);
        Book savedBook = bookService.save(book);

        assertNotNull(savedBook, "Saved book should not be null");
        assertEquals(book.getIsbn(), savedBook.getIsbn(), "Isbn name should match");
        assertEquals(book.getTitle().toString(), savedBook.getTitle().toString(), "Title name should match");
        assertEquals(book.getDescription().toString(), savedBook.getDescription().toString(), "Description should match");
        assertEquals(book.getGenre().toString(), savedBook.getGenre().toString(), "Genre should match");
    }

    @Test
    void testFindByIsbn() {
        Book book = bookService.findByIsbn("9789723716160");

        assertNotNull(book, "Book should not be null");
        assertEquals(book.getIsbn().toString(), "9789723716160", "Isbn should match");
        assertEquals(book.getTitle().toString(), "Como se Desenha Uma Casa", "Title should match");
        assertEquals(book.getDescription().toString(), "Como quem, vindo de países distantes fora do caminho.", "Description should match");
        assertEquals(book.getGenre().getGenre(), "Mystery", "Genre should match");
    }

    @Test
    void testFindByGenre() {
        List<Book> books = bookService.findByGenre("Romance");

        assertNotNull(books, "Books list should not be null");
        assertFalse(books.isEmpty(), "Books list should not be empty");

        for (Book book : books) {
            assertEquals("Romance", book.getGenre().getGenre(), "Genre should match");
        }
    }

    @Test
    void testFindByTitle() {
        List<Book> books = bookService.findByTitle("Como se Desenha Uma Casa");

        assertNotNull(books, "Books list should not be null");
        assertFalse(books.isEmpty(), "Books list should not be empty");

        for (Book book : books) {
            assertTrue(book.getTitle().toString().contains("Como se Desenha Uma Casa"), "Title should contain the search term");
        }
    }

    @Test
    void testUpdate() {
        UpdateBookRequest updateRequest = new UpdateBookRequest();
        updateRequest.setIsbn("9782722203402");
        updateRequest.setDescription("No Description");
        updateRequest.setGenre("Fantasy");

        Book updatedBook = bookService.update(updateRequest, "9782722203402");
        assertNotNull(updatedBook, "Updated book should not be null");
        assertEquals("9782722203402", updatedBook.getIsbn().toString(), "Isbn should stay the same");
        assertEquals("No Description", updatedBook.getDescription().toString(), "Description should be updated");
        assertEquals("Fantasy", updatedBook.getGenre().getGenre(), "Genre should be updated");
    }
}
