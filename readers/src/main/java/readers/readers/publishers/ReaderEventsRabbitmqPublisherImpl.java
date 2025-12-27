package readers.readers.publishers;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import readers.readers.services.CreateReaderRequest;
import readers.shared.model.ReaderEvents;

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
            ObjectMapper objectMapper = new ObjectMapper();
            com.fasterxml.jackson.databind.node.ObjectNode jsonNode = objectMapper.valueToTree(request);
            jsonNode.put("role", "READER");
            jsonNode.put("readerId", readerId);
            jsonNode.put("username", request.getEmail());

            String readerAMQPinString = objectMapper.writeValueAsString(jsonNode);

            this.template.convertAndSend(direct.getName(), ReaderEvents.READER_CREATED, readerAMQPinString);

        } catch (Exception ex) {
            System.out.println(" [x] Erro ao publicar no RabbitMQ: " + ex.getMessage());
        }
    }
}
