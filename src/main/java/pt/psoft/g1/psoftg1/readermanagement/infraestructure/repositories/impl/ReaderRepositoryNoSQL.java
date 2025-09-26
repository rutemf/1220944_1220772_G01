package pt.psoft.g1.psoftg1.readermanagement.infraestructure.repositories.impl;

import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import pt.psoft.g1.psoftg1.readermanagement.model.ReaderDetails;
import pt.psoft.g1.psoftg1.readermanagement.repositories.ReaderRepository;
import pt.psoft.g1.psoftg1.readermanagement.services.ReaderBookCountDTO;
import pt.psoft.g1.psoftg1.readermanagement.services.SearchReadersQuery;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@Profile("nosql")
public class ReaderRepositoryNoSQL implements ReaderRepository {

    private final MongoTemplate mongoTemplate;

    public ReaderRepositoryNoSQL(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Optional<ReaderDetails> findByReaderNumber(String readerNumber) {
        Query query = new Query();
        query.addCriteria(Criteria.where("readerNumber").is(readerNumber));
        return Optional.ofNullable(mongoTemplate.findOne(query, ReaderDetails.class));
    }

    @Override
    public List<ReaderDetails> findByPhoneNumber(String phoneNumber) {
        Query query = new Query();
        query.addCriteria(Criteria.where("phoneNumber").is(phoneNumber));
        return mongoTemplate.find(query, ReaderDetails.class);
    }

    @Override
    public Optional<ReaderDetails> findByUsername(String username) {
        Query query = new Query();
        query.addCriteria(Criteria.where("username").is(username));
        return Optional.ofNullable(mongoTemplate.findOne(query, ReaderDetails.class));
    }

    @Override
    public Optional<ReaderDetails> findByUserId(Long userId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("userId").is(userId));
        return Optional.ofNullable(mongoTemplate.findOne(query, ReaderDetails.class));    }

    @Override
    public int getCountFromCurrentYear() {
        LocalDate startOfYear = LocalDate.now().withDayOfYear(1);
        Query query = new Query();
        query.addCriteria(Criteria.where("creationDate").gte(startOfYear));
        return (int) mongoTemplate.count(query, ReaderDetails.class);
    }

    @Override
    public ReaderDetails save(ReaderDetails readerDetails) {
        return mongoTemplate.save(readerDetails);
    }

    @Override
    public Iterable<ReaderDetails> findAll() {
        return mongoTemplate.findAll(ReaderDetails.class);
    }

    @Override
    public Page<ReaderDetails> findTopReaders(Pageable pageable) {
        Query query = new Query().with(pageable);
        List<ReaderDetails> readers = mongoTemplate.find(query, ReaderDetails.class);
        long total = mongoTemplate.count(new Query(), ReaderDetails.class);
        return new PageImpl<>(readers, pageable, total);
    }

    @Override
    public Page<ReaderBookCountDTO> findTopByGenre(Pageable pageable, String genre, LocalDate startDate, LocalDate endDate) {
        return new PageImpl<>(List.of(), pageable, 0);
    }

    @Override
    public void delete(ReaderDetails readerDetails) {
        mongoTemplate.remove(readerDetails);
    }

    @Override
    public List<ReaderDetails> searchReaderDetails(pt.psoft.g1.psoftg1.shared.services.Page page, SearchReadersQuery query) {
        return List.of();
    }
}
