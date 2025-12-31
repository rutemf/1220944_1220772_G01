package genres.genres.infrastructure.publishers.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import genres.genres.api.GenreViewAMQP;
import genres.genres.api.GenreViewAMQPMapper;
import genres.genres.model.Genre;
import genres.genres.publishers.GenreEventsPublisher;
import genres.shared.model.GenreEvents;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GenreEventsRabbitmqPublisherImpl  implements GenreEventsPublisher {

    @Autowired
    RabbitTemplate rabbitTemplate;
    @Autowired
    private DirectExchange direct;
    @Autowired
    private GenreViewAMQPMapper genreViewAMQPMapper;


    @Override
    public GenreViewAMQP sendGenreCreated(Genre genre) {
        return sendGenreEvent(genre, 1L, GenreEvents.GENRE_CREATED);
    }

    @Override
    public GenreViewAMQP sendGenreUpdated(Genre genre, Long currentVersion) {
        return sendGenreEvent(genre, currentVersion, GenreEvents.GENRE_UPDATED);
    }

    @Override
    public GenreViewAMQP sendGenreDeleted(Genre genre, Long currentVersion) {
        return sendGenreEvent(genre, currentVersion, GenreEvents.GENRE_DELETED);
    }

    private GenreViewAMQP sendGenreEvent(Genre genre, Long currentVersion, String genreEventType) {

        System.out.println("Send Genre event to AMQP Broker: " + genre.getGenre());

        try {
            GenreViewAMQP genreViewAMQP = genreViewAMQPMapper.toGenreViewAMQP(genre);
            genreViewAMQP.setVersion(currentVersion);

            ObjectMapper objectMapper = new ObjectMapper();
            String genreViewAMQPinString = objectMapper.writeValueAsString(genreViewAMQP);

            this.rabbitTemplate.convertAndSend(direct.getName(), genreEventType, genreViewAMQPinString);

            return genreViewAMQP;
        }
        catch( Exception ex ) {
            System.out.println(" [x] Exception sending genre event: '" + ex.getMessage() + "'");

            return null;
        }
    }
}
