package pt.psoft.g1.psoftg1.lendingmanagement.infrastructure.repositories.impl;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import pt.psoft.g1.psoftg1.bookmanagement.dataschema.BookNoSQL;
import pt.psoft.g1.psoftg1.lendingmanagement.model.Lending;
import pt.psoft.g1.psoftg1.lendingmanagement.dataschema.LendingNoSQL;
import pt.psoft.g1.psoftg1.lendingmanagement.repositories.LendingRepository;
import pt.psoft.g1.psoftg1.readermanagement.dataschema.ReaderDetailsNoSQL;
import pt.psoft.g1.psoftg1.shared.services.Page;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@Profile("nosql")
@CacheConfig(cacheNames = "lendings")
public class LendingRepositoryNoSQL implements LendingRepository {

    private final MongoTemplate mongoTemplate;

    public LendingRepositoryNoSQL(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    @Cacheable(key = "#lendingNumber")

    public Optional<Lending> findByLendingNumber(String lendingNumber) {
        Query query = new Query();
        query.addCriteria(Criteria.where("lendingNumber").is(lendingNumber));
        LendingNoSQL lendingNoSQL = mongoTemplate.findOne(query, LendingNoSQL.class, "lendings");
        return Optional.ofNullable(lendingNoSQL).map(LendingNoSQL::toDomain);
    }

    @Override
    public List<Lending> listByReaderNumberAndIsbn(String readerNumber, String isbn) {
        Query query = new Query(Criteria.where("book.isbn").is(isbn)
                .and("readerDetails.readerNumber").is(readerNumber));
        return mongoTemplate.find(query, LendingNoSQL.class)
                .stream()
                .map(LendingNoSQL::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public int getCountFromCurrentYear() {
        int year = LocalDate.now().getYear();

        Aggregation agg = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("startDate")
                        .gte(LocalDate.of(year, 1, 1))
                        .lt(LocalDate.of(year + 1, 1, 1))),
                Aggregation.count().as("total")
        );

        var result = mongoTemplate.aggregate(agg, "lendings", org.bson.Document.class).getUniqueMappedResult();
        return result != null ? ((Number) result.get("total")).intValue() : 0;
    }

    @Override
    public List<Lending> listOutstandingByReaderNumber(String readerNumber) {
        Query query = new Query(Criteria.where("readerDetails.readerNumber").is(readerNumber)
                .and("returnedDate").is(null));
        return mongoTemplate.find(query, LendingNoSQL.class)
                .stream()
                .map(LendingNoSQL::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Double getAverageDuration() {
        Aggregation agg = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("returnedDate").ne(null)),
                Aggregation.project()
                        .andExpression("returnDate - startDate").as("duration"),
                Aggregation.group().avg("duration").as("avgDuration")
        );

        var result = mongoTemplate.aggregate(agg, "lendings", org.bson.Document.class).getUniqueMappedResult();
        return result != null ? result.getDouble("avgDuration") : 0.0;
    }

    @Override
    public Double getAvgLendingDurationByIsbn(String isbn) {
        Aggregation agg = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("returnedDate").ne(null)
                        .and("book.isbn").is(isbn)),
                Aggregation.project()
                        .andExpression("returnDate - startDate").as("duration"),
                Aggregation.group().avg("duration").as("avgDuration")
        );

        var result = mongoTemplate.aggregate(agg, "lendings", org.bson.Document.class).getUniqueMappedResult();
        return result != null ? result.getDouble("avgDuration") : 0.0;
    }

    @Override
    public List<Lending> getOverdue(Page page) {
        int pageNumber = page.getNumber() > 0 ? page.getNumber() : 1;
        int pageLimit = page.getLimit() > 0 ? page.getLimit() : 10;

        Query query = new Query();
        query.addCriteria(Criteria.where("returnedDate").is(null));

        List<Lending> lendings = mongoTemplate.find(query, Lending.class);

        LocalDate today = LocalDate.now();

        return lendings.stream()
                .filter(l -> {
                    try {
                        LocalDate limit = LocalDate.parse(l.getLimitDate().toString());
                        return limit.isBefore(today);
                    } catch (Exception e) {
                        return false;
                    }
                })
                .sorted(Comparator.comparing(Lending::getLimitDate))
                .skip((long) (pageNumber - 1) * pageLimit)
                .limit(pageLimit)
                .collect(Collectors.toList());
    }

    @Override
    public List<Lending> searchLendings(Page page, String readerNumber, String isbn, Boolean returned, LocalDate startDate, LocalDate endDate) {
        Criteria criteria = new Criteria();
        if (readerNumber != null) criteria.and("readerDetails.readerNumber").is(readerNumber);
        if (isbn != null) criteria.and("book.isbn").is(isbn);
        if (returned != null) {
            if (returned) criteria.and("returnedDate").ne(null);
            else criteria.and("returnedDate").is(null);
        }
        if (startDate != null) criteria.and("startDate").gte(startDate);
        if (endDate != null) criteria.and("startDate").lte(endDate);

        Query query = new Query(criteria)
                .skip((page.getNumber() - 1) * page.getLimit())
                .limit(page.getLimit());

        return mongoTemplate.find(query, LendingNoSQL.class)
                .stream()
                .map(LendingNoSQL::toDomain)
                .collect(Collectors.toList());
    }

    public Lending save(Lending lending) {
        LendingNoSQL entity = LendingNoSQL.fromDomain(lending);

        if (entity.getBook() != null && entity.getBook().getIsbn() != null) {
            BookNoSQL book = mongoTemplate.findOne(
                    new Query(Criteria.where("isbn").is(entity.getBook().getIsbn())), BookNoSQL.class);
            if (book == null) throw new IllegalArgumentException("Book not found: " + entity.getBook().getIsbn());
            entity.setBook(book);
        }

        if (entity.getReaderDetails() != null && entity.getReaderDetails().getReaderNumber() != null) {
            ReaderDetailsNoSQL reader = mongoTemplate.findOne(
                    new Query(Criteria.where("readerNumber").is(entity.getReaderDetails().getReaderNumber())), ReaderDetailsNoSQL.class);
            if (reader == null) throw new IllegalArgumentException("Reader not found: " + entity.getReaderDetails().getReaderNumber());
            entity.setReaderDetails(reader);
        }

        mongoTemplate.save(entity);
        return entity.toDomain();
    }

    @Override
    @CacheEvict(key = "#lending.lendingNumber")
    public void delete(Lending lending) {
        Query query = new Query(Criteria.where("lendingNumber").is(lending.getLendingNumber()));
        mongoTemplate.remove(query, LendingNoSQL.class);
    }
}
