package pt.psoft.g1.psoftg1.bookmanagement.services;


import pt.psoft.g1.psoftg1.bookmanagement.model.Book;
import pt.psoft.g1.psoftg1.bookmanagement.model.Genre;
import pt.psoft.g1.psoftg1.bookmanagement.model.Isbn;
import pt.psoft.g1.psoftg1.bookmanagement.model.Title;

import java.util.List;
import java.util.Optional;

/**
 *
 */
public interface BookService {
    Book create(CreateBookRequest request);
    Book save(Book book);
    Optional<Book> findByIsbn(Isbn isbn);
    Book update(UpdateBookRequest request, String currentVersion);
    List<Book> findByGenre(Genre genre);
    List<Book> findByTitle(String title);
}
