package pt.psoft.g1.psoftg1.bootstrapping;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pt.psoft.g1.psoftg1.authormanagement.model.Author;
import pt.psoft.g1.psoftg1.authormanagement.model.Bio;
import pt.psoft.g1.psoftg1.authormanagement.repositories.AuthorRepository;
import pt.psoft.g1.psoftg1.bookmanagement.model.Book;
import pt.psoft.g1.psoftg1.genremanagement.model.Genre;
import pt.psoft.g1.psoftg1.bookmanagement.repositories.BookRepository;
import pt.psoft.g1.psoftg1.genremanagement.repositories.GenreRepository;
import pt.psoft.g1.psoftg1.exceptions.NotFoundException;
import pt.psoft.g1.psoftg1.lendingmanagement.model.Lending;
import pt.psoft.g1.psoftg1.lendingmanagement.repositories.LendingRepository;
import pt.psoft.g1.psoftg1.readermanagement.model.ReaderDetails;
import pt.psoft.g1.psoftg1.readermanagement.repositories.ReaderRepository;
import pt.psoft.g1.psoftg1.shared.model.Name;
import pt.psoft.g1.psoftg1.shared.repositories.PhotoRepository;
import pt.psoft.g1.psoftg1.shared.services.ForbiddenNameService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Profile("sql")
@PropertySource({"classpath:config/library.properties"})
@Order(2)
public class Bootstrapper implements CommandLineRunner {
    @Value("${lendingDurationInDays}")
    private int lendingDurationInDays;
    @Value("${fineValuePerDayInCents}")
    private int fineValuePerDayInCents;

    private final GenreRepository genreRepository;
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final LendingRepository lendingRepository;
    private final ReaderRepository readerRepository;
    private final PhotoRepository photoRepository;

    private final ForbiddenNameService forbiddenNameService;

    @Override
    @Transactional
    public void run(final String... args) {
        // createAuthors();
        // createGenres();
        // createBooks();
        // loadForbiddenNames();
        // createLendings();
        // createPhotos();
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
        int i;
        int seq = 0;

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

        final var readerDetails1 = readerRepository.findByReaderNumber("2025/1");
        final var readerDetails2 = readerRepository.findByReaderNumber("2025/2");
        final var readerDetails3 = readerRepository.findByReaderNumber("2025/3");
        final var readerDetails4 = readerRepository.findByReaderNumber("2025/4");
        final var readerDetails5 = readerRepository.findByReaderNumber("2025/5");
        final var readerDetails6 = readerRepository.findByReaderNumber("2025/6");

        List<ReaderDetails> readers = new ArrayList<>();
        if (readerDetails1.isPresent() && readerDetails2.isPresent() && readerDetails3.isPresent()) {
            readers = List.of(new ReaderDetails[]{readerDetails1.get(), readerDetails2.get(), readerDetails3.get(),
                    readerDetails4.get(), readerDetails5.get(), readerDetails6.get()});
        }

        LocalDate startDate;
        LocalDate returnedDate;
        Lending lending;

        //Lendings 1 through 3 (late, returned)
        for (i = 0; i < 3; i++) {
            ++seq;
            if (lendingRepository.findByLendingNumber("2024/" + seq).isEmpty()) {
                startDate = LocalDate.of(2024, 1, 31 - i);
                returnedDate = LocalDate.of(2024, 2, 15 + i);
                // lending = Lending.newBootstrappingLending(books.get(i), readers.get(i*2), 2024, seq, startDate, returnedDate, lendingDurationInDays, fineValuePerDayInCents);
                // lendingRepository.save(lending);
            }
        }

        //Lendings 4 through 6 (overdue, not returned)
        for (i = 0; i < 3; i++) {
            ++seq;
            if (lendingRepository.findByLendingNumber("2024/" + seq).isEmpty()) {
                startDate = LocalDate.of(2024, 3, 25 + i);
                // lending = Lending.newBootstrappingLending(books.get(1+i), readers.get(1+i*2), 2024, seq, startDate, null, lendingDurationInDays, fineValuePerDayInCents);
                // lendingRepository.save(lending);
            }
        }
        //Lendings 7 through 9 (late, overdue, not returned)
        for (i = 0; i < 3; i++) {
            ++seq;
            if (lendingRepository.findByLendingNumber("2024/" + seq).isEmpty()) {
                startDate = LocalDate.of(2024, 4, (1 + 2 * i));
                // lending = Lending.newBootstrappingLending(books.get(3/(i+1)), readers.get(i*2), 2024, seq, startDate, null, lendingDurationInDays, fineValuePerDayInCents);
                // lendingRepository.save(lending);
            }
        }

        //Lendings 10 through 12 (returned)
        for (i = 0; i < 3; i++) {
            ++seq;
            if (lendingRepository.findByLendingNumber("2024/" + seq).isEmpty()) {
                startDate = LocalDate.of(2024, 5, (i + 1));
                returnedDate = LocalDate.of(2024, 5, (i + 2));
                // lending = Lending.newBootstrappingLending(books.get(3-i), readers.get(1+i*2), 2024, seq, startDate, returnedDate, lendingDurationInDays, fineValuePerDayInCents);
                // lendingRepository.save(lending);
            }
        }

        //Lendings 13 through 18 (returned)
        for (i = 0; i < 6; i++) {
            ++seq;
            if (lendingRepository.findByLendingNumber("2024/" + seq).isEmpty()) {
                startDate = LocalDate.of(2024, 5, (i + 2));
                returnedDate = LocalDate.of(2024, 5, (i + 2 * 2));
                // lending = Lending.newBootstrappingLending(books.get(i), readers.get(i), 2024, seq, startDate, returnedDate, lendingDurationInDays, fineValuePerDayInCents);
                // lendingRepository.save(lending);
            }
        }

        //Lendings 19 through 23 (returned)
        for (i = 0; i < 6; i++) {
            ++seq;
            if (lendingRepository.findByLendingNumber("2024/" + seq).isEmpty()) {
                startDate = LocalDate.of(2024, 5, (i + 8));
                returnedDate = LocalDate.of(2024, 5, (2 * i + 8));
                // lending = Lending.newBootstrappingLending(books.get(i), readers.get(1+i%4), 2024, seq, startDate, returnedDate, lendingDurationInDays, fineValuePerDayInCents);
                // lendingRepository.save(lending);
            }
        }

        //Lendings 24 through 29 (returned)
        for (i = 0; i < 6; i++) {
            ++seq;
            if (lendingRepository.findByLendingNumber("2024/" + seq).isEmpty()) {
                startDate = LocalDate.of(2024, 5, (i + 18));
                returnedDate = LocalDate.of(2024, 5, (2 * i + 18));
                // lending = Lending.newBootstrappingLending(books.get(i), readers.get(i%2+2), 2024, seq, startDate, returnedDate, lendingDurationInDays, fineValuePerDayInCents);
                // lendingRepository.save(lending);
            }
        }

        //Lendings 30 through 35 (not returned, not overdue)
        for (i = 0; i < 6; i++) {
            ++seq;
            if (lendingRepository.findByLendingNumber("2024/" + seq).isEmpty()) {
                startDate = LocalDate.of(2024, 6, (i / 3 + 1));
                // lending = Lending.newBootstrappingLending(books.get(i), readers.get(i%2+3), 2024, seq, startDate, null, lendingDurationInDays, fineValuePerDayInCents);
                // lendingRepository.save(lending);
            }
        }

        //Lendings 36 through 45 (not returned, not overdue)
        for (i = 0; i < 10; i++) {
            ++seq;
            if (lendingRepository.findByLendingNumber("2024/" + seq).isEmpty()) {
                startDate = LocalDate.of(2024, 6, (2 + i / 4));
                // lending = Lending.newBootstrappingLending(books.get(i), readers.get(4-i%4), 2024, seq, startDate, null, lendingDurationInDays, fineValuePerDayInCents);
                // lendingRepository.save(lending);
            }
        }
    }

    private void createPhotos() {
        /*Optional<Photo> photoJoao = photoRepository.findByPhotoFile("foto-joao.jpg");
        if(photoJoao.isEmpty()) {
            Photo photo = new Photo(Paths.get(""))
        }*/
    }
}


