package pt.psoft.g1.psoftg1.genremanagement.infrastructure.repositories.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import pt.psoft.g1.psoftg1.bookmanagement.services.GenreBookCountDTO;
import pt.psoft.g1.psoftg1.genremanagement.model.Genre;
import pt.psoft.g1.psoftg1.genremanagement.model.GenreSQL;
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
        TypedQuery<GenreSQL> query = entityManager.createQuery(
        "SELECT g FROM GenreSQL g", GenreSQL.class);

        return query.getResultList().stream().map(GenreSQL::toDomain).toList();
    }

    @Override
    public Optional<Genre> findByString(String genreName) {
        TypedQuery<GenreSQL> query = entityManager.createQuery(
        "SELECT g FROM GenreSQL g WHERE g.genre = :genreName", GenreSQL.class);

        query.setParameter("genreName", genreName);
        return query.getResultStream().map(GenreSQL::toDomain).findFirst();
    }

    @Override
    public Genre save(Genre genre) {
        GenreSQL entity = GenreSQL.fromDomain(genre);

        if (!entityManager.contains(entity)) {
            entityManager.persist(entity);
        } else {
            entityManager.merge(entity);
        }

        return entity.toDomain();
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
        GenreSQL entity = GenreSQL.fromDomain(genre);
        GenreSQL managed = entityManager.contains(entity) ? entity : entityManager.merge(entity);
        entityManager.remove(managed);
    }
}
