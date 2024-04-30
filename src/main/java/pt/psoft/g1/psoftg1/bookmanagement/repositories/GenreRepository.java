package pt.psoft.g1.psoftg1.bookmanagement.repositories;

import pt.psoft.g1.psoftg1.bookmanagement.model.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreRepository {

    List<Genre> findByName(Genre genre);

    Iterable<Genre> findAll();

    Genre save(Genre genre) throws Exception;
}
