package books.authors.infrastructure.repositories.impl;

import books.authors.model.Author;
import books.authors.repositories.AuthorRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface SpringDataAuthorRepository extends AuthorRepository, CrudRepository<Author, Long> {
    @Override
    Optional<Author> findByAuthorNumber(Long authorNumber);

    @Query("SELECT DISTINCT coAuthor FROM Book b " + "JOIN b.authors coAuthor "
            + "WHERE b IN (SELECT b FROM Book b JOIN b.authors a WHERE a.authorNumber = :authorNumber) "
            + "AND coAuthor.authorNumber <> :authorNumber")
    List<Author> findCoAuthorsByAuthorNumber(Long authorNumber);
}
