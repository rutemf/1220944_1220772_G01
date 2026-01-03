package CDC.consumer;


import au.com.dius.pact.core.model.*;

import au.com.dius.pact.core.model.messaging.Message;

import books.books.api.BookRabbitmqController;
import books.books.api.BookViewAMQP;
import books.books.services.BookService;
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
,classes = {BookRabbitmqController.class, BookService.class}
)
public class BooksCDCConsumerIT {

    @MockBean
    BookService bookService;

    @Autowired
    BookRabbitmqController listener;

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
                "target/pacts/book_created-consumer-book_event-producer.json",
                message -> {
                    listener.receiveBookCreatedMsg(message);
                    verify(bookService, times(1)).create(any(BookViewAMQP.class));
                }
        );
    }

    @Test
    void testBookUpdatedMessage() throws Exception {
        processPactMessages(
                "target/pacts/book_updated-consumer-book_event-producer.json",
                message -> {
                    listener.receiveBookUpdated(message);
                    verify(bookService, times(1)).update(any(BookViewAMQP.class));
                }
        );
    }

}