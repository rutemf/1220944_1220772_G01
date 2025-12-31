package readers.genres.infrastructure.repositories.impl;

import readers.genres.repositories.GenreRepository;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import readers.genres.model.Genre;

import java.util.*;

public interface SpringDataGenreRepository extends GenreRepository, CrudRepository<Genre, Integer> {

    @Override
    @Query("SELECT g FROM Genre g WHERE g.genre = :genreName")
    Optional<Genre> findByString(@Param("genreName") @NotNull String genre);
}