package pt.psoft.g1.psoftg1.lendingmanagement.infrastructure.repositories.impl;

import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import pt.psoft.g1.psoftg1.lendingmanagement.model.Fine;
import pt.psoft.g1.psoftg1.lendingmanagement.model.FineNoSQL;
import pt.psoft.g1.psoftg1.lendingmanagement.repositories.FineRepository;

import java.util.List;
import java.util.Optional;

@Repository
@Profile("nosql")
public class FineRepositoryNoSQL implements FineRepository {

    private final MongoTemplate mongoTemplate;

    public FineRepositoryNoSQL(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Optional<Fine> findByLendingNumber(String lendingNumber) {
        Query query = new Query(Criteria.where("lending.lendingNumber.lendingNumber").is(lendingNumber));
        FineNoSQL fineNoSQL = mongoTemplate.findOne(query, FineNoSQL.class);

        if (fineNoSQL == null) {
            return Optional.empty();
        }

        return Optional.of(fineNoSQL.toDomain());
    }

    @Override
    public Iterable<Fine> findAll() {
        List<FineNoSQL> fineNoSQLList = mongoTemplate.findAll(FineNoSQL.class);
        return fineNoSQLList.stream().map(FineNoSQL::toDomain).toList();
    }

    @Override
    public Fine save(Fine fine) {
        FineNoSQL fineNoSQL = new FineNoSQL(fine);
        FineNoSQL saved = mongoTemplate.save(fineNoSQL);
        return saved.toDomain();
    }
}
