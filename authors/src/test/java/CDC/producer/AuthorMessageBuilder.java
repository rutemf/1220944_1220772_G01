package CDC.producer;

import authors.authors.api.AuthorsViewAMQP;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

public class AuthorMessageBuilder {

    private ObjectMapper mapper = new ObjectMapper();
    private AuthorsViewAMQP authorsViewAMQP;

    public AuthorMessageBuilder withAuthor(AuthorsViewAMQP authorsViewAMQP) {
        this.authorsViewAMQP = authorsViewAMQP;
        return this;
    }

    public Message<String> build() throws JsonProcessingException {
        return MessageBuilder.withPayload(this.mapper.writeValueAsString(this.authorsViewAMQP))
                .setHeader("Content-Type", "application/json; charset=utf-8").build();
    }
}
