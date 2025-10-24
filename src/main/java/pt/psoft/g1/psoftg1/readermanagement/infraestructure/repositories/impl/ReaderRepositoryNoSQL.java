package pt.psoft.g1.psoftg1.readermanagement.infraestructure.repositories.impl;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import pt.psoft.g1.psoftg1.genremanagement.model.GenreNoSQL;
import pt.psoft.g1.psoftg1.readermanagement.model.ReaderDetails;
import pt.psoft.g1.psoftg1.readermanagement.model.ReaderDetailsNoSQL;
import pt.psoft.g1.psoftg1.readermanagement.model.ReaderDetailsSQL;
import pt.psoft.g1.psoftg1.readermanagement.repositories.ReaderRepository;
import pt.psoft.g1.psoftg1.readermanagement.services.ReaderBookCountDTO;
import pt.psoft.g1.psoftg1.readermanagement.services.SearchReadersQuery;
import pt.psoft.g1.psoftg1.usermanagement.model.ReaderNoSQL;
import pt.psoft.g1.psoftg1.usermanagement.model.UserNoSQL;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@Profile("nosql")
@CacheConfig(cacheNames = "readers")
public class ReaderRepositoryNoSQL implements ReaderRepository {
    private final MongoTemplate mongoTemplate;
    public ReaderRepositoryNoSQL(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }
    @Override
    @Cacheable(key = "#readerNumber")
    public Optional<ReaderDetails> findByReaderNumber(String readerNumber) {
        Query query = new Query(Criteria.where("readerNumber").is(readerNumber));
        ReaderDetailsNoSQL result = mongoTemplate.findOne(query, ReaderDetailsNoSQL.class);
        return Optional.ofNullable(result).map(ReaderDetailsNoSQL::toDomain);
    }

    @Override
    public List<ReaderDetails> findByPhoneNumber(String phoneNumber) {
        Query query = new Query(Criteria.where("phoneNumber").is(phoneNumber));
        return mongoTemplate.find(query, ReaderDetailsNoSQL.class)
                .stream()
                .map(ReaderDetailsNoSQL::toDomain)
                .collect(Collectors.toList());
    }

    public Optional<ReaderDetails> findByUsername(String username) {
        Query query = new Query(Criteria.where("reader.username").is(username));
        ReaderDetailsNoSQL result = mongoTemplate.findOne(query, ReaderDetailsNoSQL.class);
        return Optional.ofNullable(result).map(ReaderDetailsNoSQL::toDomain);
    }

    @Override
    public Optional<ReaderDetails> findByUserId(Long userId) {
        Query query = new Query(Criteria.where("reader.id").is(userId));
        ReaderDetailsNoSQL result = mongoTemplate.findOne(query, ReaderDetailsNoSQL.class);
        return Optional.ofNullable(result).map(ReaderDetailsNoSQL::toDomain);
    }

    @Override
    public int getCountFromCurrentYear() {
        int year = LocalDate.now().getYear();

        Aggregation agg = Aggregation.newAggregation(
                Aggregation.lookup("users", "reader.id", "_id", "user"),
                Aggregation.unwind("user"),
                Aggregation.match(Criteria.where("user.createdAt")
                        .gte(LocalDate.of(year, 1, 1))
                        .lt(LocalDate.of(year + 1, 1, 1))),
                Aggregation.count().as("total")
        );

        var result = mongoTemplate.aggregate(agg, "readerDetails", Map.class).getUniqueMappedResult();
        return result != null ? ((Number) result.get("total")).intValue() : 0;
    }

    public ReaderDetails save(ReaderDetails readerDetails) {
        ReaderDetailsNoSQL entity = ReaderDetailsNoSQL.fromDomain(readerDetails);

        UserNoSQL user = mongoTemplate.findOne(
                new Query(Criteria.where("username").is(readerDetails.getReader().getUsername())),
                UserNoSQL.class
        );
        if (user == null) {
            throw new IllegalStateException("UserMongo not found: " + readerDetails.getReader().getUsername());
        }

        ReaderNoSQL reader = mongoTemplate.findOne(
                new Query(Criteria.where("username").is(readerDetails.getReader().getUsername())),
                ReaderNoSQL.class
        );
        if (reader == null) {
            throw new IllegalStateException("ReaderMongo not found: " + readerDetails.getReader().getUsername());
        }
        entity.setReader(reader);

        if (entity.getInterestList() != null && !entity.getInterestList().isEmpty()) {
            List<GenreNoSQL> managedGenres = entity.getInterestList().stream()
                    .map(g -> Optional.ofNullable(
                            mongoTemplate.findOne(new Query(Criteria.where("genre").is(g.getGenre())), GenreNoSQL.class)
                    ).orElse(g))
                    .toList();

            entity.setInterestList(managedGenres);
        }
        mongoTemplate.save(entity);
        return entity.toDomain();
    }

    @Override
    @Cacheable(key = "'allReaders'")
    public Iterable<ReaderDetails> findAll() {
        return mongoTemplate.findAll(ReaderDetailsSQL.class)
                .stream()
                .map(ReaderDetailsSQL::toDomain)
                .toList();
    }

    @Override
    public Page<ReaderDetails> findTopReaders(Pageable pageable) {
        Query query = new Query().with(pageable);
        List<ReaderDetails> content = mongoTemplate.find(query, ReaderDetailsNoSQL.class)
                .stream()
                .map(ReaderDetailsNoSQL::toDomain)
                .toList();
        long total = mongoTemplate.count(new Query(), ReaderDetailsNoSQL.class);
        return new PageImpl<>(content, pageable, total);
    }

    //verificar
    @Override
    public Page<ReaderBookCountDTO> findTopByGenre(Pageable pageable, String genre, LocalDate startDate, LocalDate endDate) {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("lendings.genre.name").is(genre)
                        .and("lendings.lendDate").gte(startDate).lte(endDate)),
                Aggregation.unwind("lendings"),
                Aggregation.group("readerNumber", "username")
                        .count().as("bookCount"),
                Aggregation.sort(org.springframework.data.domain.Sort.by(org.springframework.data.domain.Sort.Direction.DESC, "bookCount")),
                Aggregation.skip(pageable.getOffset()),
                Aggregation.limit(pageable.getPageSize())
        );

        List<ReaderBookCountDTO> topReaders = mongoTemplate.aggregate(aggregation, "readers", ReaderBookCountDTO.class)
                .getMappedResults();

        long total = mongoTemplate.count(Query.query(
                Criteria.where("lendings.genre.name").is(genre)
                        .and("lendings.lendDate").gte(startDate).lte(endDate)
        ), ReaderDetails.class);

        return new PageImpl<>(topReaders, pageable, total);
    }

    @Override
    @CacheEvict(key = "#readerDetails.readerNumber")
    public void delete(ReaderDetails readerDetails) {
        Query query = new Query(Criteria.where("readerNumber").is(readerDetails.getReaderNumber()));
        mongoTemplate.remove(query, ReaderDetailsNoSQL.class);
    }

    //verificar
    @Override
    public List<ReaderDetails> searchReaderDetails(pt.psoft.g1.psoftg1.shared.services.Page page, SearchReadersQuery query) {
        Query mongoQuery = new Query();

        if (query.getName() != null) {
            mongoQuery.addCriteria(Criteria.where("username").regex(query.getName(), "i"));
        }
        if (query.getEmail() != null) {
            mongoQuery.addCriteria(Criteria.where("email").regex(query.getEmail(), "i"));
        }
        if (query.getPhoneNumber() != null) {
            mongoQuery.addCriteria(Criteria.where("phoneNumber").regex(query.getPhoneNumber(), "i"));
        }

        return mongoTemplate.find(mongoQuery, ReaderDetails.class);
    }
}
