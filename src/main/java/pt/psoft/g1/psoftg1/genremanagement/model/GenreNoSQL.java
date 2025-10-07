package pt.psoft.g1.psoftg1.genremanagement.model;

import jakarta.persistence.Id;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "genre")
public class GenreNoSQL {

    @Id
    private String id;

    @Size(min = 1, max = 100)
    private String genre;

    public GenreNoSQL(String id, String genre) {
        this.id = id;
        this.genre = genre;
    }

    // NoSQL
    protected GenreNoSQL() {
    }

    public Genre toDomain() {
        return new Genre(id, genre);
    }

    public GenreNoSQL fromDomain(Genre genre) {
        return new GenreNoSQL(genre.getId(), genre.getGenre());
    }
}
