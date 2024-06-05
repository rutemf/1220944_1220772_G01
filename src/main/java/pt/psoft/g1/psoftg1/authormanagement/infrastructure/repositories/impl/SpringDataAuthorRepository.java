package pt.psoft.g1.psoftg1.authormanagement.infrastructure.repositories.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import pt.psoft.g1.psoftg1.authormanagement.api.AuthorLendingView;
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
            "WHERE a.name.name LIKE :name% ")
    List<Author> searchByAuthorNameLike(@Param("name") String name);

    @Override
    @Query("SELECT new pt.psoft.g1.psoftg1.authormanagement.api.AuthorLendingView(a.name.name, COUNT(l.pk)) " +
            "FROM Book b " +
            "JOIN b.authors a " +
            "JOIN Lending l ON l.book.pk = b.pk " +
            "GROUP BY a.name " +
            "ORDER BY COUNT(l) DESC")
    Page<AuthorLendingView> findTopAuthorByLendings(Pageable pageable);

}
