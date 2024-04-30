package pt.psoft.g1.psoftg1.bookmanagement.infrastructure.repositories.impl;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import pt.psoft.g1.psoftg1.bookmanagement.model.Book;
import pt.psoft.g1.psoftg1.bookmanagement.model.Genre;
import pt.psoft.g1.psoftg1.bookmanagement.model.Isbn;
import pt.psoft.g1.psoftg1.bookmanagement.repositories.BookRepository;
import pt.psoft.g1.psoftg1.bookmanagement.services.UpdateBookRequest;

import java.util.List;

public interface SpringDataBookRepository  extends BookRepository, CrudRepository<Book, Isbn> {

    @Query("SELECT b " +
            "FROM Book b " +
            "WHERE b.isbn = :isbn")
    Book findByIsbn(@Param("isbn") String isbn);

    @Query("UPDATE Book b " +
        "SET " +
        "b.title = COALESCE(:title, b.title), " +
        "b.genre = COALESCE(:genre, b.genre), " +
        "b.description = COALESCE(:description, b.description) " +
        "WHERE b.isbn = :isbn")
    Book update(@Param("isbn") @NotNull String isbn, @Param("title") String title, @Param("description") String description, @Param("genre") Genre genre);

    @Override
    @Query("SELECT b " +
            "FROM Book b " +
            "JOIN Genre g ON b.genre.genre LIKE %:genre%" +
            "WHERE b.isbn = :isbn")
    List<Book> findByGenre(@Param("genre") Genre genre);

}
