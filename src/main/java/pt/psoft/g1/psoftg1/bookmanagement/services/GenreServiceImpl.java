package pt.psoft.g1.psoftg1;

import java.util.Optional;

import jakarta.validation.ValidationException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    @Override
    public Iterable<Genre> findAll() {
        return genreRepository.findAll();
    }

    @Override
    public Optional<Genre> findOne(final Genre genre) {
        return genreRepository.findById(id);
    }

    @Override
    public Genre create(final createGenreRequest resource) {
        // construct a new object based on data received by the service
        final Genre genre = genreEditMapper.create(resource);

        // TODO ensure domain invariants or does the Mapper runs validations?
        return genreRepository.save(genre);
    }
}