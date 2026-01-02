package authors.bootstrapping;

import authors.authors.model.Author;
import authors.authors.repositories.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Profile("bootstrap")
@Order(1)
public class Bootstrapper implements CommandLineRunner {

    private final AuthorRepository authorRepository;

    @Override
    @Transactional
    public void run(final String... args) {
        createAuthors();
    }

    private void createAuthors() {
        if (authorRepository.findByAuthorNumber(1L).isEmpty()) {
            final Author rute = new Author("Rute Maia", "Vila do Conde", null);
            authorRepository.save(rute);
        }

        if (authorRepository.findByAuthorNumber(2L).isEmpty()) {
            final Author miguel = new Author("Miguel Cardoso", "Maia", null);
            authorRepository.save(miguel);
        }
    }
}
