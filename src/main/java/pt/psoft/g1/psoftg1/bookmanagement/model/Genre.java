package pt.psoft.g1.psoftg1.bookmanagement.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.util.Collection;
import java.util.List;

@Entity
@Table
public class Genre {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    int id;

    //TODO: verify domain rules to genre
    @Size(min = 1, max = 100, message = "Genre name must be between 1 and 100 characters")
    @Column(unique=true, nullable=false)
    String genre;

    //TODO: This will imply having the objects always up to date when a book is updated
    @OneToMany(mappedBy = "genre")
    List<Book> bookList;

    public Collection<Book> getBookList() {
        return bookList;
    }

    public void setBookList(Collection<Book> bookList) {
        this.bookList = (List<Book>) bookList;
    }

    protected Genre(){}

    public Genre(String genre) {
        setGenre(genre);
    }

    private void setGenre(String genre) {
        this.genre = genre;
    }

    public String toString() {
        return genre;
    }
}
