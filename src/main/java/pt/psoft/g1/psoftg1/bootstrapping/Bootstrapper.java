package pt.psoft.g1.psoftg1.bootstrapping;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import pt.psoft.g1.psoftg1.authormanagement.model.Author;
import pt.psoft.g1.psoftg1.authormanagement.repositories.AuthorRepository;
import pt.psoft.g1.psoftg1.bookmanagement.model.Book;
import pt.psoft.g1.psoftg1.bookmanagement.model.Genre;
import pt.psoft.g1.psoftg1.exceptions.NotFoundException;
import pt.psoft.g1.psoftg1.readermanagement.model.ReaderDetails;
import pt.psoft.g1.psoftg1.readermanagement.repositories.ReaderRepository;
import pt.psoft.g1.psoftg1.usermanagement.model.Librarian;
import pt.psoft.g1.psoftg1.usermanagement.model.Role;
import pt.psoft.g1.psoftg1.usermanagement.model.User;
import pt.psoft.g1.psoftg1.usermanagement.repositories.UserRepository;
import pt.psoft.g1.psoftg1.bookmanagement.repositories.GenreRepository;
import pt.psoft.g1.psoftg1.bookmanagement.repositories.BookRepository;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Optional;


@Component
@RequiredArgsConstructor
@Profile("bootstrap")
public class Bootstrapper implements CommandLineRunner {

    private final UserRepository userRepository;
    private final GenreRepository genreRepository;
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final ReaderRepository readerRepository;

    @Override
    @Transactional
    public void run(final String... args) throws Exception {
        createAuthors();
        createBookGenres();
        createUsers();
        createBooks();
        createReaders();
        createLibrarians();
    }

    private void createLibrarians() {
        //TODO: Tratar disto e talvez de librarianmanagement (????
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

    private void createReaders() {
        //Reader1 - Manuel
        try {
            Optional<User> u1 = userRepository.findByUsername("manuel@gmail.com");
            Optional<ReaderDetails> readerNumber = readerRepository.findByReaderNumber("2024/1");
            if (u1.isPresent() && readerNumber.isEmpty()) {
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
            Optional<ReaderDetails> readerNumber2 = readerRepository.findByReaderNumber("2024/2");
            if (u2.isPresent() && readerNumber2.isEmpty()) {
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
            Optional<ReaderDetails> readerNumber3 = readerRepository.findByReaderNumber("2024/3");
            if (u3.isPresent() && readerNumber3.isEmpty()) {
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

    private void createBookGenres() {
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

    private void createBooks() {
        Optional<Genre> genre = Optional.ofNullable(genreRepository.findByString("Ação"))
                .orElseThrow(() -> new NotFoundException("Cannot find genre"));
        Optional<Author> author = Optional.ofNullable(authorRepository.findByName("Manuel Antonio Pina"))
                .orElseThrow(() -> new NotFoundException("Cannot find author"));
        ArrayList<Author> authors = new ArrayList<>();

        // O Inspetor Max
        if (genre.isPresent() && author.isPresent()) {
            authors.add(author.get());
            Book b1 = new Book("9782826012092",
                    "O Inspetor Max",
                    "conhecido pastor-alemão que trabalha para a Judiciária, vai ser fundamental para resolver um importante caso de uma rede de malfeitores que quer colocar uma bomba num megaconcerto de uma ilustre cantora",
                    genre.get(),
                    authors);
            if(bookRepository.findByIsbn("9782826012092").isEmpty()) {
                bookRepository.save(b1);
            }
        }

        authors.clear();

        // C e Algoritmos
        genre = Optional.ofNullable(genreRepository.findByString("Informação"))
                .orElseThrow(() -> new NotFoundException("Cannot find genre"));
        author = Optional.ofNullable(authorRepository.findByName("Author2"))
                .orElseThrow(() -> new NotFoundException("Cannot find author"));
        if (genre.isPresent() && author.isPresent()) {
            authors.add(author.get());
            Book b2 = new Book("9782608153111",
                    "C e Algoritmos",
                    "O C é uma linguagem de programação incontornável no estudo e aprendizagem das linguagens de programação",
                    genre.get(),
                    authors);
            if(bookRepository.findByIsbn("9782608153111").isEmpty()) {
                bookRepository.save(b2);
            }
        }

        authors.clear();
/*
        // Vemo-nos em Agosto    Ricardo: está a dar erro. Diz que quebra a relaçao de unicidade do ISBN, apesar de ser diferente
        genre = Optional.ofNullable(genreRepository.findByString("Romance"))
                .orElseThrow(() -> new NotFoundException("Cannot find genre"));
        author = Optional.ofNullable(authorRepository.findByName("Author3"))
                .orElseThrow(() -> new NotFoundException("Cannot find author"));
        if (genre.isPresent() && author.isPresent()) {
            authors.add(author.get());
            Book b3 = new Book("9782722203402",
                    "Vemo-nos em Agosto",
                    "Através das sensuais noites caribenhas repletas de salsa e boleros, homens sedutores e vigaristas, a cada agosto que passa Ana viaja mais longe para o interior do seu desejo e do medo escondido no seu coração.",
                    genre.get(),
                    authors);
            if(bookRepository.findByIsbn("9782722203402").isEmpty()) {
                bookRepository.save(b3);
            }
        }

        authors.clear();
*/

        // O Principezinho

        /*genre = Optional.ofNullable(genreRepository.findByString("Infantil"))
                .orElseThrow(() -> new NotFoundException("Cannot find genre"));
        author = Optional.ofNullable(authorRepository.findByName("Author4"))
                .orElseThrow(() -> new NotFoundException("Cannot find author"));
        Optional<Author> author2 = Optional.ofNullable(authorRepository.findByName("Author4"))
                .orElseThrow(() -> new NotFoundException("Cannot find author"));
        if (genre.isPresent() && author.isPresent() && author2.isPresent()) {
            authors.add(author.get());
            authors.add(author2.get());
            Book b4 = new Book("9782722203426",
                    "O Principezinho", "Depois de deixar o seu asteroide e embarcar numa viagem pelo espaço, o principezinho chega, finalmente, à Terra. No deserto, o menino de cabelos da cor do ouro conhece um aviador, a quem conta todas as aventuras que viveu e tudo o que viu ao longo da sua jornada.",
                    genre.get(),
                    authors);
            if(bookRepository.findByIsbn("9782722203426").isEmpty()) {
                bookRepository.save(b4);
            }
        }
        authors.clear();*/

    }

    private void createUsers() throws Exception {
        // Manuel
        if (userRepository.findByUsername("manuel@gmail.com").isEmpty()) {
            final User manuel = User.newUser("manuel@gmail.com", "Manuelino123!", "Manuel Sarapinto das Coives");
            manuel.addAuthority(new Role(Role.READER));
            userRepository.save(manuel);
        }
        // João
        if (userRepository.findByUsername("joao@gmail.com").isEmpty()) {
            final User joao = User.newUser("joao@gmail.com", "Joaoratao!123", "Joao Ratao");
            joao.addAuthority(new Role(Role.READER));
            userRepository.save(joao);
        }
        // Pedro
        if (userRepository.findByUsername("pedro@gmail.com").isEmpty()) {
            final User pedro = User.newUser("pedro@gmail.com", "Pedrodascenas!123", "Pedro Das Cenas");
            pedro.addAuthority(new Role(Role.READER));
            userRepository.save(pedro);
        }
        // Maria
        if (userRepository.findByUsername("maria@gmail.com").isEmpty()) {
            final User maria = Librarian.newLibrarian("maria@gmail.com", "Mariaroberta!123", "Maria Roberta");
            maria.addAuthority(new Role(Role.LIBRARIAN));
            userRepository.save(maria);
        }

    }
}


