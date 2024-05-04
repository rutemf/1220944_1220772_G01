package pt.psoft.g1.psoftg1.bookmanagement.infrastructure.repositories.impl;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import pt.psoft.g1.psoftg1.bookmanagement.model.Book;
import pt.psoft.g1.psoftg1.bookmanagement.model.Isbn;
import pt.psoft.g1.psoftg1.bookmanagement.repositories.BookRepository;


import java.util.List;
import java.util.Optional;

public interface SpringDataBookRepository  extends BookRepository, CrudRepository<Book, Isbn> {

    @Query("SELECT b " +
            "FROM Book b " +
            "WHERE b.isbn.isbn = :isbn")
    Optional<Book> findByIsbn(@Param("isbn") String isbn);

    @Override
    @Query("SELECT b " +
            "FROM Book b " +
            "JOIN Genre g ON b.genre.genre LIKE %:genre% " +
            "WHERE b.isbn.isbn = :isbn")
    List<Book> findByGenre(@Param("genre") String genre);

}
