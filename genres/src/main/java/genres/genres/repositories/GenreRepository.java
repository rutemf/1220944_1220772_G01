package genres.genres.repositories;

import genres.genres.model.Genre;
import genres.genres.services.SearchGenreQuery;
import genres.shared.services.Page;
import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;

public interface GenreRepository {

    Genre save(Genre genre);
    void delete(Genre genre);
    List<Genre> searchGenres(Page page, SearchGenreQuery query);
    Genre findByGenreName(@Param("genre") String genreName);
}
