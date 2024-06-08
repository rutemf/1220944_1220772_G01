package pt.psoft.g1.psoftg1.bookmanagement.services;


import pt.psoft.g1.psoftg1.bookmanagement.model.Book;
import pt.psoft.g1.psoftg1.readermanagement.model.ReaderDetails;

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
    List<Book> findByGenre(String genre);
    List<Book> findByTitle(String title);
    List<BookCountDTO> findTop5BooksLent();
    Optional<Book> removeBookPhoto(String isbn, long desiredVersion);
}
