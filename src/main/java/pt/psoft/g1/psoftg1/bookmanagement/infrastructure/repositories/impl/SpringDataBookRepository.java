package pt.psoft.g1.psoftg1.bookmanagement.infrastructure.repositories.impl;

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
/*
// Ricardo: isto entra em conflito com BookRepositoryImpl. Essa classe não deve existir.
    @Override
    @Query("SELECT b " +
            "FROM Book b " +
            "WHERE b.isbn = :isbn")
    Book findByIsbn(@Param("isbn") String isbn);
*/

    @Override
    @Query("SELECT b " +
            "FROM Book b " +
            "JOIN Genre g ON b.genre.genre = g.genre " +
            "WHERE b.isbn = :isbn")
    List<Book> findByGenre(@Param("genre") Genre genre);

}
