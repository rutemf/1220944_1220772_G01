package pt.psoft.g1.psoftg1.bookmanagement.repositories;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.repository.query.Param;
import pt.psoft.g1.psoftg1.bookmanagement.model.Book;
import pt.psoft.g1.psoftg1.bookmanagement.model.Isbn;

import pt.psoft.g1.psoftg1.bookmanagement.model.Description;
import pt.psoft.g1.psoftg1.bookmanagement.model.Genre;
import pt.psoft.g1.psoftg1.bookmanagement.model.Isbn;
import pt.psoft.g1.psoftg1.bookmanagement.services.UpdateBookRequest;
import pt.psoft.g1.psoftg1.bookmanagement.model.Title;

import java.util.List;
import java.util.Optional;


/**
 *
 */
public interface BookRepository {

    List<Book> findByGenre(@Param("genre") Genre genre);

    Optional<Book> findByIsbn(@Param("isbn") String isbn);

    Book save(Book book);
    Book update(@Param("isbn") @NotNull String isbn, @Param("title") String title, @Param("description") String description, @Param("genre") Genre genre);
}
