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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.messaging.Message;
import readers.readers.api.ReaderViewAMQPMapperImpl;
import readers.readers.publishers.ReaderEventsPublisher;
import readers.readers.publishers.ReaderEventsRabbitmqPublisherImpl;
import readers.readers.services.ReaderViewAMQP;
import readers.shared.model.ReaderEvents;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.NONE
        ,classes = {ReaderEventsRabbitmqPublisherImpl.class, ReaderEvents.class, ReaderViewAMQPMapperImpl.class}
        , properties = {"stubrunner.amqp.mockConnection=true", "spring.profiles.active=test"}
)
@Provider("reader_event-producer")
@PactFolder("target/pacts")
public class ReadersProducerCDCIT {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReadersProducerCDCIT.class);

    @Autowired
    ReaderEventsPublisher readerEventsPublisher;

    @Autowired
    ReaderViewAMQPMapperImpl readerViewAMQPMapper;

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
    @PactVerifyProvider("a reader created event")
    public MessageAndMetadata readerCreated() throws JsonProcessingException {

        List<String> interests = new ArrayList<>();
        interests.add("Fiction");

        ReaderViewAMQP readerViewAMQP = new ReaderViewAMQP(
                "6",
                "1990-01-01",
                "123456789",
                true,
                interests
        );

        Message<String> message = new ReaderMessageBuilder().withReader(readerViewAMQP).build();

        return generateMessageAndMetadata(message);
    }


    private MessageAndMetadata generateMessageAndMetadata(Message<String> message) {
        HashMap<String, Object> metadata = new HashMap<String, Object>();
        message.getHeaders().forEach((k, v) -> metadata.put(k, v));

        return new MessageAndMetadata(message.getPayload().getBytes(), metadata);
    }


}
