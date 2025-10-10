package pt.psoft.g1.psoftg1.genremanagement.model;

import jakarta.persistence.Id;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import pt.psoft.g1.psoftg1.shared.services.IDGeneratorService;

@Getter
@Setter
@Document(collection = "genre")
public class GenreNoSQL {

    @Id
    private String id;

    @Size(min = 1, max = 100)
    private String genre;

    public GenreNoSQL(Genre genre) {
        IDGeneratorService IDGeneratorService = new IDGeneratorService();
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
