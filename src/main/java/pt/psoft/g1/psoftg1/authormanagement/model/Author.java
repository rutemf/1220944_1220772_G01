package pt.psoft.g1.psoftg1.authormanagement.model;

import jakarta.persistence.*;
import pt.psoft.g1.psoftg1.bookmanagement.model.Book;
import pt.psoft.g1.psoftg1.usermanagement.model.Name;

import java.util.List;

@Entity
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="AUTHOR_NUMBER")
    private Long authorNumber;

    @Embedded
    Name name;

    @OneToOne(fetch=FetchType.LAZY)
    Photo photo;

    @Embedded
    Bio bio;

    @ManyToMany
    @JoinTable(
            name="BOOK_AUTHOR",
            joinColumns=@JoinColumn(name="AUTHOR_ID", referencedColumnName="AUTHOR_NUMBER"),
            inverseJoinColumns=@JoinColumn(name="BOOK_ID", referencedColumnName="ISBN"))
    private List<Book> books;

    protected Author() {
        // for ORM only
    }
}
