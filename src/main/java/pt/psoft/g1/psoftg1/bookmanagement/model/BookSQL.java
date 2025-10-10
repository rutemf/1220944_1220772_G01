package pt.psoft.g1.psoftg1.bookmanagement.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import pt.psoft.g1.psoftg1.authormanagement.model.AuthorSQL;
import pt.psoft.g1.psoftg1.genremanagement.model.GenreSQL;
import pt.psoft.g1.psoftg1.shared.services.IDGeneratorService;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "Book", uniqueConstraints = @UniqueConstraint(columnNames = "isbn"))
public class BookSQL {

    @Id
    private String id;

    @Embedded
    private Isbn isbn;

    private String title;

    private String description;

    @ManyToOne
    private GenreSQL genre;

    @ManyToMany
    private List<AuthorSQL> authors;

    private String photoURI;

    public BookSQL(Book book) {
        IDGeneratorService IDGeneratorService = new IDGeneratorService();
        this.id = IDGeneratorService.generateIdSQL();
        this.isbn = book.getIsbn();
        this.title = book.getTitle().toString();
        this.description = book.getDescription().toString();
        this.genre = book.getGenre() != null ? GenreSQL.fromDomain(book.getGenre()) : null;
        this.authors = book.getAuthors().stream().map(AuthorSQL::fromDomain).toList();
        this.photoURI = book.getPhotoURI();
    }

    // JPA
    protected BookSQL() {}

    public Book toDomain() {
        return new Book(
                isbn.toString(),
                title,
                description != null ? description : null,
                genre != null ? genre.toDomain() : null,
                authors != null ? authors.stream().map(AuthorSQL::toDomain).toList() : null,
                photoURI
        );
    }

    public static BookSQL fromDomain(Book book) {
        return new BookSQL(book);
    }
}
