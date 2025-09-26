package pt.psoft.g1.psoftg1.genremanagement.infrastructure.repositories.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import pt.psoft.g1.psoftg1.bookmanagement.services.GenreBookCountDTO;
import pt.psoft.g1.psoftg1.genremanagement.model.Genre;
import pt.psoft.g1.psoftg1.genremanagement.repositories.GenreRepository;
import pt.psoft.g1.psoftg1.genremanagement.services.GenreLendingsDTO;
import pt.psoft.g1.psoftg1.genremanagement.services.GenreLendingsPerMonthDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@Profile("sql")
public class GenreRepositorySQL implements GenreRepository {

    private final EntityManager entityManager;

    public GenreRepositorySQL(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Iterable<Genre> findAll() {
        TypedQuery<Genre> query = entityManager.createQuery(
        "SELECT g FROM Genre g", Genre.class);

        return query.getResultList();
    }

    @Override
    public Optional<Genre> findByString(String genreName) {
        TypedQuery<Genre> query = entityManager.createQuery(
        "SELECT g FROM Genre g WHERE g.genre = :genreName", Genre.class);

        query.setParameter("genreName", genreName);
        return query.getResultStream().findFirst();
    }

    @Override
    public Genre save(Genre genre) {
        return null;
    }

    @Override
    public Page<GenreBookCountDTO> findTop5GenreByBookCount(Pageable pageable) {
        return null;
    }

    @Override
    public List<GenreLendingsDTO> getAverageLendingsInMonth(LocalDate month, pt.psoft.g1.psoftg1.shared.services.Page page) {
        return List.of();
    }

    @Override
    public List<GenreLendingsPerMonthDTO> getLendingsPerMonthLastYearByGenre() {
        return List.of();
    }

    @Override
    public List<GenreLendingsPerMonthDTO> getLendingsAverageDurationPerMonth(LocalDate startDate, LocalDate endDate) {
        return List.of();
    }

    @Override
    public void delete(Genre genre) {
        Genre managed = entityManager.contains(genre) ? genre : entityManager.merge(genre);
        entityManager.remove(managed);
    }
}
