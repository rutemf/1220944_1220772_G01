package pt.psoft.g1.psoftg1.bookmanagement.services;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pt.psoft.g1.psoftg1.bookmanagement.model.Book;
import pt.psoft.g1.psoftg1.bookmanagement.model.BookCountDTO;
import pt.psoft.g1.psoftg1.bookmanagement.model.Genre;

import java.util.List;
import java.util.Optional;

/**
 *
 */
public interface BookService {
    Book create(CreateBookRequest request, String isbn);
    Book save(Book book);
    Optional<Book> findByIsbn(String isbn);
    Book update(UpdateBookRequest request, String currentVersion);
    List<Book> findByGenre(Genre genre);
    List<Book> findByTitle(String title);
    List<BookCountDTO> findTop5BooksLent();
}
