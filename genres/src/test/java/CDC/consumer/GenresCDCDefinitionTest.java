package CDC.consumer;

import au.com.dius.pact.consumer.junit5.PactConsumerTest;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.consumer.junit5.ProviderType;
import au.com.dius.pact.core.model.PactSpecVersion;
import au.com.dius.pact.core.model.V4Interaction;
import au.com.dius.pact.core.model.V4Pact;
import au.com.dius.pact.consumer.MessagePactBuilder;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;

import au.com.dius.pact.core.model.annotations.Pact;
import genres.genres.api.GenreRabbitmqController;
import genres.genres.api.GenreViewAMQP;
import genres.genres.services.GenreService;
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
        , classes = {GenreRabbitmqController.class, GenreService.class}
)
@PactConsumerTest
@PactTestFor(providerName = "genre_event-producer", providerType = ProviderType.ASYNCH, pactVersion = PactSpecVersion.V4)
public class GenresCDCDefinitionTest {

    @MockBean
    GenreService genreService;

    @Autowired
    GenreRabbitmqController listener;

    @Pact(consumer = "genre_created-consumer")
    V4Pact createGenreCreatedPact(MessagePactBuilder builder) {
        PactDslJsonBody body = new PactDslJsonBody()
                .stringType("genre", "Infantil");

        Map<String, Object> metadata = new HashMap<>();
        metadata.put("Content-Type", "application/json");

        return builder.expectsToReceive("a genre created event")
                .withMetadata(metadata)
                .withContent(body)
                .toPact();
    }

    @Pact(consumer = "genre_updated-consumer")
    V4Pact createGenreUpdatedPact(MessagePactBuilder builder) {
        PactDslJsonBody body = new PactDslJsonBody()
                .stringType("genre", "Infantil")
                .stringMatcher("version", "[0-9]+", "1");

        Map<String, Object> metadata = new HashMap<>();
        metadata.put("Content-Type", "application/json");

        return builder.expectsToReceive("a genre updated event")
                .withMetadata(metadata)
                .withContent(body)
                .toPact();
    }

    @Test
    @PactTestFor(pactMethod = "createGenreCreatedPact")
    void testGenreCreated(List<V4Interaction.AsynchronousMessage> messages) throws Exception {
        String jsonReceived = messages.get(0).contentsAsString();

        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType("application/json");
        Message message = new Message(jsonReceived.getBytes(StandardCharsets.UTF_8), messageProperties);

        assertDoesNotThrow(() -> listener.receiveGenreCreatedMsg(message));

        verify(genreService, times(1)).create(any(GenreViewAMQP.class));
    }

    @Test
    @PactTestFor(pactMethod = "createGenreUpdatedPact")
    void testGenreUpdated(List<V4Interaction.AsynchronousMessage> messages) throws Exception {
        String jsonReceived = messages.get(0).contentsAsString();

        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType("application/json");
        Message message = new Message(jsonReceived.getBytes(StandardCharsets.UTF_8), messageProperties);

        assertDoesNotThrow(() -> listener.receiveGenreUpdated(message));

        verify(genreService, times(1)).update(any(GenreViewAMQP.class));
    }

}
