package authors.authors.infrastructures.repositories.impl;

import authors.authors.model.Author;
import authors.authors.repositories.AuthorRepository;
import authors.authors.services.SearchAuthorsQuery;
import authors.shared.services.Page;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface SpringDataAuthorRepository extends AuthorRepository, AuthorRepoCustom, CrudRepository<Author, Long> {

    @Query("SELECT a FROM Author a WHERE a.authorNumber = :authorNumber")
    Optional<Author> findByAuthorNumber(@Param("authorNumber") Long authorNumber);

    @Query("SELECT a FROM Author a WHERE a.name.name = :name")
    Optional<Author> findByAuthorName(@Param("name") String name);
}

interface AuthorRepoCustom {
    List<Author> searchAuthors(Page page, SearchAuthorsQuery query);
}

@RequiredArgsConstructor
class AuthorRepoCustomImpl implements AuthorRepoCustom {

    private final EntityManager em;

    @Override
    public List<Author> searchAuthors(Page page, SearchAuthorsQuery query) {
        String name = query.getName();
        Long authorNumber = query.getAuthorNumber();

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Author> cq = cb.createQuery(Author.class);
        Root<Author> root = cq.from(Author.class);
        cq.select(root);

        List<Predicate> predicates = new ArrayList<>();

        if (StringUtils.hasText(name)) {
            predicates.add(cb.like(root.get("name").get("name"), name + "%"));
        }

        if (authorNumber != null) {
            predicates.add(cb.equal(root.get("authorNumber"), authorNumber));
        }

        cq.where(predicates.toArray(new Predicate[0]));
        cq.orderBy(cb.asc(root.get("name").get("name")));

        TypedQuery<Author> typedQuery = em.createQuery(cq);
        typedQuery.setFirstResult((page.getNumber() - 1) * page.getLimit());
        typedQuery.setMaxResults(page.getLimit());

        return typedQuery.getResultList();
    }
}
