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
import pt.psoft.g1.psoftg1.authormanagement.repositories.AuthorRepository;
import pt.psoft.g1.psoftg1.bookmanagement.model.Book;
import pt.psoft.g1.psoftg1.bookmanagement.model.Genre;
import pt.psoft.g1.psoftg1.bookmanagement.repositories.BookRepository;
import pt.psoft.g1.psoftg1.bookmanagement.repositories.GenreRepository;
import pt.psoft.g1.psoftg1.exceptions.NotFoundException;
import pt.psoft.g1.psoftg1.lendingmanagement.model.Lending;
import pt.psoft.g1.psoftg1.lendingmanagement.repositories.LendingRepository;
import pt.psoft.g1.psoftg1.readermanagement.model.ReaderDetails;
import pt.psoft.g1.psoftg1.readermanagement.repositories.ReaderRepository;
import pt.psoft.g1.psoftg1.shared.services.ForbiddenNameService;
import pt.psoft.g1.psoftg1.usermanagement.model.Reader;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Profile("bootstrap")
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

    private final ForbiddenNameService forbiddenNameService;

    @Override
    @Transactional
    public void run(final String... args) {
        createAuthors();
        createGenres();
        createBooks();
        loadForbiddenNames();
        createLendings();
    }

    private void createAuthors() {
        if (authorRepository.findByName("Manuel Antonio Pina").isEmpty()) {
            final Author a1 = new Author("Manuel Antonio Pina",
                    "Manuel António Pina foi um jornalista e escritor português, premiado em 2011 com o Prémio Camões");
            authorRepository.save(a1);
        }
        if (authorRepository.findByName("Author2").isEmpty()) {
            final Author a2 = new Author("Author2", "Bio do Author 2");
            authorRepository.save(a2);
        }
        if (authorRepository.findByName("Author3").isEmpty()) {
            final Author a3 = new Author("Author3", "Bio do Author 3");
            authorRepository.save(a3);
        }
        if (authorRepository.findByName("Author4").isEmpty()) {
            final Author a4 = new Author("Author4", "Bio do Author 4");
            authorRepository.save(a4);
        }
    }

    private void createGenres() {
        if (genreRepository.findByString("Ação").isEmpty()) {
            final Genre g1 = new Genre("Ação");
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
    }

    protected void createBooks() {
        Optional<Genre> genre = Optional.ofNullable(genreRepository.findByString("Ação"))
                .orElseThrow(() -> new NotFoundException("Cannot find genre"));
        List<Author> author = authorRepository.findByName("Manuel Antonio Pina");

        // O Inspetor Max
        if(bookRepository.findByIsbn("9782826012092").isEmpty()) {
            List<Author> authors = new ArrayList<>();
            if (genre.isPresent() && !author.isEmpty()) {
                authors.add(author.get(0));
                Book b1 = new Book("9782826012092",
                        "O Inspetor Max",
                        "conhecido pastor-alemão que trabalha para a Judiciária, vai ser fundamental para resolver um importante caso de uma rede de malfeitores que quer colocar uma bomba num megaconcerto de uma ilustre cantora",
                        genre.get(),
                        authors);

                bookRepository.save(b1);
            }
        }

        // C e Algoritmos
        if(bookRepository.findByIsbn("9782608153111").isEmpty()) {
            List<Author> authors2 = new ArrayList<>();
            genre = Optional.ofNullable(genreRepository.findByString("Informação"))
                    .orElseThrow(() -> new NotFoundException("Cannot find genre"));
            author = authorRepository.findByName("Author2");
            if (genre.isPresent() && !author.isEmpty()) {
                authors2.add(author.get(0));
                Book b2 = new Book("9782608153111",
                        "C e Algoritmos",
                        "O C é uma linguagem de programação incontornável no estudo e aprendizagem das linguagens de programação",
                        genre.get(),
                        authors2);

                bookRepository.save(b2);
            }
        }


        // Vemo-nos em Agosto    Ricardo: está a dar erro. Diz que quebra a relaçao de unicidade do ISBN, apesar de ser diferente
        // Ricardo: tentei de tudo para corrigir, só o @ManyToMany é que resolveu o problema
        if(bookRepository.findByIsbn("9782722203402").isEmpty()) {
            List<Author> authors3 = new ArrayList<>();
            genre = Optional.ofNullable(genreRepository.findByString("Romance"))
                    .orElseThrow(() -> new NotFoundException("Cannot find genre"));
            author = authorRepository.findByName("Author3");
            if (genre.isPresent() && !author.isEmpty()) {
                authors3.add(author.get(0));
                Book b3 = new Book("9782722203402",
                        "Vemo-nos em Agosto",
                        "Através das sensuais noites caribenhas repletas de salsa e boleros, homens sedutores e vigaristas, a cada agosto que passa Ana viaja mais longe para o interior do seu desejo e do medo escondido no seu coração.",
                        genre.get(),
                        authors3);

                bookRepository.save(b3);
            }
        }

        // O Principezinho
        if(bookRepository.findByIsbn("9782722203426").isEmpty()) {
            List<Author> authors4 = new ArrayList<>();
            genre = Optional.ofNullable(genreRepository.findByString("Infantil"))
                    .orElseThrow(() -> new NotFoundException("Cannot find genre"));
            author = authorRepository.findByName("Author3");
            List<Author> author2 = authorRepository.findByName("Author4");
            if (genre.isPresent() && !author.isEmpty() && !author2.isEmpty()) {
                authors4.add(author.get(0));
                authors4.add(author2.get(0));
                Book b4 = new Book("9782722203426",
                        "O Principezinho", "Depois de deixar o seu asteroide e embarcar numa viagem pelo espaço, o principezinho chega, finalmente, à Terra. No deserto, o menino de cabelos da cor do ouro conhece um aviador, a quem conta todas as aventuras que viveu e tudo o que viu ao longo da sua jornada.",
                        genre.get(),
                        authors4);

                bookRepository.save(b4);
            }
        }
    }

    protected void loadForbiddenNames() {
        String fileName = "forbiddenNames.txt";
        forbiddenNameService.loadDataFromFile(fileName);
    }

    private void createLendings() {
        int i;
        final var book1 = bookRepository.findByIsbn("9782722203426");
        final var book2 = bookRepository.findByIsbn("9782722203402");
        final var book3 = bookRepository.findByIsbn("9782608153111");
        final var book4 = bookRepository.findByIsbn("9782826012092");
        List<Book> books = new ArrayList<>();
        if(book1.isPresent() && book2.isPresent() && book3.isPresent() && book4.isPresent()){
            books = List.of(new Book[]{book1.get(), book2.get(), book3.get(), book4.get()});
        }

        final var readerDetails1 = readerRepository.findByReaderNumber("2024/1");
        final var readerDetails2 = readerRepository.findByReaderNumber("2024/2");
        final var readerDetails3 = readerRepository.findByReaderNumber("2024/3");
        List<ReaderDetails> readers = new ArrayList<>();
        if(readerDetails1.isPresent() && readerDetails2.isPresent() && readerDetails3.isPresent()){
            readers = List.of(new ReaderDetails[]{readerDetails1.get(), readerDetails2.get(), readerDetails3.get()});
        }

        LocalDate startDate = LocalDate.of(2024, 4,1);
        LocalDate returnedDate;
        Lending lending;

        //Lendings 1 through 3 (late, returned)
        for(i = 0; i < 3; i++){
            if(lendingRepository.findByLendingNumber("2024/" + (1+i)).isEmpty()){
                startDate = LocalDate.of(2024, 3,31-i);
                returnedDate = LocalDate.of(2024,4,15+i);
                lending = Lending.newBootstrappingLending(books.get(i), readers.get(0), 2024, i+1, startDate, returnedDate, lendingDurationInDays, fineValuePerDayInCents);
                lendingRepository.save(lending);
            }
        }

        //Lendings 4 through 6 (overdue, not returned)
        for(i = 0; i < 3; i++){
            if(lendingRepository.findByLendingNumber("2024/" + (4+i)).isEmpty()){
                startDate = LocalDate.of(2024, 5,25+i);
                lending = Lending.newBootstrappingLending(books.get(1+i), readers.get(1), 2024, (i+4), startDate, null, lendingDurationInDays, fineValuePerDayInCents);
                lendingRepository.save(lending);
            }
        }
        //Lendings 7 through 9 (late, overdue, not returned)
        for(i = 0; i < 3; i++){
            if(lendingRepository.findByLendingNumber("2024/" + (7+i)).isEmpty()){
                startDate = LocalDate.of(2024, 5,(1+2*i));
                lending = Lending.newBootstrappingLending(books.get(3/(i+1)), readers.get(2), 2024, (i+7), startDate, null, lendingDurationInDays, fineValuePerDayInCents);
                lendingRepository.save(lending);
            }
        }

        //Lendings 10 through 12 (returned)
        for(i = 0; i < 3; i++){
            if(lendingRepository.findByLendingNumber("2024/" + (10+i)).isEmpty()){
                startDate = LocalDate.of(2024, 6,(i+1));
                returnedDate = LocalDate.of(2024,6,(i+2));
                lending = Lending.newBootstrappingLending(books.get(3-i), readers.get(0), 2024, (i+10), startDate, returnedDate, lendingDurationInDays, fineValuePerDayInCents);
                lendingRepository.save(lending);
            }
        }
    }
}


