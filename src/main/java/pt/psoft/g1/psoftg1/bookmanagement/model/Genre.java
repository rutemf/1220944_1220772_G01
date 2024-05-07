package pt.psoft.g1.psoftg1.bookmanagement.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.util.Collection;
import java.util.List;

@Entity
@Table
public class Genre {
    @Transient
    private final int GENRE_MAX_LENGTH = 100;
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    long pk;

    @Size(min = 1, max = GENRE_MAX_LENGTH, message = "Genre name must be between 1 and 100 characters")
    @Column(unique=true, nullable=false, length = GENRE_MAX_LENGTH)
    String genre;

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
