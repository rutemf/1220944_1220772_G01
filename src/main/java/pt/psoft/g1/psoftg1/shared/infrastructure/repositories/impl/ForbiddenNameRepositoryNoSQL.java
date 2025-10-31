package pt.psoft.g1.psoftg1.shared.infrastructure.repositories.impl;

import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import pt.psoft.g1.psoftg1.shared.model.ForbiddenName;
import pt.psoft.g1.psoftg1.shared.dataschema.ForbiddenNameNoSQL;
import pt.psoft.g1.psoftg1.shared.repositories.ForbiddenNameRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@Profile("nosql")
public class ForbiddenNameRepositoryNoSQL implements ForbiddenNameRepository {

    private final MongoTemplate mongoTemplate;

    public ForbiddenNameRepositoryNoSQL(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Iterable<ForbiddenName> findAll() {
        List<ForbiddenNameNoSQL> results = mongoTemplate.findAll(ForbiddenNameNoSQL.class);
        return results.stream().map(ForbiddenNameNoSQL::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<ForbiddenName> findByForbiddenNameIsContained(String pat) {
        Query query = new Query(Criteria.where("forbiddenName").regex(pat, "i"));
        List<ForbiddenNameNoSQL> results = mongoTemplate.find(query, ForbiddenNameNoSQL.class);

        return results.stream().map(ForbiddenNameNoSQL::toDomain).collect(Collectors.toList());
    }

    @Override
    public ForbiddenName save(ForbiddenName forbiddenName) {
        ForbiddenNameNoSQL saved = mongoTemplate.save(new ForbiddenNameNoSQL(forbiddenName));
        return saved.toDomain();
    }

    @Override
    public Optional<ForbiddenName> findByForbiddenName(String forbiddenName) {
        Query query = new Query(Criteria.where("forbiddenName").is(forbiddenName));
        ForbiddenNameNoSQL result = mongoTemplate.findOne(query, ForbiddenNameNoSQL.class);
        return Optional.ofNullable(result).map(ForbiddenNameNoSQL::toDomain);
    }

    @Override
    public int deleteForbiddenName(String forbiddenName) {
        Query query = new Query(Criteria.where("forbiddenName").is(forbiddenName));
        return (int) mongoTemplate.remove(query, ForbiddenNameNoSQL.class).getDeletedCount();
    }
}
