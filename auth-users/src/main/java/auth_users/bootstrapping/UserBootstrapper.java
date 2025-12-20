package auth_users.bootstrapping;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import auth_users.users.model.Librarian;
import auth_users.users.model.Reader;
import auth_users.users.model.User;
import auth_users.users.repositories.UserRepository;

@Component
@RequiredArgsConstructor
@Profile("bootstrap")
@Order(1)
public class UserBootstrapper implements CommandLineRunner {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public void run(final String... args) {
        createReaders();
        createLibrarian();
    }

    private void createReaders() {
        if (userRepository.findByUsername("rute@gmail.com").isEmpty()) {
            final Reader rute = Reader.newReader("rute@gmail.com", "Rute!123", "Rute Ferreira");
            userRepository.save(rute);
        }

        if (userRepository.findByUsername("pedro@gmail.com").isEmpty()) {
            final Reader pedro = Reader.newReader("pedro@gmail.com", "Pedro!123", "Pedro Tabau");
            userRepository.save(pedro);
        }

        if (userRepository.findByUsername("marcelo@gmail.com").isEmpty()) {
            final Reader marcelo = Reader.newReader("marcelo@gmail.com", "Marcelo!123", "Marcelo Sousa");
            userRepository.save(marcelo);
        }
    }

    private void createLibrarian() {
        if (userRepository.findByUsername("maria@gmail.com").isEmpty()) {
            final User maria = Librarian.newLibrarian("maria@gmail.com", "Maria!123", "Maria Roberta");
            userRepository.save(maria);
        }

        if (userRepository.findByUsername("miguel@gmail.com").isEmpty()) {
            final User miguel = Librarian.newLibrarian("miguel@gmail.com", "Miguel!123", "Miguel Angelo");
            userRepository.save(miguel);
        }
    }
}
