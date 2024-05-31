package pt.psoft.g1.psoftg1.authormanagement.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import pt.psoft.g1.psoftg1.authormanagement.model.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository {

    Optional<Author> findByAuthorNumber(Long authorNumber);
    List<Author> searchByAuthorNameLike(@Param("name") String name);
    List<Author> findByName(String name);
    Author save(Author author);
    Iterable<Author> findAll();
    Page<Author> findTopAuthorByLendings (Pageable pageableRules);

}
