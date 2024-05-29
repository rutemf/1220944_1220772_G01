package pt.psoft.g1.psoftg1.bookmanagement.repositories;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import pt.psoft.g1.psoftg1.authormanagement.model.Author;
import pt.psoft.g1.psoftg1.bookmanagement.model.*;

import pt.psoft.g1.psoftg1.bookmanagement.model.Isbn;
import pt.psoft.g1.psoftg1.bookmanagement.services.UpdateBookRequest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


/**
 *
 */
public interface BookRepository {


    List<Book> findByGenre(@Param("genre") String genre);

    List<Book> findByTitle(@Param("title") String title);

    Optional<Book> findByIsbn(@Param("isbn") String isbn);

    Page<BookCountDTO> findTop5BooksLent(Pageable pageable);

    Book save(Book book);

}
