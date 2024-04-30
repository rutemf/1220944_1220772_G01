package pt.psoft.g1.psoftg1.bookmanagement.infrastructure.repositories.impl;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import pt.psoft.g1.psoftg1.bookmanagement.model.Genre;
import pt.psoft.g1.psoftg1.bookmanagement.repositories.GenreRepository;

import java.util.List;

public interface SpringDataGenreRepository extends GenreRepository, CrudRepository<Genre, String> {

    @Query("SELECT DISTINCT g FROM Genre g")
    List<Genre> findAllGenres();
}
