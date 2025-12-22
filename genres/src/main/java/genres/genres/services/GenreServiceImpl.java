package genres.genres.services;

import genres.genres.api.GenreViewAMQP;
import genres.genres.model.Genre;
import genres.shared.services.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    @Override
    public Genre create(CreateGenreRequest request) {

        return null;
    }

    @Override
    public Genre create(GenreViewAMQP genreViewAMQP) {


        return null;
    }


    private Genre create(String genreName) {

       return null;
    }
    @Override
    public Genre update(CreateGenreRequest request) {
        return null;
    }

    @Override
    public Genre update(GenreViewAMQP genreViewAMQP) {


        return null;
    }

    @Override
    public Genre findByGenre(String genre) {
        return null;

    }

    @Override
    public List<Genre> searchGenres(Page page, SearchGenreQuery query) {
        return null;
    }
}
