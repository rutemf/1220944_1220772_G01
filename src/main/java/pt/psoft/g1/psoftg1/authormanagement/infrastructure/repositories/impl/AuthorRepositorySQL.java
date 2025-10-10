package pt.psoft.g1.psoftg1.authormanagement.infrastructure.repositories.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import pt.psoft.g1.psoftg1.authormanagement.api.AuthorLendingView;
import pt.psoft.g1.psoftg1.authormanagement.model.Author;
import pt.psoft.g1.psoftg1.authormanagement.model.AuthorSQL;
import pt.psoft.g1.psoftg1.authormanagement.repositories.AuthorRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@Profile("sql")
public class AuthorRepositorySQL implements AuthorRepository {

    private final EntityManager entityManager;

    public AuthorRepositorySQL(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<Author> findByAuthorNumber(Long authorNumber) {
        TypedQuery<AuthorSQL> query = entityManager.createQuery(
        "SELECT a FROM AuthorSQL a WHERE a.authorNumber = :authorNumber", AuthorSQL.class);

        query.setParameter("authorNumber", authorNumber);
        return query.getResultStream().findFirst().map(AuthorSQL::toDomain);
    }

    @Override
    public List<Author> searchByNameNameStartsWith(String name) {
        TypedQuery<AuthorSQL> query = entityManager.createQuery(
        "SELECT a FROM AuthorSQL a WHERE a.name LIKE :namePattern", AuthorSQL.class);

        query.setParameter("namePattern", name + "%");
        return query.getResultList().stream().map(AuthorSQL::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<Author> searchByNameName(String name) {
        TypedQuery<AuthorSQL> query = entityManager.createQuery(
        "SELECT a FROM AuthorSQL a WHERE a.name = :name", AuthorSQL.class);

        query.setParameter("name", name);
        return query.getResultList().stream().map(AuthorSQL::toDomain).collect(Collectors.toList());
    }

    @Override
    public Author save(Author author) {
        AuthorSQL authorSQL = AuthorSQL.fromDomain(author);

        if (authorSQL.getId() == null) {
            entityManager.persist(authorSQL);
            return authorSQL.toDomain();
        } else {
            AuthorSQL merged = entityManager.merge(authorSQL);
            return merged.toDomain();
        }
    }

    @Override
    public Iterable<Author> findAll() {
        TypedQuery<AuthorSQL> query = entityManager.createQuery(
        "SELECT a FROM AuthorSQL a", AuthorSQL.class);

        return query.getResultList().stream().map(AuthorSQL::toDomain).collect(Collectors.toList());
    }

    @Override
    public Page<AuthorLendingView> findTopAuthorByLendings(Pageable pageableRules) {
        TypedQuery<AuthorLendingView> query = entityManager.createQuery(
        "SELECT new pt.psoft.g1.psoftg1.authormanagement.api.AuthorLendingView(a.name, COUNT(l.id)) " +
        "FROM BookSQL b " +
        "JOIN b.authors a " +
        "JOIN LendingSQL l ON l.book.id = b.id " +
        "GROUP BY a.name " +
        "ORDER BY COUNT(l) DESC", AuthorLendingView.class);

        query.setFirstResult((int) pageableRules.getOffset());
        query.setMaxResults(pageableRules.getPageSize());

        List<AuthorLendingView> results = query.getResultList();
        return new PageImpl<>(results, pageableRules, results.size());
    }

    @Override
    public void delete(Author author) {
        AuthorSQL sql = AuthorSQL.fromDomain(author);
        AuthorSQL managed = entityManager.contains(sql) ? sql : entityManager.merge(sql);
        entityManager.remove(managed);
    }

    @Override
    public List<Author> findCoAuthorsByAuthorNumber(Long authorNumber) {
        TypedQuery<AuthorSQL> query = entityManager.createQuery(
        "SELECT DISTINCT coAuthor FROM BookSQL b " +
        "JOIN b.authors coAuthor " +
        "WHERE b IN (SELECT b FROM BookSQL b JOIN b.authors a WHERE a.authorNumber = :authorNumber) " +
        "AND coAuthor.authorNumber <> :authorNumber", AuthorSQL.class);

        query.setParameter("authorNumber", authorNumber);
        return query.getResultList().stream().map(AuthorSQL::toDomain).collect(Collectors.toList());
    }
}
