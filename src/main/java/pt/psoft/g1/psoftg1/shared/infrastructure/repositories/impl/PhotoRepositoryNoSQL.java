package pt.psoft.g1.psoftg1.shared.infrastructure.repositories.impl;

import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;
import pt.psoft.g1.psoftg1.shared.model.PhotoNoSQL;
import pt.psoft.g1.psoftg1.shared.repositories.PhotoRepository;
import org.springframework.data.mongodb.core.query.Query;

import java.nio.file.Paths;

@Repository
@Profile("nosql")
public class PhotoRepositoryNoSQL implements PhotoRepository {

    private final MongoTemplate mongoTemplate;

    public PhotoRepositoryNoSQL(MongoTemplate mongoTemplate) { this.mongoTemplate = mongoTemplate; }

    @Override
    public void deleteByPhotoFile(String photoFile) {
        Query query = new Query();
        query.addCriteria(Criteria.where("photoFile").is(Paths.get(photoFile).toString()));

        mongoTemplate.remove(query, PhotoNoSQL.class);
    }
}
