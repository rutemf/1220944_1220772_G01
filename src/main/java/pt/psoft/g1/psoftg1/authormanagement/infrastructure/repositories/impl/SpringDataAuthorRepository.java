package pt.psoft.g1.psoftg1.authormanagement.infrastructure.repositories.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import pt.psoft.g1.psoftg1.authormanagement.model.Author;
import pt.psoft.g1.psoftg1.authormanagement.repositories.AuthorRepository;

import java.util.List;
import java.util.Optional;

public interface SpringDataAuthorRepository extends AuthorRepository, CrudRepository<Author, Long> {
    @Override
    Optional<Author> findByAuthorNumber(Long authorNumber);
    @Override
    @Query("SELECT a " +
            "FROM Author a " +
            "WHERE a.name.name = :name")
    List<Author> findByName(String name);
    @Override
    @Query("SELECT a " +
            "FROM Author a " +
            "WHERE a.name.name LIKE %:name% ")
    List<Author> searchByAuthorNameLike(@Param("name") String name);

    @Override
    @Query(value =
            "SELECT a.name AS authorname, COUNT(l.LENDING_NUMBER) AS total_Lending " +
            "FROM Author a " +
            "JOIN BOOK_AUTHORS on a.AUTHOR_NUMBER = BOOK_AUTHORS.AUTHORS_AUTHOR_NUMBER " +
            "JOIN Book b on BOOK_AUTHORS.BOOK_PK = b.PK " +
            "JOIN Lending l ON b.isbn " +
            "GROUP BY a.name " +
            "ORDER BY total_Lending DESC "
            , nativeQuery = true)
    Page<Author> findTopAuthorByLendings(Pageable pageable);

}
