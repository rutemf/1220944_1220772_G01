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
        TypedQuery<GenreBookCountDTO> query = entityManager.createQuery(
        "SELECT new pt.psoft.g1.psoftg1.bookmanagement.services.GenreBookCountDTO(g.genre, COUNT(b)) " +
        "FROM BookSQL b JOIN b.genre g " +
        "GROUP BY g.genre " +
        "ORDER BY COUNT(b) DESC", GenreBookCountDTO.class);

        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        List<GenreBookCountDTO> results = query.getResultList();
        return new org.springframework.data.domain.PageImpl<>(results, pageable, results.size());
    }

    @Override
    public List<GenreLendingsDTO> getAverageLendingsInMonth(LocalDate month, pt.psoft.g1.psoftg1.shared.services.Page page) {
        TypedQuery<GenreLendingsDTO> query = entityManager.createQuery(
        "SELECT new pt.psoft.g1.psoftg1.genremanagement.services.GenreLendingsDTO(g.genre, COUNT(l)) " +
        "FROM LendingSQL l JOIN l.book b JOIN b.genre g " +
        "WHERE FUNCTION('MONTH', l.startDate) = :monthMonth AND FUNCTION('YEAR', l.startDate) = :monthYear " +
        "GROUP BY g.genre", GenreLendingsDTO.class);

        query.setParameter("monthMonth", month.getMonthValue());
        query.setParameter("monthYear", month.getYear());

        return query.getResultList();
    }

    @Override
    public List<GenreLendingsPerMonthDTO> getLendingsPerMonthLastYearByGenre() {
        LocalDate oneYearAgo = LocalDate.now().minusYears(1);

        TypedQuery<GenreLendingsPerMonthDTO> query = entityManager.createQuery(
        "SELECT new pt.psoft.g1.psoftg1.genremanagement.services.GenreLendingsPerMonthDTO(" +
        "g.genre, FUNCTION('MONTH', l.startDate), COUNT(l)) " +
        "FROM LendingSQL l JOIN l.book b JOIN b.genre g " +
        "WHERE l.startDate >= :startDate " +
        "GROUP BY g.genre, FUNCTION('MONTH', l.startDate) " +
        "ORDER BY g.genre, FUNCTION('MONTH', l.startDate)", GenreLendingsPerMonthDTO.class);

        query.setParameter("startDate", oneYearAgo);

        return query.getResultList();
    }

    @Override
    public List<GenreLendingsPerMonthDTO> getLendingsAverageDurationPerMonth(LocalDate startDate, LocalDate endDate) {
        TypedQuery<GenreLendingsPerMonthDTO> query = entityManager.createQuery(
        "SELECT new pt.psoft.g1.psoftg1.genremanagement.services.GenreLendingsPerMonthDTO(" +
        "g.genre, FUNCTION('MONTH', l.startDate), " +
        "AVG(FUNCTION('DATEDIFF', FUNCTION('COALESCE', l.returnedDate, FUNCTION('CURRENT_DATE')), l.startDate))) " +
        "FROM LendingSQL l JOIN l.book b JOIN b.genre g " +
        "WHERE l.startDate BETWEEN :startDate AND :endDate " +
        "GROUP BY g.genre, FUNCTION('MONTH', l.startDate) " +
        "ORDER BY g.genre, FUNCTION('MONTH', l.startDate)", GenreLendingsPerMonthDTO.class);

        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);

        return query.getResultList();
    }

    @Override
    public void delete(Genre genre) {
        GenreSQL entity = GenreSQL.fromDomain(genre);
        GenreSQL managed = entityManager.contains(entity) ? entity : entityManager.merge(entity);
        entityManager.remove(managed);
    }
}
