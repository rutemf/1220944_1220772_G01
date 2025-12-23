package genres.genres.services;

import genres.exceptions.ConflictException;
import genres.genres.api.GenreViewAMQP;
import genres.genres.model.Genre;
import genres.genres.publishers.GenreEventsPublisher;
import genres.genres.repositories.GenreRepository;
import genres.shared.services.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    private final GenreEventsPublisher genreEventsPublisher;

    @Override
    public Genre create(CreateGenreRequest request) {

        final String genre = request.getGenre();

        Genre savedGenre = create(genre);

        if (savedGenre != null) {
            genreEventsPublisher.sendGenreCreated(savedGenre);
        }
        return savedGenre;
    }

    @Override
    public Genre create(GenreViewAMQP genreViewAMQP) {
        final String genre = genreViewAMQP.getGenre();
        return create(genre);
    }

    private Genre create(String genreName) {

        if (genreRepository.findByGenreName(genreName) != null) {
            throw new ConflictException("Genre " + genreName + " already exists");
        }

        Genre newGenre= new Genre(genreName);

        return genreRepository.save(newGenre);
    }

    @Override
    public Genre update(UpdateGenreRequest request, Long currentVersion) {

        Genre genre = genreRepository.findByGenreName(request.getGenre());

        String genreName = request.getGenre();

        Genre updatedGenre = update(genre, currentVersion, genreName);

        if (updatedGenre != null) {
            genreEventsPublisher.sendGenreUpdated(updatedGenre, currentVersion);
        }

        return updatedGenre;
    }

    @Override
    public Genre update(GenreViewAMQP genreViewAMQP) {

        final Long version = genreViewAMQP.getVersion();
        final String genreName = genreViewAMQP.getGenre();

        Genre genre = genreRepository.findByGenreName(genreName);

        return update(genre, version, genreName);
    }

    private Genre update(Genre genre, Long currentVersion, String genreName) {

        if (!genre.getGenre().equalsIgnoreCase(genreName)) {

            boolean exists = genreRepository
                    .findByGenreName(genreName) != null;

            if (exists) {
                throw new IllegalArgumentException(
                        "Already existing gender: " + genreName
                );
            }
        }
        genre.applyPatch(currentVersion, genreName);

        return genreRepository.save(genre);
    }

    @Override
    public Genre findByGenre(String genre) {
        return genreRepository.findByGenreName(genre);
    }

    @Override
    public List<Genre> searchGenres(Page page, SearchGenreQuery query) {

        if (page == null) {
            page = new Page(1, 10);
        }
        if (query == null) {
            query = new SearchGenreQuery("");
        }
        return genreRepository.searchGenres(page, query);
    }
}
