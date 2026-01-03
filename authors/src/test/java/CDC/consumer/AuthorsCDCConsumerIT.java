package CDC.consumer;

import au.com.dius.pact.core.model.DefaultPactReader;
import au.com.dius.pact.core.model.Pact;
import au.com.dius.pact.core.model.PactReader;
import au.com.dius.pact.core.model.messaging.Message;
import authors.authors.api.AuthorRabbitmqController;
import authors.authors.api.AuthorsViewAMQP;
import authors.authors.services.AuthorService;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.NONE
        ,classes = {AuthorRabbitmqController.class, AuthorService.class}
)
public class AuthorsCDCConsumerIT {

    @MockBean
    AuthorService authorService;

    @Autowired
    AuthorRabbitmqController listener;

    private void processPactMessages(String pactFilePath, MessageHandler handler) throws Exception {
        File pactFile = new File(pactFilePath);
        PactReader pactReader = DefaultPactReader.INSTANCE;

        Pact pact = pactReader.loadPact(pactFile);

        List<Message> messagesGeneratedByPact = pact.asMessagePact().get().getMessages();
        for (Message message : messagesGeneratedByPact) {
            String json = message.contentsAsString();

            MessageProperties props = new MessageProperties();
            props.setContentType("application/json");

            org.springframework.amqp.core.Message springMsg =
                    new org.springframework.amqp.core.Message(json.getBytes(StandardCharsets.UTF_8), props);

            assertDoesNotThrow(() -> handler.handle(springMsg));
        }
    }

    @FunctionalInterface
    interface MessageHandler {
        void handle(org.springframework.amqp.core.Message message) throws Exception;
    }

    @Test
    void testAuthorCreatedMessage() throws Exception {
        processPactMessages(
                "target/pacts/author_created-consumer-author_event-producer.json",
                message -> {
                    listener.receiveAuthorCreatedMsg(message);
                    verify(authorService, times(1))
                            .create(any(AuthorsViewAMQP.class));
                }
        );
    }

    @Test
    void testAuthorUpdatedMessage() throws Exception {
        processPactMessages(
                "target/pacts/author_updated-consumer-author_event-producer.json",
                message -> {
                    listener.receiveAuthorUpdated(message);
                    verify(authorService, times(1))
                            .update(any(AuthorsViewAMQP.class));
                }
        );
    }

}
