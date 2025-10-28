package pt.psoft.g1.psoftg1.bookmanagement.model;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import pt.psoft.g1.psoftg1.authormanagement.model.AuthorNoSQL;
import pt.psoft.g1.psoftg1.authormanagement.model.AuthorSQL;
import pt.psoft.g1.psoftg1.genremanagement.model.GenreNoSQL;
import pt.psoft.g1.psoftg1.shared.services.IDGeneratorService;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Document(collection = "books")
public class BookNoSQL {

    @Id
    private String id;

    private String isbn;
    private String title;
    private String description;

    @DBRef
    private GenreNoSQL genre;

    @DBRef
    private List<AuthorNoSQL> authors;

    private String photoURI;

    public BookNoSQL(Book book) {
        this.id = IDGeneratorService.generateIdNoSQL();
        this.isbn = book.getIsbn().toString();
        this.title = book.getTitle().toString();
        this.description = book.getDescription().toString();
        this.genre = book.getGenre() != null ? GenreNoSQL.fromDomain(book.getGenre()) : null;
        this.authors = book.getAuthors() != null ? book.getAuthors().stream().map(AuthorNoSQL::fromDomain).toList() : List.of();
        this.photoURI = book.getPhotoURI();
    }

    // NoSQL
    protected BookNoSQL() {}

    public Book toDomain() {
        return new Book(
                isbn,
                title,
                description != null ? description : null,
                genre != null ? genre.toDomain() : null,
                authors != null ? authors.stream().map(AuthorNoSQL::toDomain).toList() : null,
                photoURI
        );
    }


    public static BookNoSQL fromDomain(Book book) {
        return new BookNoSQL(book);
    }
}
