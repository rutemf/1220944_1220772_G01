package pt.psoft.g1.psoftg1.authormanagement.repositories;
import org.springframework.data.repository.query.Param;
import pt.psoft.g1.psoftg1.usermanagement.model.Name;
import pt.psoft.g1.psoftg1.authormanagement.model.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository {

    Optional<Author> findByAuthorNumber(Long authorNumber); //todo: até que ponto faz sentido isto aqui estar para o meu wp?
    List<Author> searchByName(@Param("name") String name);
    List<Author> searchByAuthorNumber(@Param("authorNumber") Long authorNumber);

    Author save(Author author);
}
