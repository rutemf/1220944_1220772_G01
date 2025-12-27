package auth_users.users.api;

import auth_users.configuration.RabbitmqClientConfig;
import auth_users.users.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.core.Message;

import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class UserRabbitMQController {

    @Autowired
    private final UserService userService;

    @RabbitListener(queues = "auth.users.reader.created.queue")
    public void receiveBookCreatedMsg(Message msg) {

        try {
            String jsonReceived = new String(msg.getBody(), StandardCharsets.UTF_8);

            ObjectMapper objectMapper = new ObjectMapper();
            UserAMQP userAMQP = objectMapper.readValue(jsonReceived, UserAMQP.class);
            System.out.println(userAMQP);

            System.out.println(" [x] Received Reader Created by AMQP: " + msg + ".");
            try {
                userService.createFromAMQP(userAMQP);
                System.out.println(" [x] Success: User inserted/processed.");
            } catch (Exception e) {
                System.err.println(" [!] Error in userService.createFromAMQP: " + e.getMessage());
                e.printStackTrace();
            }
        }
        catch(Exception ex) {
            System.out.println(" [x] Exception receiving reader event from AMQP: '" + ex.getMessage() + "'");
        }
    }
}
