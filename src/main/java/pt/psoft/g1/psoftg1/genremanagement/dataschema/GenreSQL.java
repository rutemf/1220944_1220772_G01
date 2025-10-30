package pt.psoft.g1.psoftg1.genremanagement.dataschema;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import pt.psoft.g1.psoftg1.genremanagement.model.Genre;
import pt.psoft.g1.psoftg1.shared.services.IDGeneratorService;

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
        this.id = IDGeneratorService.generateIdSQL();
        this.genre = genre.getGenre();
    }

    // JPA
    protected GenreSQL() { }

    public Genre toDomain() {
        return new Genre(genre);
    }

    public static GenreSQL fromDomain(Genre domain) {
        return new GenreSQL(domain);
    }
}
