package pt.psoft.g1.psoftg1.bookmanagement.infrastructure.repositories.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Tuple;
import jakarta.persistence.criteria.*;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.util.Pair;
import pt.psoft.g1.psoftg1.bookmanagement.model.Book;
import pt.psoft.g1.psoftg1.bookmanagement.model.Genre;
import pt.psoft.g1.psoftg1.bookmanagement.services.GenreBookCountDTO;
import pt.psoft.g1.psoftg1.bookmanagement.repositories.GenreRepository;
import pt.psoft.g1.psoftg1.lendingmanagement.model.Lending;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface SpringDataGenreRepository extends GenreRepository, GenreRepoCustom, CrudRepository<Genre, Integer> {

    @Query("SELECT g FROM Genre g")
    List<Genre> findAllGenres();

    @Override
    @Query("SELECT g FROM Genre g WHERE g.genre = :genreName" )
    Optional<Genre> findByString(@Param("genreName")@NotNull String genre);

    @Override
    @Query("SELECT new pt.psoft.g1.psoftg1.bookmanagement.services.GenreBookCountDTO(g.genre, COUNT(b))" +
            "FROM Genre g " +
            "JOIN Book b ON b.genre.pk = g.pk " +
            "GROUP BY g " +
            "ORDER BY COUNT(b) DESC")
    Page<GenreBookCountDTO> findTop5GenreByBookCount(Pageable pageable);
}


interface GenreRepoCustom{
    List<Pair<Genre, Double>> getAverageLendings(String period, LocalDate startDate, LocalDate endDate);
}

@RequiredArgsConstructor
class GenreRepoCustomImpl implements GenreRepoCustom {

    private final EntityManager entityManager;

    @Override
    public List<Pair<Genre, Double>> getAverageLendings(String period, LocalDate startDate, LocalDate endDate) {

        int diff = switch (period.toLowerCase()) {
            case "days", "day" -> (int) ChronoUnit.DAYS.between(startDate, endDate)+1;
            case "weeks", "week" -> (int) ChronoUnit.WEEKS.between(startDate, endDate)+1;
            case "months", "month" -> (int) ChronoUnit.MONTHS.between(startDate, endDate)+1;
            case "years", "year" -> (int) ChronoUnit.YEARS.between(startDate, endDate)+1;
            default -> 1;
        };

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tuple> query = cb.createQuery(Tuple.class);

        Root<Lending> lendingRoot = query.from(Lending.class);
        Join<Lending, Book> bookJoin = lendingRoot.join("book", JoinType.LEFT);
        Join<Book, Genre> genreJoin = bookJoin.join("genre", JoinType.LEFT);

        Expression<Long> loanCount = cb.count(lendingRoot.get("pk"));
        Expression<Number> dailyAvgLoans = cb.quot(cb.toDouble(loanCount), cb.literal(diff));

        query.multiselect(genreJoin, dailyAvgLoans);
        query.groupBy(genreJoin.get("pk"));

        Predicate startDatePredicate = cb.greaterThanOrEqualTo(lendingRoot.get("startDate"), startDate);
        Predicate endDatePredicate = cb.lessThanOrEqualTo(lendingRoot.get("startDate"), endDate);

        Predicate finalPredicate = cb.and(startDatePredicate, endDatePredicate);

        query.where(finalPredicate);

        List<Tuple> results = entityManager.createQuery(query).getResultList();
        List<Pair<Genre, Double>> avgLendings = new ArrayList<>();

        for (Tuple result : results) {
            Genre genre = result.get(0, Genre.class);
            Double avg = result.get(1, Double.class);
            avgLendings.add(Pair.of(genre, avg));
        }

        return avgLendings;
    }
}