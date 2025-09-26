package pt.psoft.g1.psoftg1.authormanagement.infrastructure.repositories.impl;

import jakarta.persistence.EntityManager;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import pt.psoft.g1.psoftg1.authormanagement.api.AuthorLendingView;
import pt.psoft.g1.psoftg1.authormanagement.model.Author;
import pt.psoft.g1.psoftg1.authormanagement.repositories.AuthorRepository;

import java.util.List;
import java.util.Optional;

@Repository
@Profile("sql")
public class AuthorRepositorySQL implements AuthorRepository {

    private final EntityManager entityManager;

    public AuthorRepositorySQL(EntityManager entityManager) {
        this.entityManager = entityManager;
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
        return null;
    }

    @Override
    public Iterable<Author> findAll() {
        return null;
    }

    @Override
    public Page<AuthorLendingView> findTopAuthorByLendings(Pageable pageableRules) {
        return null;
    }

    @Override
    public void delete(Author author) {

    }

    @Override
    public List<Author> findCoAuthorsByAuthorNumber(Long authorNumber) {
        return List.of();
    }
}
