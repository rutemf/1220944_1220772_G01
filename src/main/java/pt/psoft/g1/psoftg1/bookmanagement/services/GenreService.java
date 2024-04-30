package pt.psoft.g1.psoftg1.bookmanagement.services;


import pt.psoft.g1.psoftg1.bookmanagement.model.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreService {

    List<Genre> findAllGenres();

    Genre save(Genre genre);
}
