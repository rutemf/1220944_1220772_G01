package books.genres.repositories;

import books.genres.model.Genre;

import java.util.Optional;

public interface GenreRepository {

    Optional<Genre> findByString(String genreName);
    Genre save(Genre genre);

}
