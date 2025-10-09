package pt.psoft.g1.psoftg1.bookmanagement.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import pt.psoft.g1.psoftg1.authormanagement.model.Author;
import pt.psoft.g1.psoftg1.genremanagement.model.GenreSQL;

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

    @Embedded
    private Title title;

    @Embedded
    private Description description;

    @ManyToOne
    private GenreSQL genre;

    @ManyToMany
    private List<Author> authors;

    private String photoURI;

    public BookSQL(Book book, GenreSQL genreSQL) {
        this.isbn = book.getIsbn();
        this.title = book.getTitle();
        this.description = book.getDescription();
        this.genre = genreSQL;
        this.authors = book.getAuthors();
        this.photoURI = book.getPhotoURI();
    }

    // JPA
    protected BookSQL() {}

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

    public static BookSQL fromDomain(Book book, GenreSQL genreSQL) {
        return new BookSQL(book, genreSQL);
    }
}
