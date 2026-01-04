package CDC.consumer;

import au.com.dius.pact.consumer.MessagePactBuilder;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.junit5.PactConsumerTest;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.consumer.junit5.ProviderType;
import au.com.dius.pact.core.model.PactSpecVersion;
import au.com.dius.pact.core.model.V4Interaction;
import au.com.dius.pact.core.model.V4Pact;
import au.com.dius.pact.core.model.annotations.Pact;
import authors.authors.api.AuthorRabbitmqController;
import authors.authors.api.AuthorsViewAMQP;
import authors.authors.services.AuthorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(PactConsumerTestExt.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.NONE
        , classes = {AuthorRabbitmqController.class, AuthorService.class}
)
@PactConsumerTest
@PactTestFor(providerName = "author_event-producer", providerType = ProviderType.ASYNCH, pactVersion = PactSpecVersion.V4)
public class AuthorsCDCDefinitionTest {

    @MockBean
    private AuthorService authorService;

    @Autowired
    private AuthorRabbitmqController listener;

    @Pact(consumer = "author_created-consumer")
    V4Pact createAuthorCreatedPact(MessagePactBuilder builder) {

        PactDslJsonBody body = new PactDslJsonBody()
                .stringType("name", "J. K. Rowling")
                .stringType("bio", "British author")
                .stringMatcher("version", "[0-9]+", "1");

        Map<String, Object> metadata = new HashMap<>();
        metadata.put("Content-Type", "application/json");

        return builder
                .expectsToReceive("a author created event")
                .withMetadata(metadata)
                .withContent(body)
                .toPact();
    }

    @Pact(consumer = "author_updated-consumer")
    V4Pact createAuthorUpdatedPact(MessagePactBuilder builder) {

        PactDslJsonBody body = new PactDslJsonBody()
                .stringType("name", "J. K. Rowling")
                .stringType("bio", "Updated bio")
                .stringMatcher("version", "[0-9]+", "2");

        Map<String, Object> metadata = new HashMap<>();
        metadata.put("Content-Type", "application/json");

        return builder
                .expectsToReceive("a author updated event")
                .withMetadata(metadata)
                .withContent(body)
                .toPact();
    }

    @Test
    @PactTestFor(pactMethod = "createAuthorCreatedPact")
    void testAuthorCreated(List<V4Interaction.AsynchronousMessage> messages) {

        String jsonReceived = messages.get(0).contentsAsString();

        MessageProperties props = new MessageProperties();
        props.setContentType("application/json");

        Message message =
                new Message(jsonReceived.getBytes(StandardCharsets.UTF_8), props);

        assertDoesNotThrow(() ->
                listener.receiveAuthorCreatedMsg(message)
        );

        verify(authorService, times(1))
                .create(any(AuthorsViewAMQP.class));
    }

    @Test
    @PactTestFor(pactMethod = "createAuthorUpdatedPact")
    void testAuthorUpdated(List<V4Interaction.AsynchronousMessage> messages) {

        String jsonReceived = messages.get(0).contentsAsString();

        MessageProperties props = new MessageProperties();
        props.setContentType("application/json");

        Message message =
                new Message(jsonReceived.getBytes(StandardCharsets.UTF_8), props);

        assertDoesNotThrow(() ->
                listener.receiveAuthorUpdated(message)
        );

        verify(authorService, times(1))
                .update(any(AuthorsViewAMQP.class));
    }

}
