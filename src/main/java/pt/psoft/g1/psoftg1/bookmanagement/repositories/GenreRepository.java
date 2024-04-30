package pt.psoft.g1.psoftg1.bookmanagement.repositories;

import pt.psoft.g1.psoftg1.bookmanagement.model.Genre;

import java.util.List;

public interface GenreRepository {

    List<Genre> findAllGenre();

    Genre save(Genre genre);
}
