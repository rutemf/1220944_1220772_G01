package pt.psoft.g1.psoftg1.authormanagement.infrastructure.repositories.impl;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.MongoExpression;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationExpression;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import pt.psoft.g1.psoftg1.authormanagement.api.AuthorLendingView;
import pt.psoft.g1.psoftg1.authormanagement.model.Author;
import pt.psoft.g1.psoftg1.authormanagement.dataschema.AuthorNoSQL;
import pt.psoft.g1.psoftg1.authormanagement.repositories.AuthorRepository;
import pt.psoft.g1.psoftg1.bookmanagement.dataschema.BookNoSQL;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@Profile("nosql")
@CacheConfig(cacheNames = "authors")
public class AuthorRepositoryNoSQL implements AuthorRepository {

    private final MongoTemplate mongoTemplate;

    public AuthorRepositoryNoSQL(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    @Cacheable(key = "#authorNumber")
    public Optional<Author> findByAuthorNumber(Long authorNumber) {
        Query query = new Query();
        query.addCriteria(Criteria.where("authorNumber").is(authorNumber));
        AuthorNoSQL authorSQL = mongoTemplate.findOne(query, AuthorNoSQL.class);
        return Optional.ofNullable(authorSQL).map(AuthorNoSQL::toDomain);
    }

    @Override
    public List<Author> searchByNameNameStartsWith(String name) {
        Query query = new Query(Criteria.where("name").regex("^" + name, "i"));
        List<AuthorNoSQL> authors = mongoTemplate.find(query, AuthorNoSQL.class);
        return authors.stream().map(AuthorNoSQL::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<Author> searchByNameName(String name) {
        Query query = new Query(Criteria.where("name").is(name));
        List<AuthorNoSQL> authors = mongoTemplate.find(query, AuthorNoSQL.class);
        return authors.stream().map(AuthorNoSQL::toDomain).collect(Collectors.toList());
    }

    @Override
    public Author save(Author author) {
        Optional<AuthorNoSQL> existingAuthorNoSQL = findAuthorNoSQLByNumber(author.getAuthorNumber());
        AuthorNoSQL authorNoSQL;

        if (existingAuthorNoSQL.isPresent()) {
            authorNoSQL = existingAuthorNoSQL.get();
            authorNoSQL.setName(author.getName().toString());
            authorNoSQL.setBio(author.getBio().toString());

        } else {
            authorNoSQL = AuthorNoSQL.fromDomain(author);
        }
        AuthorNoSQL saved = mongoTemplate.save(authorNoSQL);

        return saved.toDomain();
    }

    @Override
    @Cacheable(key = "'allAuthors'")
    public Iterable<Author> findAll() {
        return mongoTemplate.findAll(AuthorNoSQL.class)
                .stream()
                .map(AuthorNoSQL::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Page<AuthorLendingView> findTopAuthorByLendings(Pageable pageableRules) {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.unwind("authors"),
                Aggregation.lookup("lendings", "id", "book.id", "lendings"),
                Aggregation.addFields()
                        .addFieldWithValue("lendingCount",
                                AggregationExpression.from(MongoExpression.create("{$size: '$lendings'}")))
                        .build(),
                Aggregation.group("authorName")
                        .sum("lendingCount").as("totalLendings"),
                Aggregation.project("totalLendings")
                        .and("id").as("authorName"),

                Aggregation.sort(Sort.by(Sort.Direction.DESC, "totalLendings")),
                Aggregation.skip( pageableRules.getOffset()),
                Aggregation.limit(pageableRules.getPageSize())
        );
        AggregationResults<AuthorLendingView> results = mongoTemplate.aggregate(
                aggregation, "books", AuthorLendingView.class
        );
        List<AuthorLendingView> list = results.getMappedResults();
        return new PageImpl<>(list, pageableRules, list.size());
    }

    @Override
    @CacheEvict(key = "author.authorNumber")
    public void delete(Author author) {
        Query query = new Query(Criteria.where("authorNumber").is(author.getAuthorNumber()));
        mongoTemplate.remove(query, AuthorNoSQL.class);
    }

    @Override
    public List<Author> findCoAuthorsByAuthorNumber(Long authorNumber) {
        Query query = new Query(Criteria.where("authorNumber").is(authorNumber));
        List<BookNoSQL> books = mongoTemplate.find(query, BookNoSQL.class);

        return books.stream()
                .flatMap(book -> book.getAuthors().stream())
                .filter(a -> !a.getAuthorNumber().equals(authorNumber))
                .distinct()
                .map(AuthorNoSQL::toDomain)
                .toList();
    }

    private Optional<AuthorNoSQL> findAuthorNoSQLByNumber(Long authorNumber) {
        Query query = new Query(Criteria.where("authorNumber").is(authorNumber));
        return Optional.ofNullable(mongoTemplate.findOne(query, AuthorNoSQL.class));
    }
}
