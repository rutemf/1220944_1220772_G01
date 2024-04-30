package pt.psoft.g1.psoftg1.bookmanagement.services;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pt.psoft.g1.psoftg1.bookmanagement.model.Genre;
import pt.psoft.g1.psoftg1.bookmanagement.repositories.GenreRepository;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    @Override
    public List<Genre> findAllGenres() {
        return genreRepository.findAllGenre();
    }

    /*@Override
    public Genre create(final CreateGenreRequest resource) {
        // construct a new object based on data received by the service
        final Genre genre = genreEditMapper.create(resource);

        // TODO ensure domain invariants or does the Mapper runs validations?
        return genreRepository.save(genre);
    }*/

    @Override
    public Genre save(Genre genre) {
        return this.genreRepository.save(genre);
    }
}
