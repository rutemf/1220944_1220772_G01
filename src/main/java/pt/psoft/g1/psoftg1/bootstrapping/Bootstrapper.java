package pt.psoft.g1.psoftg1.bootstrapping;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import pt.psoft.g1.psoftg1.authormanagement.model.Author;
import pt.psoft.g1.psoftg1.authormanagement.repositories.AuthorRepository;
import pt.psoft.g1.psoftg1.bookmanagement.model.Book;
import pt.psoft.g1.psoftg1.bookmanagement.model.Genre;
import pt.psoft.g1.psoftg1.bookmanagement.repositories.BookRepository;
import pt.psoft.g1.psoftg1.bookmanagement.repositories.GenreRepository;
import pt.psoft.g1.psoftg1.exceptions.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Profile("bootstrap")
public class Bootstrapper implements CommandLineRunner {

    private final GenreRepository genreRepository;
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    @Override
    public void run(final String... args) {
        createAuthors();
        createGenres();
        createBooks();
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
        Optional<Author> author = Optional.ofNullable(authorRepository.findByName("Manuel Antonio Pina"))
                .orElseThrow(() -> new NotFoundException("Cannot find author"));

        // O Inspetor Max
        if(bookRepository.findByIsbn("9782826012092").isEmpty()) {
            List<Author> authors = new ArrayList<>();
            if (genre.isPresent() && author.isPresent()) {
                authors.add(author.get());
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
            author = Optional.ofNullable(authorRepository.findByName("Author2"))
                    .orElseThrow(() -> new NotFoundException("Cannot find author"));
            if (genre.isPresent() && author.isPresent()) {
                authors2.add(author.get());
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
            author = Optional.ofNullable(authorRepository.findByName("Author3"))
                    .orElseThrow(() -> new NotFoundException("Cannot find author"));
            if (genre.isPresent() && author.isPresent()) {
                authors3.add(author.get());
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
            author = Optional.ofNullable(authorRepository.findByName("Author3"))
                    .orElseThrow(() -> new NotFoundException("Cannot find author"));
            Optional<Author> author2 = Optional.ofNullable(authorRepository.findByName("Author4"))
                    .orElseThrow(() -> new NotFoundException("Cannot find author"));
            if (genre.isPresent() && author.isPresent() && author2.isPresent()) {
                authors4.add(author.get());
                authors4.add(author2.get());
                Book b4 = new Book("9782722203426",
                        "O Principezinho", "Depois de deixar o seu asteroide e embarcar numa viagem pelo espaço, o principezinho chega, finalmente, à Terra. No deserto, o menino de cabelos da cor do ouro conhece um aviador, a quem conta todas as aventuras que viveu e tudo o que viu ao longo da sua jornada.",
                        genre.get(),
                        authors4);

                bookRepository.save(b4);
            }
        }
    }
}


