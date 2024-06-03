package pt.psoft.g1.psoftg1.bookmanagement.infrastructure.repositories.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import pt.psoft.g1.psoftg1.bookmanagement.model.Book;
import pt.psoft.g1.psoftg1.bookmanagement.model.BookCountDTO;
import pt.psoft.g1.psoftg1.bookmanagement.model.Isbn;
import pt.psoft.g1.psoftg1.bookmanagement.repositories.BookRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface SpringDataBookRepository  extends BookRepository, CrudRepository<Book, Isbn> {

    @Query("SELECT b " +
            "FROM Book b " +
            "WHERE b.isbn.isbn = :isbn")
    Optional<Book> findByIsbn(@Param("isbn") String isbn);

    @Override
    @Query("SELECT new pt.psoft.g1.psoftg1.bookmanagement.model.BookCountDTO(b, COUNT(l)) " +
                "FROM Book b " +
                "JOIN Lending l ON l.book = b " +
                "WHERE l.startDate > :oneYearAgo " +
                "GROUP BY b " +
                "ORDER BY COUNT(l) DESC")
    Page<BookCountDTO> findTop5BooksLent(@Param("oneYearAgo") LocalDate oneYearAgo, Pageable pageable);


    @Override
    @Query("SELECT b " +
            "FROM Book b " +
            "JOIN Genre g ON b.genre.genre LIKE %:genre% ")
    List<Book> findByGenre(@Param("genre") String genre);

    @Override
    @Query("SELECT b FROM Book b WHERE b.title.title LIKE %:title%")
    List<Book> findByTitle(@Param("title") String title);

    @Override
    @Query(value =
            "SELECT b.* " +
            "FROM Book b " +
            "JOIN BOOK_AUTHORS on b.pk = BOOK_AUTHORS.BOOK_PK " +
            "JOIN AUTHOR a on BOOK_AUTHORS.AUTHORS_AUTHOR_NUMBER = a.AUTHOR_NUMBER " +
            "WHERE a.AUTHOR_NUMBER = :authorNumber "
            , nativeQuery = true)
    List<Book> findBooksByAuthorNumber(Long authorNumber);

}
