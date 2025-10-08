package pt.psoft.g1.psoftg1.genremanagement.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import pt.psoft.g1.psoftg1.shared.services.Base65Service;

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

    public GenreSQL(Genre genre) {
        Base65Service base65Service = new Base65Service();
        this.id = base65Service.generateId();
        this.genre = genre.getGenre();
    }

    // JPA
    protected GenreSQL() {}

    public Genre toDomain() {
        return new Genre(id, genre);
    }

    public static GenreSQL fromDomain(Genre domain) {
        return new GenreSQL(domain);
    }
}
