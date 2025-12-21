package books.authors.repositories;

import books.authors.model.Author;
import pt.psoft.g1.psoftg1.authormanagement.api.AuthorLendingView;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository {

    Optional<Author> findByAuthorNumber(Long authorNumber);

    List<Author> searchByNameNameStartsWith(String name);

    List<Author> searchByNameName(String name);

    Author save(Author author);

    Iterable<Author> findAll();

    void delete(Author author);

    List<Author> findCoAuthorsByAuthorNumber(Long authorNumber);

}
