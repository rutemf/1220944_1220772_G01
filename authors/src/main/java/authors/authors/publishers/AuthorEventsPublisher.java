package authors.authors.publishers;

import authors.authors.api.AuthorsViewAMQP;
import authors.authors.model.Author;

public interface AuthorEventsPublisher {

    AuthorsViewAMQP sendAuthorCreated(Author author);

    AuthorsViewAMQP sendAuthorUpdated(Author author, Long currentVersion);

    AuthorsViewAMQP sendAuthorDeleted(Author author, Long currentVersion);

}
