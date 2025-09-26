package pt.psoft.g1.psoftg1.authormanagement.infrastructure.repositories.impl;

import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;
import pt.psoft.g1.psoftg1.authormanagement.api.AuthorLendingView;
import pt.psoft.g1.psoftg1.authormanagement.model.Author;
import pt.psoft.g1.psoftg1.authormanagement.repositories.AuthorRepository;

import java.util.List;
import java.util.Optional;

@Repository
@Profile("nosql")
public class AuthorRepositoryNoSQL implements AuthorRepository {

    private final MongoTemplate mongoTemplate;

    public AuthorRepositoryNoSQL(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Optional<Author> findByAuthorNumber(Long authorNumber) {
        return Optional.empty();
    }

    @Override
    public List<Author> searchByNameNameStartsWith(String name) {
        return List.of();
    }

    @Override
    public List<Author> searchByNameName(String name) {
        return List.of();
    }

    @Override
    public Author save(Author author) {
        return mongoTemplate.save(author);
    }

    @Override
    public Iterable<Author> findAll() {
        return mongoTemplate.findAll(Author.class);
    }

    @Override
    public Page<AuthorLendingView> findTopAuthorByLendings(Pageable pageableRules) {
        return null;
    }

    @Override
    public void delete(Author author) {
        mongoTemplate.remove(author);
    }

    @Override
    public List<Author> findCoAuthorsByAuthorNumber(Long authorNumber) {
        return List.of();
    }
}
