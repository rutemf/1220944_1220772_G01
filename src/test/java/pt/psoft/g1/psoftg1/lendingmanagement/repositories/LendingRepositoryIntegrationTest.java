package pt.psoft.g1.psoftg1.lendingmanagement.repositories;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;
import pt.psoft.g1.psoftg1.authormanagement.model.Author;
import pt.psoft.g1.psoftg1.authormanagement.repositories.AuthorRepository;
import pt.psoft.g1.psoftg1.bookmanagement.model.Book;
import pt.psoft.g1.psoftg1.bookmanagement.repositories.BookRepository;
import pt.psoft.g1.psoftg1.genremanagement.model.Genre;
import pt.psoft.g1.psoftg1.genremanagement.repositories.GenreRepository;
import pt.psoft.g1.psoftg1.lendingmanagement.model.Lending;
import pt.psoft.g1.psoftg1.readermanagement.model.ReaderDetails;
import pt.psoft.g1.psoftg1.readermanagement.repositories.ReaderRepository;
import pt.psoft.g1.psoftg1.shared.services.Page;
import pt.psoft.g1.psoftg1.usermanagement.model.Reader;
import pt.psoft.g1.psoftg1.usermanagement.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Transactional
@WithMockUser(username = "testuser")
public class LendingRepositoryIntegrationTest {

    @Autowired
    private LendingRepository lendingRepository;
    @Autowired
    private ReaderRepository readerRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    private AuthorRepository authorRepository;

    private Lending lending;
    private ReaderDetails readerDetails;
    private Reader reader;
    private Book book;
    private Author author;
    private Genre genre;

    @BeforeEach
    public void setUp() {
        author = new Author("Manuel Antonio Pina",
                "Manuel António Pina foi um jornalista e escritor português, premiado em 2011 com o Prémio Camões",
                null);
        authorRepository.save(author);

        genre = new Genre("Género");
        genreRepository.save(genre);

        List<Author> authors = List.of(author);
        book = new Book("9782826012092",
                "O Inspetor Max",
                "conhecido pastor-alemão que trabalha para a Judiciária, vai ser fundamental para resolver um importante caso de uma rede de malfeitores que quer colocar uma bomba num megaconcerto de uma ilustre cantora",
                genre,
                authors,
                null);
        bookRepository.save(book);

        reader = Reader.newReader("manuel@gmail.com", "Manuelino123!", "Manuel Sarapinto das Coives");
        userRepository.save(reader);

        readerDetails = new ReaderDetails(1,
                reader,
                "2000-01-01",
                "919191919",
                true,
                true,
                true,
                null);
        readerRepository.save(readerDetails);

        // Create and save the lending
        lending = new Lending(book, readerDetails, 999, 14, 50);
        lendingRepository.save(lending);
    }

    @AfterEach
    public void cleanUp(){
        lendingRepository.delete(lending);
        readerRepository.delete(readerDetails);
        userRepository.delete(reader);
        bookRepository.delete(book);
        genreRepository.delete(genre);
        authorRepository.delete(author);
    }

    @Test
    public void testSave() {
        Lending newLending = new Lending(lending.getBook(), lending.getReaderDetails(), 2, 14, 50);
        Lending savedLending = lendingRepository.save(newLending);
        assertThat(savedLending).isNotNull();
        assertThat(savedLending.getLendingNumber()).isEqualTo(newLending.getLendingNumber());
        lendingRepository.delete(savedLending);
    }
/*
    @Test
    public void testFindByLendingNumber() {
        String ln = lending.getLendingNumber();
        Optional<Lending> found = lendingRepository.findByLendingNumber(ln);
        assertThat(found).isPresent();
        assertThat(found.get().getLendingNumber()).isEqualTo(lending.getLendingNumber());
    }

    @Test
    public void testListByReaderNumberAndIsbn() {
        List<Lending> lendings = lendingRepository.listByReaderNumberAndIsbn(lending.getReaderDetails().getReaderNumber(), lending.getBook().getIsbn());
        assertThat(lendings).isNotEmpty();
        assertThat(lendings).contains(lending);
    }

    @Test
    public void testGetCountFromCurrentYear() {
        int count = lendingRepository.getCountFromCurrentYear();
        assertThat(count).isGreaterThan(0);
    }

    @Test
    public void testListOutstandingByReaderNumber() {
        List<Lending> outstandingLendings = lendingRepository.listOutstandingByReaderNumber(lending.getReaderDetails().getReaderNumber());
        assertThat(outstandingLendings).contains(lending);
    }

    @Test
    public void testGetAverageDuration() {
        Double averageDuration = lendingRepository.getAverageDuration();
        assertThat(averageDuration).isNotNull();
    }

    @Test
    public void testGetOverdue() {
        // Assuming a Page implementation
        Page page = new Page(0, 10);
        List<Lending> overdueLendings = lendingRepository.getOverdue(page);
        assertThat(overdueLendings).isNotNull();
    }*/
}