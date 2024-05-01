package pt.psoft.g1.psoftg1.bookmanagement.services;


import pt.psoft.g1.psoftg1.bookmanagement.model.Genre;

import java.util.List;

public interface GenreService {

    Iterable<Genre> findAll();

    Genre save(Genre genre);
}
