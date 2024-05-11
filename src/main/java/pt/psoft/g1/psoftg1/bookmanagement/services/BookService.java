package pt.psoft.g1.psoftg1.bookmanagement.services;


import jakarta.validation.constraints.NotNull;
import org.springframework.data.repository.query.Param;
import pt.psoft.g1.psoftg1.bookmanagement.model.Book;
import pt.psoft.g1.psoftg1.bookmanagement.model.Genre;
import pt.psoft.g1.psoftg1.bookmanagement.model.Isbn;

import java.util.List;
import java.util.Optional;

/**
 *
 */
public interface BookService {
    Book create(CreateBookRequest request) throws Exception;
    Book save(Book book);
    Optional<Book> findByIsbn(Isbn isbn);
    Book update(UpdateBookRequest request,String currentVersion) throws Exception;
    List<Book> findByGenre(Genre genre);
}
