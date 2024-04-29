package pt.psoft.g1.psoftg1.bookmanagement.services;


import org.hibernate.validator.constraints.ISBN;
import pt.psoft.g1.psoftg1.CreateBookRequest;
import pt.psoft.g1.psoftg1.EditBookRequest;
import pt.psoft.g1.psoftg1.bookmanagement.model.Book;
import pt.psoft.g1.psoftg1.bookmanagement.model.Genre;
import pt.psoft.g1.psoftg1.bookmanagement.model.Isbn;

import java.util.Optional;

public interface GenreService {

    Iterable<Genre> findAll();

    Optional<Genre> findOne(Genre genre);
}
