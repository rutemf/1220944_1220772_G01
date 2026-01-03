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
import com.fasterxml.jackson.core.JsonProcessingException;
import genres.genres.api.GenreViewAMQP;
import genres.genres.api.GenreViewAMQPMapperImpl;
import genres.genres.infrastructure.publishers.impl.GenreEventsRabbitmqPublisherImpl;
import genres.genres.model.Genre;
import genres.genres.publishers.GenreEventsPublisher;
import genres.genres.services.GenreService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.messaging.Message;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.HashMap;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.NONE
        ,classes = {GenreEventsRabbitmqPublisherImpl.class, GenreService.class, GenreViewAMQPMapperImpl.class}
        , properties = {"stubrunner.amqp.mockConnection=true", "spring.profiles.active=test"}
)
@Provider("genre_event-producer")
@PactFolder("target/pacts")
public class GenresProducerCDCIT {

    @Autowired
    GenreEventsPublisher genreEventsPublisher;

    @Autowired
    GenreViewAMQPMapperImpl genreViewAMQPMapper;

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

    @PactVerifyProvider("a genre created event")
    public MessageAndMetadata genreCreated() throws JsonProcessingException {
        Genre genre = new Genre("Infantil");

        GenreViewAMQP genreViewAMQP = new GenreViewAMQP();
        genreViewAMQP.setGenre(genre.getGenre());
        genreViewAMQP.setVersion(genre.getVersion());

        Message<String> message = new GenreMessageBuilder().withGenre(genreViewAMQP).build();

        return generateMessageAndMetadata(message);
    }

    @PactVerifyProvider("a genre updated event")
    public MessageAndMetadata genreUpdated() throws JsonProcessingException {
        Genre genre = new Genre("Infantil");
        ReflectionTestUtils.setField(genre, "version", 1L);

        genre.applyPatch(1L, "Infantil Atualizado");

        GenreViewAMQP genreViewAMQP = new GenreViewAMQP();
        genreViewAMQP.setGenre(genre.getGenre());
        genreViewAMQP.setVersion(genre.getVersion());

        Message<String> message = new GenreMessageBuilder().withGenre(genreViewAMQP).build();
        return generateMessageAndMetadata(message);
    }

    private MessageAndMetadata generateMessageAndMetadata(Message<String> message) {
        HashMap<String, Object> metadata = new HashMap<>();
        message.getHeaders().forEach((k, v) -> metadata.put(k, v));
        return new MessageAndMetadata(message.getPayload().getBytes(), metadata);
    }

}
