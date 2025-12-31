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
import books.authors.model.Author;
import books.books.api.BookViewAMQP;
import books.books.api.BookViewAMQPMapperImpl;
import books.books.infrastructure.publishers.impl.BookEventsRabbitmqPublisherImpl;
import books.books.model.Book;
import books.books.publishers.BookEventsPublisher;
import books.books.services.BookService;
import books.genres.model.Genre;
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

import java.util.ArrayList;
import java.util.HashMap;


import java.util.List;

@SpringBootTest(
webEnvironment = SpringBootTest.WebEnvironment.NONE
,classes = {BookEventsRabbitmqPublisherImpl.class, BookService.class, BookViewAMQPMapperImpl.class}
, properties = {"stubrunner.amqp.mockConnection=true", "spring.profiles.active=test"}
)
@Provider("book_event-producer")
@PactFolder("target/pacts")
public class BooksProducerCDCIT {
    private static final Logger LOGGER = LoggerFactory.getLogger(BooksProducerCDCIT.class);

    @Autowired
    BookEventsPublisher bookEventsPublisher;

    @Autowired
    BookViewAMQPMapperImpl bookViewAMQPMapper;

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

    @PactVerifyProvider("a book created event")
    public MessageAndMetadata bookCreated() throws JsonProcessingException {

        Genre genre = new Genre("Infantil");

        Author author = new Author("José Saramago", "shot bio", "");
        List<Author> authors = new ArrayList<>();
        authors.add(author);

        Book book = new Book(
                "6475803429671",
                "title",
                "description",
                genre,
                authors,
                "");

        BookViewAMQP bookViewAMQP = bookEventsPublisher.sendBookCreated(book);

        Message<String> message = new BookMessageBuilder().withBook(bookViewAMQP).build();

        return generateMessageAndMetadata(message);
    }

    @PactVerifyProvider("a book updated event")
    public MessageAndMetadata bookUpdated() throws JsonProcessingException {

        Genre genre = new Genre("Infantil");

        Author author = new Author("José Saramago", "shot bio", "");
        List<Author> authors = new ArrayList<>();
        authors.add(author);

        Book book = new Book(
                "6475803429671",
                "title",
                "description",
                genre,
                authors,
                "");

        BookViewAMQP bookViewAMQP = bookEventsPublisher.sendBookUpdated(book, 1L);

        Message<String> message = new BookMessageBuilder().withBook(bookViewAMQP).build();

        return generateMessageAndMetadata(message);
    }

    private MessageAndMetadata generateMessageAndMetadata(Message<String> message) {
        HashMap<String, Object> metadata = new HashMap<String, Object>();
        message.getHeaders().forEach((k, v) -> metadata.put(k, v));

        return new MessageAndMetadata(message.getPayload().getBytes(), metadata);
    }
}
