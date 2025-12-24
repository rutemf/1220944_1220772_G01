package readers.readers.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import readers.genres.model.Genre;
import readers.readers.model.ReaderDetails;
import readers.readers.publishers.ReaderEventsPublisher;
import readers.readers.repositories.ReaderRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReaderServiceImpl implements ReaderService {

    private final ReaderRepository repository;
    private final ReaderEventsPublisher publisher;

    @Override
    public ReaderDetails create(CreateReaderRequest request) {
        String generatedReaderId = UUID.randomUUID().toString();

        List<Genre> genres = new ArrayList<>();

        for (String genreName : request.getInterestList()) {
            Genre genre = new Genre(genreName);
            genres.add(genre);
        }

        int nextReaderNumber = (int) (repository.count() + 1);

        ReaderDetails readerDetails = new ReaderDetails(
                nextReaderNumber,
                generatedReaderId,
                request.getBirthDate(),
                request.getPhoneNumber(),
                request.getGdpr(),
                true,
                true,
                genres
        );

        ReaderDetails savedReader = repository.save(readerDetails);

        try {
            publisher.sendReaderCreated(request, generatedReaderId);
        } catch (Exception e) {
            System.err.println("Erro ao enviar para RabbitMQ: " + e.getMessage());
        }

        return savedReader;
    }
}
