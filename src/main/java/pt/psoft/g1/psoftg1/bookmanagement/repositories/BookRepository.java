package pt.psoft.g1.psoftg1.bookmanagement.repositories;

import pt.psoft.g1.psoftg1.bookmanagement.model.Book;
import pt.psoft.g1.psoftg1.bookmanagement.model.Isbn;

import java.util.List;
import java.util.Optional;


public interface BookRepository {

    Optional<Book> findByISBN(Isbn isbn);

    int deleteByISBNIfMatch(Long id, long desiredVersion);

    Iterable<Book> findAll();

    Book save(Book book);
}
