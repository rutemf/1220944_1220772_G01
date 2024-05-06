package pt.psoft.g1.psoftg1.authormanagement.services;

import jakarta.validation.Valid;
import pt.psoft.g1.psoftg1.authormanagement.model.Author;

import java.util.Optional;

public interface AuthorService {

    Iterable<Author> findAll();

    Optional<Author> findByAuthorNumber(Long authorNumber);

    Author create(CreateAuthorRequest resource);

    Author partialUpdate(Long authornumber, UpdateAuthorRequest resource, long desiredVersion);

}
