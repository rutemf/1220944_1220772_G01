package pt.psoft.g1.psoftg1.bookmanagement.infrastructure.repositories.impl;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import pt.psoft.g1.psoftg1.authormanagement.model.Author;
import pt.psoft.g1.psoftg1.bookmanagement.model.Book;
import pt.psoft.g1.psoftg1.bookmanagement.model.Genre;
import pt.psoft.g1.psoftg1.bookmanagement.model.Isbn;
import pt.psoft.g1.psoftg1.bookmanagement.repositories.BookRepository;
import pt.psoft.g1.psoftg1.bookmanagement.services.UpdateBookRequest;

import java.util.List;
import java.util.Optional;

public interface SpringDataBookRepository  extends BookRepository, CrudRepository<Book, Isbn> {

    @Query("SELECT b " +
            "FROM Book b " +
            "WHERE b.isbn = :isbn")
    Optional<Book> findByIsbn(@Param("isbn") String isbn);

    //TODO: Só reclama que o ultimo argumento é do tipo Author... Não lhe podemos passar uma lista???

    @Modifying
    @Query("UPDATE Book b " +
        "SET " +
        "b.title.title = COALESCE(:title, b.title.title), " +
        "b.genre.genre = COALESCE(:genre, b.genre.genre), " +
        "b.description.description = COALESCE(:description, b.description.description), " +
        "b.authors = COALESCE(:authors, b.authors) " +
        "WHERE b.isbn = :isbn")
    Book update(@Param("isbn") @NotNull String isbn, @Param("title") String title, @Param("description") String description, @Param("genre") String genre, @Param("authors") List<Author> authors);


    @Override
    @Query("SELECT b " +
            "FROM Book b " +
            "JOIN Genre g ON b.genre.genre LIKE %:genre% " +
            "WHERE b.isbn = :isbn")
    List<Book> findByGenre(@Param("genre") String genre);


}
