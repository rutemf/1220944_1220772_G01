package authors.authors.infrastructures.publishers.impl;

import authors.authors.api.AuthorsViewAMQP;
import authors.authors.api.AuthorsViewAMQPMapper;
import authors.authors.model.Author;
import authors.authors.publishers.AuthorEventsPublisher;
import authors.shared.model.AuthorEvents;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorEventsRabbitmqPublisherImpl implements AuthorEventsPublisher {

    @Autowired
    private RabbitTemplate template;
    @Autowired
    private DirectExchange direct;
    @Autowired
    private final AuthorsViewAMQPMapper authorsViewMapper;

    private int count = 0;

    @Override
    public AuthorsViewAMQP sendAuthorCreated(Author author) {
        return sendAuthorEvent(author, 1L, AuthorEvents.AUTHOR_CREATED);    }

    @Override
    public AuthorsViewAMQP sendAuthorUpdated(Author author, Long currentVersion) {
        return sendAuthorEvent(author, currentVersion, AuthorEvents.AUTHOR_UPDATED);
    }

    @Override
    public AuthorsViewAMQP sendAuthorDeleted(Author author, Long currentVersion) {
        return sendAuthorEvent(author, currentVersion, AuthorEvents.AUTHOR_DELETED);
    }

    private AuthorsViewAMQP sendAuthorEvent(Author author, Long currentVersion, String authorEventType) {

        System.out.println("Send Author event to AMQP Broker: " + author.getBio());

        try {
            AuthorsViewAMQP authorViewAMQP = authorsViewMapper.toAuthorsViewAMQP(author);
            authorViewAMQP.setVersion(currentVersion);

            ObjectMapper objectMapper = new ObjectMapper();
            String bookViewAMQPinString = objectMapper.writeValueAsString(authorViewAMQP);

            this.template.convertAndSend(direct.getName(), authorEventType, bookViewAMQPinString);

            return authorViewAMQP;
        }
        catch( Exception ex ) {
            System.out.println(" [x] Exception sending author event: '" + ex.getMessage() + "'");

            return null;
        }
    }
}