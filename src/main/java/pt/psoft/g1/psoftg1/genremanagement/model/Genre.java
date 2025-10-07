package pt.psoft.g1.psoftg1.genremanagement.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Genre {

    private final String id;
    private final String genre;
    private final int GENRE_MAX_LENGTH = 100;

    public Genre(String id, String genre) {
        this.id = id;
        validateGenre(genre);
        this.genre = genre;
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

    @Override
    public String toString() {
        return genre;
    }
}
