package CDC.consumer;

import auth_users.users.api.UserRabbitMQController;
import auth_users.users.services.UserService;
import auth_users.users.api.UserAMQP;
import org.springframework.amqp.core.MessageProperties;
import org.junit.jupiter.api.Test;
import au.com.dius.pact.core.model.messaging.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import au.com.dius.pact.core.model.Pact;
import au.com.dius.pact.core.model.*;



import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.NONE
        ,classes = {UserRabbitMQController.class, UserService.class}
)
public class UsersCDCConsumerIT {

    @MockBean
    private UserService userService;

    @Autowired
    private UserRabbitMQController listener;

    @Test
    void testUserCreatedMessageProcessing() throws Exception {

        File pactFile = new File("target/pacts/user_created-consumer-user_event-producer.json");
        PactReader pactReader = DefaultPactReader.INSTANCE;
        Pact pact = pactReader.loadPact(pactFile);

        List<Message> messagesGeneratedByPact = pact.asMessagePact().get().getMessages();

        for (Message messageGeneratedByPact : messagesGeneratedByPact) {
            String jsonReceived = messageGeneratedByPact.contentsAsString();

            MessageProperties messageProperties = new MessageProperties();
            messageProperties.setContentType("application/json");

            org.springframework.amqp.core.Message messageToSend =
                    new org.springframework.amqp.core.Message(jsonReceived.getBytes(StandardCharsets.UTF_8), messageProperties);

            assertDoesNotThrow(() -> listener.receiveBookCreatedMsg(messageToSend));

            verify(userService, times(1)).createFromAMQP(org.mockito.ArgumentMatchers.any(UserAMQP.class));
        }
    }
}
