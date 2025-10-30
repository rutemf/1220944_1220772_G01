package pt.psoft.g1.psoftg1.genremanagement.dataschema;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import pt.psoft.g1.psoftg1.genremanagement.model.Genre;
import pt.psoft.g1.psoftg1.shared.services.IDGeneratorService;

@Getter
@Setter
@Document(collection = "genres")
public class GenreNoSQL {

    @Id
    private String id;

    private String genre;

    public GenreNoSQL(Genre genre) {
        this.id = IDGeneratorService.generateIdNoSQL();
        this.genre = genre.getGenre();
    }

    // NoSQL
    protected GenreNoSQL() { }

    public Genre toDomain() {
        return new Genre(genre);
    }

    public static GenreNoSQL fromDomain(Genre domain) {
        return new GenreNoSQL(domain);
    }
}
