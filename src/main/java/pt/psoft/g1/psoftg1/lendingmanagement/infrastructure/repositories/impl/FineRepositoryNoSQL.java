package pt.psoft.g1.psoftg1.lendingmanagement.infrastructure.repositories.impl;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import pt.psoft.g1.psoftg1.lendingmanagement.model.Fine;
import pt.psoft.g1.psoftg1.lendingmanagement.model.FineNoSQL;
import pt.psoft.g1.psoftg1.lendingmanagement.repositories.FineRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
@Profile("nosql")
@CacheConfig(cacheNames = "fines")
public class FineRepositoryNoSQL implements FineRepository {

    private final MongoTemplate mongoTemplate;

    public FineRepositoryNoSQL(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    @Cacheable(key = "#lendingNumber")
    public Optional<Fine> findByLendingNumber(String lendingNumber) {
        Query query = new Query(Criteria.where("lending.lendingNumber.lendingNumber").is(lendingNumber));
        FineNoSQL fineNoSQL = mongoTemplate.findOne(query, FineNoSQL.class);

        if (fineNoSQL == null) {
            return Optional.empty();
        }

        return Optional.of(fineNoSQL.toDomain());
    }

    @Override
    @Cacheable(key = "'allFines'")
    public Iterable<Fine> findAll() {
        List<FineNoSQL> fines = mongoTemplate.findAll(FineNoSQL.class, "fines");

        return fines.stream()
                .map(FineNoSQL::toDomain)
                .filter(Objects::nonNull)
                .toList();
    }

    @Override
    public Fine save(Fine fine) {
        FineNoSQL fineNoSQL = new FineNoSQL(fine);
        FineNoSQL saved = mongoTemplate.save(fineNoSQL);
        return saved.toDomain();
    }
}
