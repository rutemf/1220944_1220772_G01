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
import auth_users.users.api.UserAMQP;
import org.springframework.amqp.core.Message;
import auth_users.users.api.UserRabbitMQController;
import auth_users.users.services.UserService;
import org.springframework.amqp.core.MessageProperties;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
        , classes = {UserRabbitMQController.class, UserService.class}
)
@PactConsumerTest
@PactTestFor(providerName = "user_event-producer", providerType = ProviderType.ASYNCH, pactVersion = PactSpecVersion.V4)
public class UsersCDCDefinitionTest {

    @MockBean
    private UserService userService;

    @Autowired
    private UserRabbitMQController listener;

    @Pact(consumer = "user_created-consumer")
    V4Pact createUserCreatedPact(MessagePactBuilder builder) {
        PactDslJsonBody body = new PactDslJsonBody();
        body.stringType("id", "123");
        body.stringType("username", "jose_saramago");
        body.stringType("email", "jose@example.com");
        body.stringType("role", "READER");

        Map<String, Object> metadata = new HashMap<>();
        metadata.put("Content-Type", "application/json");

        return builder.expectsToReceive("a user created event")
                .withMetadata(metadata)
                .withContent(body)
                .toPact();
    }

    @Test
    @PactTestFor(pactMethod = "createUserCreatedPact")
    void testUserCreated(List<V4Interaction.AsynchronousMessage> messages) throws Exception {
        String jsonReceived = messages.get(0).contentsAsString();

        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType("application/json");
        Message message = new Message(jsonReceived.getBytes(StandardCharsets.UTF_8), messageProperties);

        assertDoesNotThrow(() -> listener.receiveBookCreatedMsg(message));

        verify(userService, times(1)).createFromAMQP(any(UserAMQP.class));
    }
}
