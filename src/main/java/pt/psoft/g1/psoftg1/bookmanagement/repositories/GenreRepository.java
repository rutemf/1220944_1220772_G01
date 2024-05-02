package pt.psoft.g1.psoftg1.bookmanagement.repositories;

import pt.psoft.g1.psoftg1.bookmanagement.model.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreRepository {

    Iterable<Genre> findAll();
    Optional<Genre> findByString(String genreName);
    Genre save(Genre genre);
}
