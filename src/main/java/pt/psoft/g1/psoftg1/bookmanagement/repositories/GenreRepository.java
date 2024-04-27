package pt.psoft.g1.psoftg1;

import java.util.List;
import java.util.Optional;

public interface GenreRepository {

    Optional<Genre> findByName(Genre genre);

    List<Genre> findByName(Genre genre);

    Iterable<Genre> findAll();

    Genre save(Genre genre);
}