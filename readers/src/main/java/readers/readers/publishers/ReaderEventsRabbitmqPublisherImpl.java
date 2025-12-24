package readers.readers.publishers;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import readers.readers.services.CreateReaderRequest;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ReaderEventsRabbitmqPublisherImpl implements ReaderEventsPublisher {

    @Autowired
    private final RabbitTemplate template;

    @Autowired
    private final DirectExchange direct;

    @Override
    public void sendReaderCreated(CreateReaderRequest request, String readerId) {
        System.out.println("Enviando evento de criação de utilizador para: " + request.getEmail());

        try {
            Map<String, Object> message = new HashMap<>();
            message.put("readerId", readerId);
            message.put("username", request.getEmail());
            message.put("password", request.getPassword());
            message.put("fullName", request.getFullName());
            message.put("role", "READER");

            this.template.convertAndSend(direct.getName(), "READER_CREATED", message);

        } catch (Exception ex) {
            System.out.println(" [x] Erro ao publicar no RabbitMQ: " + ex.getMessage());
        }
    }
}
