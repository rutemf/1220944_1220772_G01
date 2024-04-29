package pt.psoft.g1.psoftg1.bookmanagement.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import pt.psoft.g1.psoftg1.authormanagement.model.Author;
import pt.psoft.g1.psoftg1.bookmanagement.services.UpdateBookRequest;


import java.util.List;

import static org.springdoc.core.service.GenericResponseService.setDescription;

@Entity
public class Book {
    @Getter
    @EmbeddedId
    @Column(name="ISBN")
    Isbn isbn;

    @Getter
    @Embedded
    Title title;

    @Setter
    @Getter
    @ManyToOne
    Genre genre;

    //TODO: Fix the many to many with either array of AuthorNumbers or store the whole objects (not good)
    @ManyToMany(mappedBy="books")
    private List<Author> authors;

    @Embedded
    Description description;

    public Book(String isbn, String title) {
        setIsbn(isbn);
        setTitle(title);
    }

    private void setTitle(String title) {
        this.title = new Title(title);
    }

    private void setIsbn(String isbn) {
        this.isbn = new Isbn(isbn);
    }

    private void setDescription(String description) {this.description = new Description(description); }

    private void setGenre(String genre) {this.genre = new Genre(genre); }

    public Book(String isbn, String title, String description, String genre) {
        setTitle(title);
        setIsbn(isbn);
        setDescription(description);
        setGenre(genre);
        //TODO: Set Author List
    }

    protected Book() {
        // got ORM only
    }

    public void applyPatch(UpdateBookRequest request) {
        String title = request.getTitle();
        String description = request.getDescription();
        String genre = request.getGenre();

        //TODO: Get the authors and set it on the object
        if(title != null) {
            setTitle(title);
        }

        if(description != null) {
            setDescription(description);
        }

        if(genre != null) {
            setGenre(genre);
        }
    }
}
