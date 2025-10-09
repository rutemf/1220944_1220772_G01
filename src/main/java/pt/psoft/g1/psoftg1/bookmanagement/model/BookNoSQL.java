package pt.psoft.g1.psoftg1.bookmanagement.model;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import pt.psoft.g1.psoftg1.authormanagement.model.AuthorNoSQL;
import pt.psoft.g1.psoftg1.genremanagement.model.GenreNoSQL;
import pt.psoft.g1.psoftg1.shared.services.Base65Service;

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
    private List<AuthorNoSQL> authors;

    private String photoURI;

    public BookNoSQL(Book book) {
        Base65Service base65Service = new Base65Service();
        this.id = base65Service.generateIdNoSQL();
        this.isbn = book.getIsbn();
        this.title = book.getTitle();
        this.description = book.getDescription();
        this.genre = book.getGenre() != null ? GenreNoSQL.fromDomain(book.getGenre()) : null;
        this.authors = book.getAuthors() != null ? book.getAuthors().stream().map(AuthorNoSQL::fromDomain).toList() : List.of();
        this.photoURI = book.getPhotoURI();
    }

    // NoSQL
    protected BookNoSQL() {}

    public Book toDomain() {
        return new Book(
                isbn != null ? isbn.toString() : null,
                title != null ? title.toString() : null,
                description != null ? description.toString() : null,
                genre != null ? genre.toDomain() : null,
                authors != null ? authors.stream().map(AuthorNoSQL::toDomain).toList() : List.of(),
                photoURI
        );
    }

    public BookNoSQL fromDomain(Book book) {
        return new BookNoSQL(book);
    }
}
