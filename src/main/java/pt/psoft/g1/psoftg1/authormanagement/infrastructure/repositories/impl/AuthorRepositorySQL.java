package pt.psoft.g1.psoftg1.authormanagement.infrastructure.repositories.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
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
        TypedQuery<Author> query = entityManager.createQuery(
        "SELECT a FROM Author a WHERE a.authorNumber = :authorNumber", Author.class);

        query.setParameter("authorNumber", authorNumber);
        return query.getResultStream().findFirst();
    }

    @Override
    public List<Author> searchByNameNameStartsWith(String name) {
        TypedQuery<Author> query = entityManager.createQuery(
        "SELECT a FROM Author a WHERE a.name.name LIKE :namePattern", Author.class);

        query.setParameter("namePattern", name + "%");
        return query.getResultList();
    }

    @Override
    public List<Author> searchByNameName(String name) {
        TypedQuery<Author> query = entityManager.createQuery(
        "SELECT a FROM Author a WHERE a.name.name = :name", Author.class);

        query.setParameter("name", name);
        return query.getResultList();
    }

    @Override
    public Author save(Author author) {
        if (author.getId() == null) {
            entityManager.persist(author);
            return author;
        } else {
            return entityManager.merge(author);
        }
    }

    @Override
    public Iterable<Author> findAll() {
        TypedQuery<Author> query = entityManager.createQuery(
        "SELECT a FROM Author a", Author.class);

        return query.getResultList();
    }

    @Override
    public Page<AuthorLendingView> findTopAuthorByLendings(Pageable pageableRules) {
        TypedQuery<AuthorLendingView> query = entityManager.createQuery(
        "SELECT new pt.psoft.g1.psoftg1.authormanagement.api.AuthorLendingView(a.name.name, COUNT(l.pk)) " +
        "FROM Book b " +
        "JOIN b.authors a " +
        "JOIN Lending l ON l.book.pk = b.pk " +
        "GROUP BY a.name " +
        "ORDER BY COUNT(l) DESC", AuthorLendingView.class);

        query.setFirstResult((int) pageableRules.getOffset());
        query.setMaxResults(pageableRules.getPageSize());

        List<AuthorLendingView> results = query.getResultList();
        return new org.springframework.data.domain.PageImpl<>(results, pageableRules, results.size());
    }

    @Override
    public void delete(Author author) {
        Author managed = entityManager.contains(author) ? author : entityManager.merge(author);
        entityManager.remove(managed);
    }

    @Override
    public List<Author> findCoAuthorsByAuthorNumber(Long authorNumber) {
        TypedQuery<Author> query = entityManager.createQuery(
        "SELECT DISTINCT coAuthor FROM Book b " +
        "JOIN b.authors coAuthor " +
        "WHERE b IN (SELECT b FROM Book b JOIN b.authors a WHERE a.authorNumber = :authorNumber) " +
        "AND coAuthor.authorNumber <> :authorNumber", Author.class);

        query.setParameter("authorNumber", authorNumber);
        return query.getResultList();
    }
}
