package books.authors.repositories;

import books.authors.model.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository {

    Optional<Author> findByAuthorNumber(Long authorNumber);
    List<Author> searchByNameName(String name);
    Author save(Author author);
    Optional<Author> findByName(String name);

}