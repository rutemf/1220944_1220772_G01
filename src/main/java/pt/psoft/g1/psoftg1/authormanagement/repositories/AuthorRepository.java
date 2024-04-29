package pt.psoft.g1.psoftg1.authormanagement.repositories;
import pt.psoft.g1.psoftg1.usermanagement.model.Name;
import pt.psoft.g1.psoftg1.authormanagement.model.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository {
    List<Author> findByName(Name name);

    Author save(Author author);
}
