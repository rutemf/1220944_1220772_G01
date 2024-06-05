package pt.psoft.g1.psoftg1.bookmanagement.services;


import org.springframework.data.util.Pair;
import pt.psoft.g1.psoftg1.bookmanagement.model.Genre;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface GenreService {

    Iterable<Genre> findAll();

    Genre save(Genre genre);

    Optional<Genre> findByString(String name);

    List<GenreBookCountDTO> findTopGenreByBooks();

    List<Pair<Genre, String>> getAverageLendings(String period, LocalDate startDate, LocalDate endDate);
}
