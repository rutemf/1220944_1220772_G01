package pt.psoft.g1.psoftg1.bootstrapping;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pt.psoft.g1.psoftg1.authormanagement.model.Author;
import pt.psoft.g1.psoftg1.authormanagement.model.Bio;
import pt.psoft.g1.psoftg1.authormanagement.repositories.AuthorRepository;
import pt.psoft.g1.psoftg1.bookmanagement.model.Book;
import pt.psoft.g1.psoftg1.genremanagement.model.Genre;
import pt.psoft.g1.psoftg1.bookmanagement.repositories.BookRepository;
import pt.psoft.g1.psoftg1.genremanagement.repositories.GenreRepository;
import pt.psoft.g1.psoftg1.lendingmanagement.model.Fine;
import pt.psoft.g1.psoftg1.lendingmanagement.model.Lending;
import pt.psoft.g1.psoftg1.lendingmanagement.model.LendingNumber;
import pt.psoft.g1.psoftg1.lendingmanagement.repositories.FineRepository;
import pt.psoft.g1.psoftg1.lendingmanagement.repositories.LendingRepository;
import pt.psoft.g1.psoftg1.readermanagement.model.ReaderDetails;
import pt.psoft.g1.psoftg1.readermanagement.repositories.ReaderRepository;
import pt.psoft.g1.psoftg1.shared.model.Name;
import pt.psoft.g1.psoftg1.shared.services.ForbiddenNameService;
import pt.psoft.g1.psoftg1.usermanagement.model.Librarian;
import pt.psoft.g1.psoftg1.usermanagement.model.Reader;
import pt.psoft.g1.psoftg1.usermanagement.model.User;
import pt.psoft.g1.psoftg1.usermanagement.repositories.UserRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Profile("bootstrap")
@PropertySource({"classpath:config/library.properties"})
public class Bootstrapper implements CommandLineRunner {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final FineRepository fineRepository;
    private final ForbiddenNameService forbiddenNameService;
    private final GenreRepository genreRepository;
    private final LendingRepository lendingRepository;
    private final ReaderRepository readerRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public void run(final String... args) {
        loadForbiddenNames();

        // createReaders();
        createLibrarian();

        createAuthors();
        createGenres();
        createBooks();

        createLendings();
        createFines();
    }

    private void createAuthors() {
        Iterable<Author> authors = authorRepository.findAll();

        if (!authors.iterator().hasNext()) {

            final Author author1 = new Author(1L, new Name("JK Rowling"), new Bio("British author best known for the Harry Potter series."));
            authorRepository.save(author1);

            final Author author2 = new Author(2L, new Name("George RR Martin"), new Bio("American novelist, creator of A Song of Ice and Fire."));
            authorRepository.save(author2);

            final Author author3 = new Author(3L, new Name("JRR Tolkien"), new Bio("English writer and philologist, author of The Lord of the Rings."));
            authorRepository.save(author3);

            final Author author4 = new Author(4L, new Name("Agatha Christie"), new Bio("English novelist, famous for detective stories like Poirot."));
            authorRepository.save(author4);

            final Author author5 = new Author(5L, new Name("Ernest Hemingway"), new Bio("American novelist and Nobel Prize winner in Literature."));
            authorRepository.save(author5);

            final Author author6 = new Author(6L, new Name("Jane Austen"), new Bio("English novelist known for Pride and Prejudice."));
            authorRepository.save(author6);

        }
    }

    private void createGenres() {
        Iterable<Genre> genres = genreRepository.findAll();

        if (!genres.iterator().hasNext()) {
            final Genre genre1 = new Genre("Fantasy");
            genreRepository.save(genre1);

            final Genre genre2 = new Genre("Science Fiction");
            genreRepository.save(genre2);

            final Genre genre3 = new Genre("Mystery");
            genreRepository.save(genre3);

            final Genre genre4 = new Genre("Romance");
            genreRepository.save(genre4);

            final Genre genre5 = new Genre("Non-Fiction");
            genreRepository.save(genre5);
        }
    }

    private void createBooks() {
        Optional<Genre> romanceGenre = genreRepository.findByString("Romance");
        Optional<Genre> mysteryGenre = genreRepository.findByString("Mystery");
        Optional<Genre> fantasyGenre = genreRepository.findByString("Fantasy");

        Author janeAusten = authorRepository.searchByNameName("Jane Austen").get(0);
        Author ernestHemingway = authorRepository.searchByNameName("Ernest Hemingway").get(0);
        Author agathaChristie = authorRepository.searchByNameName("Agatha Christie").get(0);

        if (bookRepository.findByIsbn("9789720706386").isEmpty()) {
            List<Author> authors = new ArrayList<>();
            authors.add(janeAusten);

            Book book = new Book("9789720706386", "O País das Pessoas de Pernas Para o Ar ",
            "O livro reúne quatro histórias divertidas de um peixinho vermelho. ",
            romanceGenre.get(), authors, null);

            bookRepository.save(book);
        }

        if (bookRepository.findByIsbn("9789723716160").isEmpty()) {
            List<Author> authors = new ArrayList<>();
            authors.add(ernestHemingway);

            Book book = new Book("9789723716160", "Como se Desenha Uma Casa",
            "Como quem, vindo de países distantes fora do caminho.",
            mysteryGenre.get(), authors, null);

            bookRepository.save(book);
        }

        if (bookRepository.findByIsbn("9789895612864").isEmpty()) {
            List<Author> authors = new ArrayList<>();
            authors.add(agathaChristie);

            Book book = new Book("9789895612864", "C e Algoritmos",
            "O C é uma linguagem de programação incontornável no estudo.",
            fantasyGenre.get(), authors, null);

            bookRepository.save(book);
        }

        if (bookRepository.findByIsbn("9782722203402").isEmpty()) {
            List<Author> authors = new ArrayList<>();
            authors.add(janeAusten);
            authors.add(ernestHemingway);

            Book book = new Book("9782722203402", "Introdução ao Desenvolvimento Moderno para a Web",
            "Este livro foca o desenvolvimento moderno de aplicações Web.",
            fantasyGenre.get(), authors, null);

            bookRepository.save(book);
        }

        if (bookRepository.findByIsbn("9789722328296").isEmpty()) {
            List<Author> authors = new ArrayList<>();
            authors.add(agathaChristie);
            authors.add(janeAusten);

            Book book = new Book("9789722328296", "O Principezinho",
            "Depois de deixar o seu asteroide.",
            romanceGenre.get(), authors, "bookPhotoTest.jpg");

            bookRepository.save(book);
        }
    }

    protected void loadForbiddenNames() {
        String fileName = "forbiddenNames.txt";
        forbiddenNameService.loadDataFromFile(fileName);
    }

    private void createLendings() {
        final var book1 = bookRepository.findByIsbn("9789720706386").get();
        final var book2 = bookRepository.findByIsbn("9789723716160").get();
        final var book3 = bookRepository.findByIsbn("9789895612864").get();
        final var book4 = bookRepository.findByIsbn("9782722203402").get();
        final var book5 = bookRepository.findByIsbn("9789722328296").get();

        List<Book> books = new ArrayList<>();
        books.add(book1);
        books.add(book2);
        books.add(book3);
        books.add(book4);
        books.add(book5);

        final var readerDetails1 = readerRepository.findByReaderNumber("2025/1").get();
        final var readerDetails2 = readerRepository.findByReaderNumber("2025/2").get();

        List<ReaderDetails> readers = new ArrayList<>();
        readers.add(readerDetails1);
        readers.add(readerDetails2);

        if (lendingRepository.findByLendingNumber("2025/0").isEmpty()) {
            Lending lending1 = new Lending(books.get(0), readers.get(0), 30, 5);
            lending1.setLendingNumber(new LendingNumber(2025,1));
            lendingRepository.save(lending1);
        }

        if (lendingRepository.findByLendingNumber("2025/1").isEmpty()) {
            Lending lending2 = new Lending(books.get(1), readers.get(0), 25, 5);
            lending2.setLendingNumber(new LendingNumber(2025,2));
            lendingRepository.save(lending2);
        }

        if (lendingRepository.findByLendingNumber("2025/2").isEmpty()) {
            Lending lending3 = new Lending(books.get(0), readers.get(1), 25, 10);
            lending3.setLendingNumber(new LendingNumber(2025,3));
            lendingRepository.save(lending3);
        }

        if (lendingRepository.findByLendingNumber("2025/3").isEmpty()) {
            Lending lending4 = new Lending(books.get(2), readers.get(1), 15, 5);
            lending4.setLimitDate(LocalDate.of(2025,1, 10));
            lendingRepository.save(lending4);
        }

        if (lendingRepository.findByLendingNumber("2025/4").isEmpty()) {
            Lending lending4 = new Lending(books.get(2), readers.get(1), 30, 5);
            lending4.setLimitDate(LocalDate.of(2025,3, 10));
            lendingRepository.save(lending4);
        }

        if (lendingRepository.findByLendingNumber("2025/5").isEmpty()) {
            Lending lending4 = new Lending(books.get(1), readers.get(1), 30, 15);
            lending4.setLimitDate(LocalDate.of(2025,7, 10));
            lendingRepository.save(lending4);
        }
    }

    private void createFines() {
        Iterable<Fine> fines = fineRepository.findAll();

        if (!fines.iterator().hasNext()) {
            Lending lending1 = lendingRepository.findByLendingNumber("2025/1").get();
            final Fine fine1 = new Fine(lending1);
            fineRepository.save(fine1);

            Lending lending2 = lendingRepository.findByLendingNumber("2025/2").get();
            final Fine fine2 = new Fine(lending2);
            fineRepository.save(fine2);
        }
    }

    private void createReaders() {
        boolean readersExists = true;
        Iterable<ReaderDetails> readers = readerRepository.findAll();
        if (!readers.iterator().hasNext()) {
            readersExists = false;
        }

        if (userRepository.findByUsername("miguel@gmail.com").isEmpty()) {
            final Reader miguel = Reader.newReader("miguel@gmail.com", "Miguel123!", "Miguel Cardoso");
            userRepository.save(miguel);
        } else if (!readersExists) {
            final Reader miguel = Reader.newReader("miguel@gmail.com", "Miguel123!", "Miguel Cardoso");
            createReaderDetails(1, miguel, "2004-07-04", "910663221");
        }

        if (userRepository.findByUsername("rute@gmail.com").isEmpty()) {
            final Reader rute = Reader.newReader("rute@gmail.com", "Rute!123", "Rute Ferreira");
            userRepository.save(rute);
        } else if (!readersExists) {
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