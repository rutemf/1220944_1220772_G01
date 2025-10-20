package pt.psoft.g1.psoftg1.genremanagement.infrastructure.repositories.impl;

import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
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
@Profile("nosql")
public class GenreRepositoryNoSQL implements GenreRepository {

    private final MongoTemplate mongoTemplate;

    public GenreRepositoryNoSQL(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Iterable<Genre> findAll() {
        return mongoTemplate.findAll(Genre.class);
    }

    @Override
    public Optional<Genre> findByString(String genreName) {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(genreName));
        return Optional.ofNullable(mongoTemplate.findOne(query, Genre.class));
    }

    @Override
    public Genre save(Genre genre) {
        return mongoTemplate.save(genre);
    }

    @Override
    public Page<GenreBookCountDTO> findTop5GenreByBookCount(Pageable pageable) {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.group("genre.name").count().as("bookCount"),
                Aggregation.sort(org.springframework.data.domain.Sort.by(org.springframework.data.domain.Sort.Direction.DESC, "bookCount")),
                Aggregation.limit(5)
        );

        AggregationResults<GenreBookCountDTO> results = mongoTemplate.aggregate(aggregation, "books", GenreBookCountDTO.class);

        List<GenreBookCountDTO> list = results.getMappedResults();

        return new PageImpl<>(list, pageable, list.size());
    }

    @Override
    public List<GenreLendingsDTO> getAverageLendingsInMonth(LocalDate month, pt.psoft.g1.psoftg1.shared.services.Page page) {
        LocalDate startDate = month.withDayOfMonth(1);
        LocalDate endDate = month.plusMonths(1).withDayOfMonth(1);

        int skip = (page.getNumber() - 1) * page.getLimit();
        int limit = page.getLimit();

        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("lendingDate").gte(startDate).lt(endDate)),

                Aggregation.group("book.genre.name")
                        .count().as("lendingCount"),

                Aggregation.project()
                        .and("_id").as("genreName")
                        .and("lendingCount").as("lendingCount"),

                Aggregation.sort(org.springframework.data.domain.Sort.by(org.springframework.data.domain.Sort.Direction.DESC, "lendingCount")),

                Aggregation.skip(skip),
                Aggregation.limit(limit)
        );

        AggregationResults<GenreLendingsDTO> results =
                mongoTemplate.aggregate(aggregation, "lendings", GenreLendingsDTO.class);

        return results.getMappedResults();
    }

    @Override
    public List<GenreLendingsPerMonthDTO> getLendingsPerMonthLastYearByGenre() {
        LocalDate oneYearAgo = LocalDate.now().minusYears(1);

        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("lendingDate").gte(oneYearAgo)),

                Aggregation.project("book.genre.name")
                        .andExpression("year(lendingDate)").as("year")
                        .andExpression("month(lendingDate)").as("month"),
                Aggregation.group("book.genre.name", "year", "month")
                        .count().as("lendings"),

                Aggregation.project()
                        .and("_id.genreName").as("genreName")
                        .and("_id.year").as("year")
                        .and("_id.month").as("month")
                        .and("lendings").as("lendingCount"),

                Aggregation.sort(org.springframework.data.domain.Sort.by("year", "month"))
        );

        AggregationResults<GenreLendingsPerMonthDTO> results = mongoTemplate.aggregate(aggregation, "lendings", GenreLendingsPerMonthDTO.class);
        return results.getMappedResults();
    }

    @Override
    public List<GenreLendingsPerMonthDTO> getLendingsAverageDurationPerMonth(LocalDate startDate, LocalDate endDate) {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("lendingDate").gte(startDate).lt(endDate)
                        .and("returnDate").ne(null)),

                Aggregation.project("book.genre.name", "lendingDate", "returnDate")
                        .andExpression("year(lendingDate)").as("year")
                        .andExpression("month(lendingDate)").as("month")
                        .andExpression("returnDate - lendingDate").as("durationInDays"),

                Aggregation.group("book.genre.name", "year", "month")
                        .avg("durationInDays").as("averageDuration"),

                Aggregation.project()
                        .and("_id.genreName").as("genreName")
                        .and("_id.year").as("year")
                        .and("_id.month").as("month")
                        .and("averageDuration").as("averageDuration"),

                Aggregation.sort(org.springframework.data.domain.Sort.by("year", "month"))
        );

        AggregationResults<GenreLendingsPerMonthDTO> results = mongoTemplate.aggregate(aggregation, "lendings", GenreLendingsPerMonthDTO.class);
        return results.getMappedResults();
    }

    @Override
    public void delete(Genre genre) {
        mongoTemplate.remove(genre);
    }
}
