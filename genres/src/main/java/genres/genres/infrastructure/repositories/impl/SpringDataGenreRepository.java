package genres.genres.infrastructure.repositories.impl;

import genres.genres.model.Genre;
import genres.genres.repositories.GenreRepository;
import genres.genres.services.SearchGenreQuery;
import genres.shared.services.Page;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public interface SpringDataGenreRepository extends GenreRepository, GenreRepoCustom, CrudRepository<Genre, Long> {

    @Override
    @Query("SELECT g FROM Genre g WHERE g.genre LIKE %:genre%")
    Genre findByGenreName(@Param("genre") String genreName);

}

interface GenreRepoCustom {
    List<Genre> searchGenres(Page page, SearchGenreQuery query);
}

@RequiredArgsConstructor
class GenreRepoCustomImpl implements GenreRepoCustom {

    private final EntityManager em;

    @Override
    public List<Genre> searchGenres(Page page, SearchGenreQuery query) {
        String genre = query.getGenre();

        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<Genre> cq = cb.createQuery(Genre.class);
        final Root<Genre> root = cq.from(Genre.class);
        cq.select(root);

        final List<Predicate> where = new ArrayList<>();

        if (StringUtils.hasText(genre)) {
            where.add(cb.like(root.get("genre"), genre + "%"));
        }

        cq.where(where.toArray(new Predicate[0]));
        cq.orderBy(cb.asc(root.get("genre"))); // ordem alfabética

        final TypedQuery<Genre> q = em.createQuery(cq);
        q.setFirstResult((page.getNumber() - 1) * page.getLimit());
        q.setMaxResults(page.getLimit());

        return q.getResultList();
    }
}
