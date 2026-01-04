package CDC.consumer;

import au.com.dius.pact.core.model.DefaultPactReader;
import au.com.dius.pact.core.model.Pact;
import au.com.dius.pact.core.model.PactReader;
import au.com.dius.pact.core.model.messaging.Message;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import readers.readers.api.ReaderRabbitmqController;
import readers.readers.services.ReaderService;
import readers.readers.services.ReaderViewAMQP;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.NONE
        ,classes = {ReaderRabbitmqController.class, ReaderService.class}
)
public class ReadersCDCConsumerIT {

    @MockBean
    ReaderService readerService;

    @Autowired
    ReaderRabbitmqController listener;

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
    void testBookCreatedMessage() throws Exception {
        processPactMessages(
                "target/pacts/reader_created-consumer-reader_event-producer.json",
                message -> {
                    listener.receiveReaderCreatedMsg(message);
                    verify(readerService, times(1)).create(any(ReaderViewAMQP.class));
                }
        );
    }

}
