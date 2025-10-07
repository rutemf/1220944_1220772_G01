package pt.psoft.g1.psoftg1.bookmanagement.model;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import pt.psoft.g1.psoftg1.authormanagement.model.Author;
import pt.psoft.g1.psoftg1.genremanagement.model.GenreNoSQL;

import java.util.List;

@Getter
@Setter
@Document(collection = "books")
public class BookNoSQL {

    @Id
    private String id;

    private Isbn isbn;
    private Title title;
    private Description description;

    @DBRef
    private GenreNoSQL genre;

    @DBRef
    private List<Author> authors;

    private String photoURI;

    public BookNoSQL(Book book, GenreNoSQL genreNoSQL) {
        this.id = book.getId();
        this.isbn = book.getIsbn();
        this.title = book.getTitle();
        this.description = book.getDescription();
        this.genre = genreNoSQL;
        this.authors = book.getAuthors();
        this.photoURI = book.getPhotoURI();
    }

    // NoSQL
    protected BookNoSQL() {}

    public Book toDomain() {
        return new Book(
                id,
                isbn.toString(),
                title.toString(),
                description != null ? description.toString() : null,
                genre != null ? genre.toDomain() : null,
                authors,
                photoURI
        );
    }

    public BookNoSQL fromDomain(Book book, GenreNoSQL genreNoSQL) {
        return new BookNoSQL(book, genreNoSQL);
    }
}
