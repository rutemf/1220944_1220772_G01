package pt.psoft.g1.psoftg1.genremanagement.infrastructure.repositories.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Caching;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import pt.psoft.g1.psoftg1.bookmanagement.services.GenreBookCountDTO;
import pt.psoft.g1.psoftg1.genremanagement.model.Genre;
import pt.psoft.g1.psoftg1.genremanagement.dataschema.GenreSQL;
import pt.psoft.g1.psoftg1.genremanagement.repositories.GenreRepository;
import pt.psoft.g1.psoftg1.genremanagement.services.GenreLendingsDTO;
import pt.psoft.g1.psoftg1.genremanagement.services.GenreLendingsPerMonthDTO;

import java.time.LocalDate;
import java.util.*;

@Repository
@Profile("sql")
@CacheConfig(cacheNames = "genres")
public class GenreRepositorySQL implements GenreRepository {

    private final EntityManager entityManager;

    public GenreRepositorySQL(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Cacheable(key = "'allGenres'")
    public Iterable<Genre> findAll() {
        TypedQuery<GenreSQL> query = entityManager.createQuery(
        "SELECT g FROM GenreSQL g", GenreSQL.class);

        return query.getResultList().stream().map(GenreSQL::toDomain).toList();
    }

    @Override
    @Cacheable(key = "#genreName")
    public Optional<Genre> findByString(String genreName) {
        TypedQuery<GenreSQL> query = entityManager.createQuery(
        "SELECT g FROM GenreSQL g WHERE g.genre = :genreName", GenreSQL.class);

        query.setParameter("genreName", genreName);
        return query.getResultStream().map(GenreSQL::toDomain).findFirst();
    }

    @Override
    @Caching(evict = {
            @CacheEvict(key = "#genre.genre"),
            @CacheEvict(key = "'allGenres'")
    })
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
        "WHERE FUNCTION('MONTH', FUNCTION('STR_TO_DATE', l.startDate, '%Y-%m-%d')) = :monthMonth " +
        "AND FUNCTION('YEAR', FUNCTION('STR_TO_DATE', l.startDate, '%Y-%m-%d')) = :monthYear " +
        "GROUP BY g.genre", GenreLendingsDTO.class);

        query.setParameter("monthMonth", month.getMonthValue());
        query.setParameter("monthYear", month.getYear());

        return query.getResultList();
    }

    @Override
    public List<GenreLendingsPerMonthDTO> getLendingsPerMonthLastYearByGenre() {
        LocalDate oneYearAgo = LocalDate.now().minusYears(1);

        TypedQuery<Object[]> query = entityManager.createQuery(
        "SELECT g.genre, " +
        "  FUNCTION('YEAR',  FUNCTION('STR_TO_DATE', l.startDate, '%Y-%m-%d')), " +
        "  FUNCTION('MONTH', FUNCTION('STR_TO_DATE', l.startDate, '%Y-%m-%d')), " +
        "  COUNT(l) " +
        "FROM LendingSQL l JOIN l.book b JOIN b.genre g " +
        "WHERE FUNCTION('STR_TO_DATE', l.startDate, '%Y-%m-%d') >= :fromDate " +
        "GROUP BY g.genre, " +
        "  FUNCTION('YEAR',  FUNCTION('STR_TO_DATE', l.startDate, '%Y-%m-%d')), " +
        "  FUNCTION('MONTH', FUNCTION('STR_TO_DATE', l.startDate, '%Y-%m-%d')) " +
        "ORDER BY " +
        "  FUNCTION('YEAR',  FUNCTION('STR_TO_DATE', l.startDate, '%Y-%m-%d')), " +
        "  FUNCTION('MONTH', FUNCTION('STR_TO_DATE', l.startDate, '%Y-%m-%d')), " +
        "  g.genre", Object[].class);

        query.setParameter("fromDate", oneYearAgo);

        var rows = query.getResultList();

        Map<String, List<GenreLendingsDTO>> byYm = new LinkedHashMap<>();
        for (Object[] r : rows) {
            String genre = (String) r[0];
            int year     = ((Number) r[1]).intValue();
            int month    = ((Number) r[2]).intValue();
            long count   = ((Number) r[3]).longValue();

            String key = year + "-" + String.format("%02d", month);
            byYm.computeIfAbsent(key, __ -> new ArrayList<>()).add(new GenreLendingsDTO(genre, count));
        }

        List<GenreLendingsPerMonthDTO> out = new ArrayList<>();
        for (var e : byYm.entrySet()) {
            var ym = e.getKey().split("-");
            int y = Integer.parseInt(ym[0]);
            int m = Integer.parseInt(ym[1]);
            out.add(new GenreLendingsPerMonthDTO(y, m, e.getValue()));
        }

        out.sort(Comparator.comparingInt(GenreLendingsPerMonthDTO::getYear).thenComparingInt(GenreLendingsPerMonthDTO::getMonth));

        return out;
    }

    @Override
    public List<GenreLendingsPerMonthDTO> getLendingsAverageDurationPerMonth(LocalDate startDate, LocalDate endDate) {
        TypedQuery<Object[]> query = entityManager.createQuery(
        "SELECT g.genre, " +
        "  FUNCTION('YEAR',  FUNCTION('STR_TO_DATE', l.startDate, '%Y-%m-%d'))," +
        "  FUNCTION('MONTH', FUNCTION('STR_TO_DATE', l.startDate, '%Y-%m-%d'))," +
        "  AVG( FUNCTION('DATEDIFF', " +
        "        FUNCTION('COALESCE', FUNCTION('STR_TO_DATE', l.returnedDate, '%Y-%m-%d'), FUNCTION('CURRENT_DATE')), " +
        "        FUNCTION('STR_TO_DATE', l.startDate, '%Y-%m-%d')" +
        "      ) ) " +
        "FROM LendingSQL l JOIN l.book b JOIN b.genre g " +
        "WHERE FUNCTION('STR_TO_DATE', l.startDate, '%Y-%m-%d') BETWEEN :startDate AND :endDate " +
        "GROUP BY g.genre, " +
        "  FUNCTION('YEAR',  FUNCTION('STR_TO_DATE', l.startDate, '%Y-%m-%d')), " +
        "  FUNCTION('MONTH', FUNCTION('STR_TO_DATE', l.startDate, '%Y-%m-%d')) " +
        "ORDER BY " +
        "  FUNCTION('YEAR',  FUNCTION('STR_TO_DATE', l.startDate, '%Y-%m-%d'))," +
        "  FUNCTION('MONTH', FUNCTION('STR_TO_DATE', l.startDate, '%Y-%m-%d'))," +
        "  g.genre", Object[].class);

        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);

        List<Object[]> rows = query.getResultList();

        Map<String, List<GenreLendingsDTO>> byYearMonth = new LinkedHashMap<>();

        for (Object[] r : rows) {
            String genre = (String) r[0];
            int year     = ((Number) r[1]).intValue();
            int month    = ((Number) r[2]).intValue();
            double avg   = r[3] == null ? 0d : ((Number) r[3]).doubleValue();

            String key = year + "-" + String.format("%02d", month);
            byYearMonth.computeIfAbsent(key, __ -> new ArrayList<>()).add(new GenreLendingsDTO(genre, avg));
        }

        List<GenreLendingsPerMonthDTO> out = new ArrayList<>();
        for (Map.Entry<String, List<GenreLendingsDTO>> e : byYearMonth.entrySet()) {
            String[] ym = e.getKey().split("-");
            int y = Integer.parseInt(ym[0]);
            int m = Integer.parseInt(ym[1]);
            out.add(new GenreLendingsPerMonthDTO(y, m, e.getValue()));
        }

        out.sort(Comparator.comparingInt(GenreLendingsPerMonthDTO::getYear).thenComparingInt(GenreLendingsPerMonthDTO::getMonth));

        return out;
    }

    @Override
    @CacheEvict(key="#genre")
    public void delete(Genre genre) {
        GenreSQL entity = GenreSQL.fromDomain(genre);
        GenreSQL managed = entityManager.contains(entity) ? entity : entityManager.merge(entity);
        entityManager.remove(managed);
    }
}
