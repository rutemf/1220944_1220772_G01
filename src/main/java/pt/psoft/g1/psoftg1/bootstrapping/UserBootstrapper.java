package pt.psoft.g1.psoftg1.bootstrapping;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pt.psoft.g1.psoftg1.genremanagement.model.Genre;
import pt.psoft.g1.psoftg1.genremanagement.repositories.GenreRepository;
import pt.psoft.g1.psoftg1.readermanagement.model.ReaderDetails;
import pt.psoft.g1.psoftg1.readermanagement.repositories.ReaderRepository;
import pt.psoft.g1.psoftg1.usermanagement.model.Librarian;
import pt.psoft.g1.psoftg1.usermanagement.model.Reader;
import pt.psoft.g1.psoftg1.usermanagement.model.User;
import pt.psoft.g1.psoftg1.usermanagement.repositories.UserRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Profile("bootstrap")
@Order(1)
public class UserBootstrapper implements CommandLineRunner {

    private final ReaderRepository readerRepository;
    private final UserRepository userRepository;
    private final GenreRepository genreRepository;

    @Override
    @Transactional
    public void run(final String... args)  {
        createReaders();
        createLibrarian();
    }

    private void createReaders() {

        if (userRepository.findByUsername("miguel@gmail.com").isEmpty()) {
            final Reader miguel = Reader.newReader("miguel@gmail.com", "Miguel123!", "Miguel Cardoso");
            userRepository.save(miguel);
        } else {
            final Reader miguel = Reader.newReader("miguel@gmail.com", "Miguel123!", "Miguel Cardoso");
            createReaderDetails(1, miguel, "2004-07-04", "910663221");
        }

        if (userRepository.findByUsername("rute@gmail.com").isEmpty()) {
            final Reader rute = Reader.newReader("rute@gmail.com", "Rute!123", "Rute Ferreira");
            userRepository.save(rute);
        } else {
            final Reader rute = Reader.newReader("rute@gmail.com", "Rute!123", "Rute Ferreira");
            createReaderDetails(2, rute, "2004-11-04", "999888777");
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

    private void createReaderDetails(int number, Reader reader, String birthDate, String phoneNumber) {
        Optional<Genre> romanceGenre = genreRepository.findByString("Romance");
        Optional<Genre> mysteryGenre = genreRepository.findByString("Mystery");
        Optional<Genre> fantasyGenre = genreRepository.findByString("Fantasy");
        List<Genre> interestList = Arrays.asList(romanceGenre.get(), mysteryGenre.get(), fantasyGenre.get());

        ReaderDetails readerDetails = new ReaderDetails(number, reader, birthDate, phoneNumber, true, true, true, null, interestList);
        readerRepository.save(readerDetails);
    }

    private void createLibrarian() {

        if (userRepository.findByUsername("maria@gmail.com").isEmpty()) {
            final User maria = Librarian.newLibrarian("maria@gmail.com", "Maria!123", "Maria Roberta");
            userRepository.save(maria);
        }

    }
}
