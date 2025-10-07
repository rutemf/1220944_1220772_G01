package pt.psoft.g1.psoftg1.bookmanagement.model;

import lombok.Getter;
import lombok.Setter;
import pt.psoft.g1.psoftg1.authormanagement.model.Author;
import pt.psoft.g1.psoftg1.genremanagement.model.Genre;
import pt.psoft.g1.psoftg1.shared.model.EntityWithPhoto;

import java.util.List;

@Getter
@Setter
public class Book extends EntityWithPhoto {

    private String id;
    private Isbn isbn;
    private Title title;
    private Genre genre;
    private List<Author> authors;
    private Description description;
    private String photoURI;

    public Book(String id, String isbn, String title, String description, Genre genre, List<Author> authors, String photoURI) {
        this.id = id;
        this.isbn = new Isbn(isbn);
        this.title = new Title(title);
        this.description = new Description(description);
        this.genre = genre;
        this.authors = authors;
        this.photoURI = photoURI;
    }

    @Override
    public String toString() {
        return title.toString();
    }
}
