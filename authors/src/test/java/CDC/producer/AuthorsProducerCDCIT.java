package CDC.producer;

import au.com.dius.pact.core.model.Interaction;
import au.com.dius.pact.core.model.Pact;
import au.com.dius.pact.provider.MessageAndMetadata;
import au.com.dius.pact.provider.PactVerifyProvider;
import au.com.dius.pact.provider.junit5.MessageTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.loader.PactFolder;
import authors.authors.api.AuthorsViewAMQP;
import authors.authors.api.AuthorsViewAMQPMapperImpl;
import authors.authors.infrastructures.publishers.impl.AuthorEventsRabbitmqPublisherImpl;
import authors.authors.model.Author;
import authors.authors.publishers.AuthorEventsPublisher;
import authors.authors.services.AuthorService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.messaging.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.HashMap;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.NONE
        ,classes = {AuthorEventsRabbitmqPublisherImpl.class, AuthorService.class, AuthorsViewAMQPMapperImpl.class}
        , properties = {"stubrunner.amqp.mockConnection=true", "spring.profiles.active=test"}
)
@Provider("author_event-producer")
@PactFolder("target/pacts")
public class AuthorsProducerCDCIT {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorsProducerCDCIT.class);

    @Autowired
    AuthorEventsPublisher authorEventsPublisher;

    @Autowired
    AuthorsViewAMQPMapperImpl authorsViewAMQPMapper;

    @MockBean
    RabbitTemplate template;

    @MockBean
    DirectExchange direct;

    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider.class)
    void testTemplate(Pact pact, Interaction interaction, PactVerificationContext context) {
        context.verifyInteraction();
    }

    @BeforeEach
    void before(PactVerificationContext context) {
        context.setTarget(new MessageTestTarget());
    }

    @PactVerifyProvider("a author created event")
    public MessageAndMetadata authorCreated() throws JsonProcessingException {

        Author author = new Author(
                "José Saramago",
                "short bio",
                "http://photo.uri/saramago.jpg"
        );

        AuthorsViewAMQP authorViewAMQP =
                authorEventsPublisher.sendAuthorCreated(author);

        Message<String> message =
                new AuthorMessageBuilder()
                        .withAuthor(authorViewAMQP)
                        .build();

        return generateMessageAndMetadata(message);
    }

    @PactVerifyProvider("a author updated event")
    public MessageAndMetadata authorUpdated() throws JsonProcessingException {

        Author author = new Author(
                "José Saramago",
                "short bio",
                "http://photo.uri/saramago.jpg"
        );

        AuthorsViewAMQP authorViewAMQP =
                authorEventsPublisher.sendAuthorUpdated(author, 1L);

        Message<String> message =
                new AuthorMessageBuilder()
                        .withAuthor(authorViewAMQP)
                        .build();

        return generateMessageAndMetadata(message);
    }


    private MessageAndMetadata generateMessageAndMetadata(org.springframework.messaging.Message<String> message) {
        HashMap<String, Object> metadata = new HashMap<String, Object>();
        message.getHeaders().forEach((k, v) -> metadata.put(k, v));

        return new MessageAndMetadata(message.getPayload().getBytes(), metadata);
    }

}
