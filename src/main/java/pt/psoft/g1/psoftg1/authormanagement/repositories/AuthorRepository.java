package pt.psoft.g1.psoftg1;

import java.util.List;
import java.util.Optional;

import com.example.demo.Authormanagement.model.Author;

public interface AuthorRepository {

    Optional<Author> findByName(Name name);

    List<Author> findByName(Name name);

    Author save(Author author);
}
