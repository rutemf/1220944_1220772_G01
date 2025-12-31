package genres.genres.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import org.hibernate.StaleObjectStateException;

import java.util.Objects;

@Entity
@Table(name = "Genre")
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    long pk;

    @Version
    @Getter
    private Long version;

    @Getter
    @NotBlank
    private String genre;

    private final int GENRE_MAX_LENGTH = 100;

    public Genre(String genre) {
        validateGenre(genre);
        setGenre(genre);
    }
    private void validateGenre(String genreName) {
        if (genreName == null) {
            throw new IllegalArgumentException("Genre cannot be null.");
        }
        if (genreName.isBlank()) {
            throw new IllegalArgumentException("Genre cannot be blank.");
        }
        if (genreName.length() > GENRE_MAX_LENGTH) {
            throw new IllegalArgumentException("Genre has a maximum of 100 characters.");
        }
    }

    public void applyPatch(final Long desiredVersion,
                           final String genre) {

        if (!Objects.equals(this.version, desiredVersion))
            throw new StaleObjectStateException("Object was already modified by another user", this.pk);

        if (genre != null) {
            setGenre(genre);
        }
    }

    public void setGenre(String genre) {this.genre = genre;}

    protected Genre() {}

}
