package pt.psoft.g1.psoftg1.genremanagement.infrastructure.repositories.impl;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.context.annotation.Profile;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import pt.psoft.g1.psoftg1.bookmanagement.services.GenreBookCountDTO;
import pt.psoft.g1.psoftg1.genremanagement.model.Genre;
import pt.psoft.g1.psoftg1.genremanagement.model.GenreNoSQL;
import pt.psoft.g1.psoftg1.genremanagement.repositories.GenreRepository;
import pt.psoft.g1.psoftg1.genremanagement.services.GenreLendingsDTO;
import pt.psoft.g1.psoftg1.genremanagement.services.GenreLendingsPerMonthDTO;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

@Repository
@Profile("nosql")
@CacheConfig(cacheNames = "genres")
public class GenreRepositoryNoSQL implements GenreRepository {

    private final MongoTemplate mongoTemplate;

    public GenreRepositoryNoSQL(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    @Cacheable(key = "'allGenres'")
    public Iterable<Genre> findAll() {
        return mongoTemplate.findAll(GenreNoSQL.class)
                .stream()
                .map(GenreNoSQL::toDomain)
                .toList();
    }


    @Override
    @Cacheable(key = "#genreName")
    public Optional<Genre> findByString(String genreName) {
        Query query = new Query(Criteria.where("genre").is(genreName));
        GenreNoSQL result = mongoTemplate.findOne(query, GenreNoSQL.class);
        return Optional.ofNullable(result).map(GenreNoSQL::toDomain);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(key = "#genre.genre"),
            @CacheEvict(key = "'allGenres'")
    })
    public Genre save(Genre genre) {
        GenreNoSQL entity = GenreNoSQL.fromDomain(genre);
        mongoTemplate.save(entity);
        return entity.toDomain();
    }

    @Override
    public Page<GenreBookCountDTO> findTop5GenreByBookCount(Pageable pageable) {
        Aggregation agg = Aggregation.newAggregation(
                Aggregation.lookup("books", "genre", "genre", "books"),
                Aggregation.unwind("books", true),
                Aggregation.group("genre").count().as("bookCount"),
                Aggregation.project("bookCount").and("_id").as("genre"),
                Aggregation.sort(org.springframework.data.domain.Sort.Direction.DESC, "bookCount"),
                Aggregation.skip(pageable.getOffset()),
                Aggregation.limit(pageable.getPageSize())
        );

        List<GenreBookCountDTO> results = mongoTemplate.aggregate(agg, "genres", GenreBookCountDTO.class)
                .getMappedResults();

        return new PageImpl<>(results, pageable, results.size());
    }

    @Override
    public List<GenreLendingsDTO> getAverageLendingsInMonth(LocalDate month, pt.psoft.g1.psoftg1.shared.services.Page page) {
        int year = month.getYear();
        int monthValue = month.getMonthValue();

        Aggregation agg = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("startDate").gte(LocalDate.of(year, monthValue, 1))
                        .lt(LocalDate.of(year, monthValue, 1).plusMonths(1))),
                Aggregation.lookup("books", "bookId", "_id", "book"),
                Aggregation.unwind("book"),
                Aggregation.lookup("genres", "book.genreId", "_id", "genre"),
                Aggregation.unwind("genre"),
                Aggregation.group("genre.genre").count().as("lendingsCount"),
                Aggregation.project("lendingsCount").and("_id").as("genre")
        );

        return mongoTemplate.aggregate(agg, "lendings", GenreLendingsDTO.class).getMappedResults();
    }

    @Override
    public List<GenreLendingsPerMonthDTO> getLendingsPerMonthLastYearByGenre() {
        LocalDate oneYearAgo = LocalDate.now().minusYears(1);

        Aggregation agg = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("startDate").gte(oneYearAgo)),
                Aggregation.lookup("books", "bookId", "_id", "book"),
                Aggregation.unwind("book"),
                Aggregation.lookup("genres", "book.genreId", "_id", "genre"),
                Aggregation.unwind("genre"),
                Aggregation.project()
                        .andExpression("year(startDate)").as("year")
                        .andExpression("month(startDate)").as("month")
                        .and("genre.genre").as("genre"),
                Aggregation.group("genre", "year", "month").count().as("count"),
                Aggregation.sort(org.springframework.data.domain.Sort.Direction.ASC, "year", "month", "genre")
        );

        var results = mongoTemplate.aggregate(agg, "lendings", Map.class).getMappedResults();

        Map<YearMonth, List<GenreLendingsDTO>> byYm = new LinkedHashMap<>();
        for (var r : results) {
            String genre = (String) r.get("_id.genre");
            int year = (int) r.get("_id.year");
            int month = (int) r.get("_id.month");
            long count = ((Number) r.get("count")).longValue();

            YearMonth ym = YearMonth.of(year, month);
            byYm.computeIfAbsent(ym, __ -> new ArrayList<>()).add(new GenreLendingsDTO(genre, count));
        }

        return byYm.entrySet().stream()
                .map(e -> new GenreLendingsPerMonthDTO(e.getKey().getYear(), e.getKey().getMonthValue(), e.getValue()))
                .sorted(Comparator.comparingInt(GenreLendingsPerMonthDTO::getYear)
                        .thenComparingInt(GenreLendingsPerMonthDTO::getMonth))
                .collect(Collectors.toList());
    }

    @Override
    public List<GenreLendingsPerMonthDTO> getLendingsAverageDurationPerMonth(LocalDate startDate, LocalDate endDate) {
        Aggregation agg = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("startDate").gte(startDate).lte(endDate)),
                Aggregation.lookup("books", "bookId", "_id", "book"),
                Aggregation.unwind("book"),
                Aggregation.lookup("genres", "book.genreId", "_id", "genre"),
                Aggregation.unwind("genre"),
                Aggregation.project()
                        .andExpression("year(startDate)").as("year")
                        .andExpression("month(startDate)").as("month")
                        .and("genre.genre").as("genre")
                        .andExpression("cond(returnedDate != null, (returnedDate - startDate), (now() - startDate))")
                        .as("duration"),
                Aggregation.group("genre", "year", "month").avg("duration").as("avgDuration"),
                Aggregation.sort(org.springframework.data.domain.Sort.Direction.ASC, "year", "month", "genre")
        );

        var results = mongoTemplate.aggregate(agg, "lendings", Map.class).getMappedResults();

        Map<YearMonth, List<GenreLendingsDTO>> byYm = new LinkedHashMap<>();
        for (var r : results) {
            String genre = (String) r.get("_id.genre");
            int year = (int) r.get("_id.year");
            int month = (int) r.get("_id.month");
            double avg = ((Number) r.get("avgDuration")).doubleValue();

            YearMonth ym = YearMonth.of(year, month);
            byYm.computeIfAbsent(ym, __ -> new ArrayList<>()).add(new GenreLendingsDTO(genre, avg));
        }

        return byYm.entrySet().stream()
                .map(e -> new GenreLendingsPerMonthDTO(e.getKey().getYear(), e.getKey().getMonthValue(), e.getValue()))
                .sorted(Comparator.comparingInt(GenreLendingsPerMonthDTO::getYear)
                        .thenComparingInt(GenreLendingsPerMonthDTO::getMonth))
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Genre genre) {
        Query query = new Query(Criteria.where("genre").is(genre.getGenre()));
        mongoTemplate.remove(query, GenreNoSQL.class);
    }
}
