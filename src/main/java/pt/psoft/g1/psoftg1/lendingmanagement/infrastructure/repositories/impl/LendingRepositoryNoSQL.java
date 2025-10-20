package pt.psoft.g1.psoftg1.lendingmanagement.infrastructure.repositories.impl;

import org.bson.Document;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import pt.psoft.g1.psoftg1.lendingmanagement.model.Lending;
import pt.psoft.g1.psoftg1.lendingmanagement.repositories.LendingRepository;
import pt.psoft.g1.psoftg1.shared.services.Page;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@Profile("nosql")
public class LendingRepositoryNoSQL implements LendingRepository {

    private final MongoTemplate mongoTemplate;

    public LendingRepositoryNoSQL(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Optional<Lending> findByLendingNumber(String lendingNumber) {
        Query query = new Query();
        query.addCriteria(Criteria.where("lendingNumber").is(lendingNumber));
        Lending lending = mongoTemplate.findOne(query, Lending.class);
        return Optional.ofNullable(lending);
    }

    @Override
    public List<Lending> listByReaderNumberAndIsbn(String readerNumber, String isbn) {
        Query query = new Query();
        query.addCriteria(Criteria.where("readerNumber").is(readerNumber)
                .and("isbn.value").is(isbn));
        return mongoTemplate.find(query, Lending.class);
    }

    @Override
    public int getCountFromCurrentYear() {
        LocalDate startOfYear = LocalDate.now().withDayOfYear(1);
        Query query = new Query();
        query.addCriteria(Criteria.where("lendingDate").gte(startOfYear));
        return (int) mongoTemplate.count(query, Lending.class);
    }

    @Override
    public List<Lending> listOutstandingByReaderNumber(String readerNumber) {
        Query query = new Query();
        query.addCriteria(Criteria.where("readerNumber").is(readerNumber)
                .and("returned").is(false));
        return mongoTemplate.find(query, Lending.class);
    }

    @Override
    public Double getAverageDuration() {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.project()
                        .andExpression("returnDate - lendingDate").as("duration"),
                Aggregation.group().avg("duration").as("averageDuration")
        );

        AggregationResults<Document> result = mongoTemplate.aggregate(aggregation, Lending.class, org.bson.Document.class);
        org.bson.Document doc = result.getUniqueMappedResult();
        if (doc != null && doc.get("averageDuration") != null) {
            return ((Number) doc.get("averageDuration")).doubleValue();
        }
        return 0.0;
    }

    @Override
    public Double getAvgLendingDurationByIsbn(String isbn) {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("isbn.value").is(isbn)),
                Aggregation.project()
                        .andExpression("returnDate - lendingDate").as("duration"),
                Aggregation.group().avg("duration").as("averageDuration")
        );

        AggregationResults<org.bson.Document> result = mongoTemplate.aggregate(aggregation, Lending.class, org.bson.Document.class);
        org.bson.Document doc = result.getUniqueMappedResult();
        if (doc != null && doc.get("averageDuration") != null) {
            return ((Number) doc.get("averageDuration")).doubleValue();
        }
        return 0.0;
    }

    @Override
    public List<Lending> getOverdue(Page page) {
        LocalDate today = LocalDate.now();
        Query query = new Query();
        query.addCriteria(Criteria.where("dueDate").lt(today)
                .and("returned").is(false));
        return mongoTemplate.find(query, Lending.class);
    }

    @Override
    public List<Lending> searchLendings(Page page, String readerNumber, String isbn, Boolean returned, LocalDate startDate, LocalDate endDate) {
        Query query = new Query();

        if (readerNumber != null && !readerNumber.isBlank()) {
            query.addCriteria(Criteria.where("readerNumber").is(readerNumber));
        }
        if (isbn != null && !isbn.isBlank()) {
            query.addCriteria(Criteria.where("isbn.value").is(isbn));
        }
        if (returned != null) {
            query.addCriteria(Criteria.where("returned").is(returned));
        }
        if (startDate != null) {
            query.addCriteria(Criteria.where("lendingDate").gte(startDate));
        }
        if (endDate != null) {
            query.addCriteria(Criteria.where("lendingDate").lte(endDate));
        }

        return mongoTemplate.find(query, Lending.class);
    }

    @Override
    public Lending save(Lending lending) { return mongoTemplate.save(lending);}

    @Override
    public void delete(Lending lending) {mongoTemplate.remove(lending);}
}
