package pt.psoft.g1.psoftg1.usermanagement.infrastructure.repositories.impl;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.annotation.Profile;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import pt.psoft.g1.psoftg1.exceptions.NotFoundException;
import pt.psoft.g1.psoftg1.shared.services.Page;
import pt.psoft.g1.psoftg1.usermanagement.model.*;
import pt.psoft.g1.psoftg1.usermanagement.repositories.UserRepository;
import pt.psoft.g1.psoftg1.usermanagement.services.SearchUsersQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@Profile("nosql")
@CacheConfig(cacheNames = "users")
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
        if (entity instanceof Reader reader) {
            ReaderNoSQL readerNoSQL = ReaderNoSQL.fromDomain(reader);
            mongoTemplate.save(readerNoSQL);
            return (S) readerNoSQL.toDomain();
        } else if (entity instanceof Librarian librarian) {
            LibrarianNoSQL librarianNoSQL = LibrarianNoSQL.fromDomain(librarian);
            mongoTemplate.save(librarianNoSQL);
            return (S) librarianNoSQL.toDomain();
        } else {
            UserNoSQL userNoSQL = UserNoSQL.fromDomain(entity);
            mongoTemplate.save(userNoSQL);
            return (S) userNoSQL.toDomain();
        }
    }


    @Override
    public Optional<User> findById(Long objectId) {
        UserNoSQL userNoSQL = mongoTemplate.findById(objectId, UserNoSQL.class);
        return Optional.ofNullable(userNoSQL).map(UserNoSQL::toDomain);
    }

    @Override
    @Cacheable(key = "#id")
    public User getById(Long id) {
        return findById(id)
                .filter(User::isEnabled)
                .orElseThrow(() -> new NotFoundException(User.class, id));
    }

    @Override
    @Cacheable(key = "#username")
    public Optional<User> findByUsername(String username) {
        Query query = new Query(Criteria.where("username").is(username));
        UserNoSQL userNoSQL = mongoTemplate.findOne(query, UserNoSQL.class);
        return Optional.ofNullable(userNoSQL).map(UserNoSQL::toDomain);
    }

    @Override
    public List<User> searchUsers(Page page, SearchUsersQuery query) {
        List<Criteria> criteriaList = new ArrayList<>();

        if (query.getUsername() != null && !query.getUsername().isBlank()) {
            criteriaList.add(Criteria.where("username").regex(query.getUsername(), "i"));
        }
        if (query.getFullName() != null && !query.getFullName().isBlank()) {
            criteriaList.add(Criteria.where("name.name").regex(query.getFullName(), "i"));
        }

        Criteria finalCriteria = new Criteria();
        if (!criteriaList.isEmpty()) {
            finalCriteria.andOperator(criteriaList.toArray(new Criteria[0]));
        }

        Query mongoQuery = new Query(finalCriteria);
        mongoQuery.skip((long) (page.getNumber() - 1) * page.getLimit());
        mongoQuery.limit(page.getLimit());

        return mongoTemplate.find(mongoQuery, UserNoSQL.class)
                .stream()
                .map(UserNoSQL::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<User> findByNameName(String name) {
        Query query = new Query(Criteria.where("name.name").is(name));
        return mongoTemplate.find(query, UserNoSQL.class)
                .stream()
                .map(UserNoSQL::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<User> findByNameNameContains(String name) {
        Query query = new Query(Criteria.where("name.name").regex(name, "i"));
        return mongoTemplate.find(query, UserNoSQL.class)
                .stream()
                .map(UserNoSQL::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    @CacheEvict(key = "#user.username")
    public void delete(User user) {
        mongoTemplate.remove(new Query(Criteria.where("id").is(user.getUsername())), UserNoSQL.class);
    }
}
