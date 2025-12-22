package genres.genres.services;

import genres.genres.api.GenreViewAMQP;
import genres.genres.model.Genre;
import genres.shared.services.Page;

import java.util.List;

public interface GenreService {

    Genre create(CreateGenreRequest request);
    Genre create(GenreViewAMQP genreViewAMQP);

    Genre update(CreateGenreRequest request);
    Genre update(GenreViewAMQP genreViewAMQP);

    Genre findByGenre(String genre);

    List<Genre> searchGenres(Page page, SearchGenreQuery query);

}
