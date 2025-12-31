package books.books.repositories;

import books.shared.services.Page;
import org.springframework.data.repository.query.Param;
import books.books.model.Book;
import books.books.services.SearchBooksQuery;

import java.util.List;
import java.util.Optional;

/**
 *
 */
public interface BookRepository {

    List<Book> findByGenre(@Param("genre") String genre);

    List<Book> findByTitle(@Param("title") String title);

    List<Book> findByAuthorName(@Param("authorName") String authorName);

    Optional<Book> findByIsbn(@Param("isbn") String isbn);

//    Page<BookCountDTO> findTop5BooksLent(@Param("oneYearAgo") LocalDate oneYearAgo, Pageable pageable);

    List<Book> findBooksByAuthorNumber(Long authorNumber);

    List<Book> searchBooks(Page page, SearchBooksQuery query);

    Book save(Book book);

    void delete(Book book);
}
