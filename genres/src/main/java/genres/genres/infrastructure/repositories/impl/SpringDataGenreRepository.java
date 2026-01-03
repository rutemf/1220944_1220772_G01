package genres.genres.infrastructure.repositories.impl;

import genres.genres.model.Genre;
import genres.genres.repositories.GenreRepository;
import genres.genres.services.SearchGenreQuery;
import genres.shared.services.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.List;

public interface SpringDataGenreRepository extends GenreRepository, GenreRepoCustom, MongoRepository<Genre, String> {

    @Override
    default Genre findByGenreName(String genreName) {
        return findByGenre(genreName);
    }

    Genre findByGenre(String genre);

}

interface GenreRepoCustom {
    List<Genre> searchGenres(Page page, SearchGenreQuery query);
}

@RequiredArgsConstructor
class GenreRepoCustomImpl implements GenreRepoCustom {

    private final MongoTemplate mongoTemplate;

    @Override
    public List<Genre> searchGenres(Page page, SearchGenreQuery query) {
        Query mongoQuery = new Query();

        if (StringUtils.hasText(query.getGenre())) {
            mongoQuery.addCriteria(Criteria.where("genre").regex("^" + query.getGenre()));
        }

        mongoQuery.with(Sort.by(Sort.Direction.ASC, "genre"));

        mongoQuery.skip((page.getNumber() - 1) * page.getLimit());
        mongoQuery.limit(page.getLimit());

        return mongoTemplate.find(mongoQuery, Genre.class);
    }
}
