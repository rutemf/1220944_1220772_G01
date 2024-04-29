package pt.psoft.g1.psoftg1.bookmanagement.services;


import pt.psoft.g1.psoftg1.bookmanagement.model.Genre;

import java.util.Optional;

public interface GenreService {

    Iterable<Genre> findAll();

    Optional<Genre> findOne(Genre genre);
}
