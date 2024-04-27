package pt.psoft.g1.psoftg1;

import java.util.List;
import java.util.Optional;

import com.example.demo.bookmanagement.model.Book;

public interface BookRepository {

    Optional<Book> findByISBN(ISBN isbn);

    List<Book> findByISBN(ISBN isbn);

    int deleteByISBNIfMatch(Long id, long desiredVersion);

    Iterable<Book> findAll();

    Book save(Book book);
}