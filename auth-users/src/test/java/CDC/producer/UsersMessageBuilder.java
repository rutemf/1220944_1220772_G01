package CDC.producer;

import auth_users.users.api.UserAMQP;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

public class UsersMessageBuilder {
    private ObjectMapper mapper = new ObjectMapper();
    private UserAMQP userAMQP;

    public UsersMessageBuilder withBook(UserAMQP userAMQP) {
        this.userAMQP = userAMQP;
        return this;
    }

    public Message<String> build() throws JsonProcessingException {
        return MessageBuilder.withPayload(this.mapper.writeValueAsString(this.userAMQP))
                .setHeader("Content-Type", "application/json; charset=utf-8").build();
    }
}
