package pt.psoft.g1.psoftg1.bootstrapping;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pt.psoft.g1.psoftg1.readermanagement.model.ReaderDetails;
import pt.psoft.g1.psoftg1.readermanagement.repositories.ReaderRepository;
import pt.psoft.g1.psoftg1.usermanagement.model.Librarian;
import pt.psoft.g1.psoftg1.usermanagement.model.Reader;
import pt.psoft.g1.psoftg1.usermanagement.model.User;
import pt.psoft.g1.psoftg1.usermanagement.repositories.UserRepository;

import java.time.LocalDate;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Profile("bootstrap")
public class UserBootstrapper implements CommandLineRunner {

    private final UserRepository userRepository;
    private final ReaderRepository readerRepository;

    @Override
    @Transactional
    public void run(final String... args)  {
        createUsers();
        createReaderDetails();

    }
    private void createUsers() {
        // Manuel
        if (userRepository.findByUsername("manuel@gmail.com").isEmpty()) {
            final User manuel = Reader.newReader("manuel@gmail.com", "Manuelino123!", "Manuel Sarapinto das Coives");
            userRepository.save(manuel);
        }
        // João
        if (userRepository.findByUsername("joao@gmail.com").isEmpty()) {
            final User joao = Reader.newReader("joao@gmail.com", "Joaoratao!123", "Joao Ratao");
            userRepository.save(joao);
        }
        // Pedro
        if (userRepository.findByUsername("pedro@gmail.com").isEmpty()) {
            final User pedro = Reader.newReader("pedro@gmail.com", "Pedrodascenas!123", "Pedro Das Cenas");
            userRepository.save(pedro);
        }
        // Maria
        if (userRepository.findByUsername("maria@gmail.com").isEmpty()) {
            final User maria = Librarian.newLibrarian("maria@gmail.com", "Mariaroberta!123", "Maria Roberta");
            userRepository.save(maria);
        }

    }

    private void createReaderDetails() {
        //Reader1 - Manuel
        try {
            Optional<User> u1 = userRepository.findByUsername("manuel@gmail.com");
            Optional<ReaderDetails> readerDetails1= readerRepository.findByReaderNumber(LocalDate.now().getYear() + "/1");
            if (u1.isPresent() && readerDetails1.isEmpty()) {
                ReaderDetails r1 = new ReaderDetails(
                        1,
                        u1.get(),
                        "2000/01/01",
                        "919191919",
                        true,
                        true,
                        true);
                readerRepository.save(r1);
            }
        } catch(Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
        //Reader2 - joao
        try {
            Optional<User> u2 = userRepository.findByUsername("joao@gmail.com");
            Optional<ReaderDetails> readerDetails2 = readerRepository.findByReaderNumber(LocalDate.now().getYear() + "/2");
            if (u2.isPresent() && readerDetails2.isEmpty()) {
                ReaderDetails r2 = new ReaderDetails(
                        2,
                        u2.get(),
                        "2000/02/02",
                        "929292929",
                        true,
                        false,
                        false);
                readerRepository.save(r2);
            }
        } catch(Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
        //Reader3 - Pedro
        try {
            Optional<User> u3 = userRepository.findByUsername("pedro@gmail.com");
            Optional<ReaderDetails> readerDetails3 = readerRepository.findByReaderNumber(LocalDate.now().getYear() + "/3");
            if (u3.isPresent() && readerDetails3.isEmpty()) {
                ReaderDetails r3 = new ReaderDetails(
                        3,
                        u3.get(),
                        "2000/03/03",
                        "939393939",
                        true,
                        false,
                        true);
                readerRepository.save(r3);
            }
        } catch(Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }


}