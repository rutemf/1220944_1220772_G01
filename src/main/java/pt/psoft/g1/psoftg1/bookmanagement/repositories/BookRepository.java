package pt.psoft.g1.psoftg1.bookmanagement.repositories;

import pt.psoft.g1.psoftg1.bookmanagement.model.Book;
import pt.psoft.g1.psoftg1.bookmanagement.model.Isbn;

import pt.psoft.g1.psoftg1.bookmanagement.model.Description;
import pt.psoft.g1.psoftg1.bookmanagement.model.Genre;
import pt.psoft.g1.psoftg1.bookmanagement.model.Isbn;
import pt.psoft.g1.psoftg1.bookmanagement.services.UpdateBookRequest;
import pt.psoft.g1.psoftg1.bookmanagement.model.Title;

import java.util.Optional;


/**
 *
 */
public interface BookRepository {

    int deleteByISBNIfMatch(Long id, long desiredVersion);
    Iterable<Book> findAll();
    Book findByIsbn(Isbn isbn);

    Book save(Book book) throws Exception;
    Book update(Isbn isbn, UpdateBookRequest updateBookRequest);
}
