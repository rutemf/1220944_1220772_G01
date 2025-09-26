package pt.psoft.g1.psoftg1.usermanagement.infrastructure.repositories.impl;

import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import pt.psoft.g1.psoftg1.exceptions.NotFoundException;
import pt.psoft.g1.psoftg1.shared.services.Page;
import pt.psoft.g1.psoftg1.usermanagement.model.User;
import pt.psoft.g1.psoftg1.usermanagement.repositories.UserRepository;
import pt.psoft.g1.psoftg1.usermanagement.services.SearchUsersQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@Profile("nosql")
public class UserRepositoryNoSQL implements UserRepository {
    private final MongoTemplate mongoTemplate;

    public UserRepositoryNoSQL(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public <S extends User> List<S> saveAll(Iterable<S> entities) {
        List<S> result = new ArrayList<>();
        for (S entity : entities) {
            result.add(save(entity));
        }
        return result;
    }

    @Override
    public <S extends User> S save(S entity) {
        return mongoTemplate.save(entity);
    }

    @Override
    public Optional<User> findById(Long objectId) {
        return Optional.ofNullable(mongoTemplate.findById(objectId, User.class));
    }

    @Override
    public User getById(Long id) {
        return findById(id)
                .filter(User::isEnabled)
                .orElseThrow(() -> new NotFoundException(User.class, id));
    }

    @Override
    public Optional<User> findByUsername(String username) {
        Query query = new Query();
        query.addCriteria(Criteria.where("username").is(username));
        return Optional.ofNullable(mongoTemplate.findOne(query, User.class));
    }

    @Override
    public List<User> searchUsers(Page page, SearchUsersQuery query) {
        // You can implement filtering and paging here using Criteria
        return List.of(); // TODO implement search logic if needed
    }

    @Override
    public List<User> findByNameName(String name) {
        Query query = new Query();
        query.addCriteria(Criteria.where("name.name").is(name));
        return mongoTemplate.find(query, User.class);
    }

    @Override
    public List<User> findByNameNameContains(String name) {
        Query query = new Query();
        query.addCriteria(Criteria.where("name.name").regex(".*" + name + ".*", "i"));
        return mongoTemplate.find(query, User.class);
    }

    @Override
    public void delete(User user) {
        mongoTemplate.remove(user);
    }
}
