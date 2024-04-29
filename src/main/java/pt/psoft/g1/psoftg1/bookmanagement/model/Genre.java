package pt.psoft.g1.psoftg1.bookmanagement.model;

import jakarta.persistence.*;

import java.util.Collection;
import java.util.List;

@Entity
@Table
public class Genre {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    int id;

    //TODO: verify domain rules to genre
    @Column(unique=true)
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
