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
    @Column(unique=true)
    String genre;

    @OneToMany(mappedBy = "genre")
    List<Book> bookList;

    public Collection<Book> getBookList() {
        return bookList;
    }

    public void setBookList(Collection<Book> bookList) {
        this.bookList = (List<Book>) bookList;
    }

    protected Genre(){}
}
