package authors.authors.services;

import authors.authors.api.AuthorsViewAMQP;
import authors.authors.model.Author;
import authors.shared.services.Page;

import java.util.List;

public interface AuthorService {

    Author create(CreateAuthorRequest request); // REST request

    Author create(AuthorsViewAMQP authorViewAMQP); // AMQP request

    Author findByAuthorNumber(Long authorNumber);

    Author findByName(String name);

    Author update(UpdateAuthorRequest request, Long currentVersion);

    Author update(AuthorsViewAMQP authorViewAMQP);

    List<Author> searchAuthors(Page page, SearchAuthorsQuery query);

    Author removeAuthorPhoto(Long authorNumber, long desiredVersion);
}
