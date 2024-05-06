package pt.psoft.g1.psoftg1.bookmanagement.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import pt.psoft.g1.psoftg1.authormanagement.model.Author;
import pt.psoft.g1.psoftg1.bookmanagement.services.UpdateBookRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "Book", uniqueConstraints = {
        @UniqueConstraint(name = "uc_book_isbn", columnNames = {"ISBN"})
})
public class Book {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    long pk;

    @Getter
    @Embedded
    Isbn isbn;

    @Getter
    @Embedded
    @NotNull
    Title title;

    @Getter
    @ManyToOne
    @NotNull
    Genre genre;

    @Getter
    @OneToMany
    private List<Author> authors = new ArrayList<>();

    @Embedded
    Description description;

    private void setTitle(String title) {this.title = new Title(title);}

    private void setIsbn(String isbn) {
        this.isbn = new Isbn(isbn);
    }

    private void setDescription(String description) {this.description = new Description(description); }

    private void setGenre(Genre genre) {this.genre = genre; }

    private void setAuthors(List<Author> authors) {this.authors = authors; }

    public Optional<Description> getDescription(){ return Optional.ofNullable(description); }

    public Book(String isbn, String title, String description, Genre genre, List<Author> authors) {
        setTitle(title);
        setIsbn(isbn);
        if(description != null)
            setDescription(description);
        setGenre(genre);
        setAuthors(authors);
    }

    protected Book() {
        // got ORM only
    }

    public void applyPatch(UpdateBookRequest request) {
        String title = request.getTitle();
        String description = request.getDescription();
        Genre genre = request.getGenreObj();
        List<Author> authors = request.getAuthorObjList();

        if(title != null) {
            setTitle(title);
        }

        if(description != null) {
            setDescription(description);
        }

        if(genre != null) {
            setGenre(genre);
        }

        if(authors != null) {
            setAuthors(authors);
        }
    }
}
