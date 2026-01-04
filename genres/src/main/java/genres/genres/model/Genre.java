package genres.genres.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;


@Document(collection = "genres")
public class Genre {

    private String id;

    @Getter
    private Long version;

    @Getter
    @NotBlank
    @Field("name")
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

        if (genre != null) {
            setGenre(genre);
        }
    }

    public void setGenre(String genre) {this.genre = genre;}

    protected Genre() {}

}
