package pt.psoft.g1.psoftg1.genremanagement.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "genre")
public class GenreSQL {

    @Id
    private String id;

    @Column(unique = true, nullable = false, length = 100)
    @Size(min = 1, max = 100)
    private String genre;

    public GenreSQL(String id, String genre) {
        this.id = id;
        this.genre = genre;
    }

    // JPA
    protected GenreSQL() {}

    // -------------------
    // Mappers
    // -------------------

    public Genre toDomain() {
        return new Genre(id, genre);
    }

    public static GenreSQL fromDomain(Genre domain) {
        return new GenreSQL(domain.getId(), domain.getGenre());
    }
}
