package pt.psoft.g1.psoftg1.bootstrapping;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pt.psoft.g1.psoftg1.usermanagement.model.Librarian;
import pt.psoft.g1.psoftg1.usermanagement.model.Reader;
import pt.psoft.g1.psoftg1.usermanagement.model.User;
import pt.psoft.g1.psoftg1.usermanagement.repositories.UserRepository;

@Component
@RequiredArgsConstructor
@Order(1)
public class UserBootstrapper implements CommandLineRunner {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public void run(final String... args)  {
        createReaders();
        createLibrarian();
    }

    private void createReaders() {

        if (userRepository.findByUsername("miguel@gmail.com").isEmpty()) {
            final Reader manuel = Reader.newReader("miguel@gmail.com", "Miguel123!", "Miguel Cardoso");
            userRepository.save(manuel);
        }

        if (userRepository.findByUsername("rute@gmail.com").isEmpty()) {
            final Reader joao = Reader.newReader("rute@gmail.com", "Rute!123", "Rute Ferreira");
            userRepository.save(joao);
        }

        if (userRepository.findByUsername("pedro@gmail.com").isEmpty()) {
            final Reader pedro = Reader.newReader("pedro@gmail.com", "Pedro!123", "Pedro Tabau");
            userRepository.save(pedro);
        }

        if (userRepository.findByUsername("catarina@gmail.com").isEmpty()) {
            final Reader catarina = Reader.newReader("catarina@gmail.com", "Catarina!123", "Catarina Martins");
            userRepository.save(catarina);
        }

        if (userRepository.findByUsername("marcelo@gmail.com").isEmpty()) {
            final Reader marcelo = Reader.newReader("marcelo@gmail.com", "Marcelo!123", "Marcelo Sousa");
            userRepository.save(marcelo);
        }

    }

    private void createLibrarian(){

        if (userRepository.findByUsername("maria@gmail.com").isEmpty()) {
            final User maria = Librarian.newLibrarian("maria@gmail.com", "Maria!123", "Maria Roberta");
            userRepository.save(maria);
        }

    }
}
