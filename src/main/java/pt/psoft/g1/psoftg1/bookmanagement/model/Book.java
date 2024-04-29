package pt.psoft.g1.psoftg1.bookmanagement.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import pt.psoft.g1.psoftg1.authormanagement.model.Author;


import java.util.List;

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

    @ManyToMany(mappedBy="books")
    private List<Author> authors;

    @Embedded
    Description description;

    public Book(String isbn, String title) {
        setIsbn(isbn);
        setTitle(title);
    }

    public void setTitle(String title) {
        this.title = new Title(title);
    }

    public void setIsbn(String isbn) {
        this.isbn = new Isbn(isbn);
    }

    protected Book() {
        // got ORM only
    }
}
