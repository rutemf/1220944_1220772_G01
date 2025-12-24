package readers.bootstrapping;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import readers.genres.model.Genre;
import readers.genres.repositories.GenreRepository;
import readers.readers.model.ReaderDetails;
import readers.readers.repositories.ReaderRepository;
import readers.shared.services.ForbiddenNameService;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Profile("bootstrap")
@Order(1)
public class Bootstrapper implements CommandLineRunner {

    private final GenreRepository genreRepository;
    private final ForbiddenNameService forbiddenNameService;
    private final ReaderRepository readerRepository;

    @Override
    @Transactional
    public void run(final String... args) {
        createGenres();
        createReaders();
        loadForbiddenNames();
    }

    private void createGenres() {
        if (genreRepository.findByString("Fantasia").isEmpty()) {
            final Genre g1 = new Genre("Fantasia");
            genreRepository.save(g1);
        }
        if (genreRepository.findByString("Informação").isEmpty()) {
            final Genre g2 = new Genre("Informação");
            genreRepository.save(g2);
        }
        if (genreRepository.findByString("Romance").isEmpty()) {
            final Genre g3 = new Genre("Romance");
            genreRepository.save(g3);
        }
        if (genreRepository.findByString("Infantil").isEmpty()) {
            final Genre g4 = new Genre("Infantil");
            genreRepository.save(g4);
        }
        if (genreRepository.findByString("Thriller").isEmpty()) {
            final Genre g5 = new Genre("Thriller");
            genreRepository.save(g5);
        }
    }

    private void createReaders() {
        List<Genre> allGenres = new ArrayList<>();

        Genre fantasia = genreRepository.findByString("Fantasia").orElse(null);
        Genre romance = genreRepository.findByString("Romance").orElse(null);

        allGenres.add(fantasia);
        allGenres.add(romance);

        if (readerRepository.findByReaderNumber("2025/1").isEmpty()) {
            final ReaderDetails r1 = new ReaderDetails(1, "1", "1990-05-20", "912345678", true, true, false, allGenres);
            readerRepository.save(r1);
        }
    }

    private void loadForbiddenNames() {
        String fileName = "forbiddenNames.txt";
        forbiddenNameService.loadDataFromFile(fileName);
    }
}