package pt.psoft.g1.psoftg1.bookmanagement.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import pt.psoft.g1.psoftg1.authormanagement.model.Author;


import java.util.List;

@Entity
public class Book {
    @EmbeddedId
    @Column(name="ISBN")
    Isbn isbn;

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


    protected Book() {
        // got ORM only
    }
}
