package pt.psoft.g1.psoftg1.readermanagement.infraestructure.repositories.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import pt.psoft.g1.psoftg1.readermanagement.model.ReaderDetails;
import pt.psoft.g1.psoftg1.readermanagement.model.ReaderDetailsSQL;
import pt.psoft.g1.psoftg1.readermanagement.repositories.ReaderRepository;
import pt.psoft.g1.psoftg1.readermanagement.services.ReaderBookCountDTO;
import pt.psoft.g1.psoftg1.readermanagement.services.SearchReadersQuery;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@Profile("sql")
public class ReaderRepositorySQL implements ReaderRepository {

    private final EntityManager entityManager;

    public ReaderRepositorySQL(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<ReaderDetails> findByReaderNumber(String readerNumber) {
        TypedQuery<ReaderDetailsSQL> query = entityManager.createQuery(
        "SELECT r FROM ReaderDetailsSQL r WHERE r.readerNumber = :readerNumber", ReaderDetailsSQL.class);

        query.setParameter("readerNumber", readerNumber);
        return query.getResultStream().findFirst().map(ReaderDetailsSQL::toDomain);
    }

    @Override
    public List<ReaderDetails> findByPhoneNumber(String phoneNumber) {
        TypedQuery<ReaderDetailsSQL> query = entityManager.createQuery(
        "SELECT r FROM ReaderDetailsSQL r WHERE r.phoneNumber = :phoneNumber", ReaderDetailsSQL.class);

        query.setParameter("phoneNumber", phoneNumber);
        return query.getResultList().stream().map(ReaderDetailsSQL::toDomain).collect(Collectors.toList());
    }

    @Override
    public Optional<ReaderDetails> findByUsername(String username) {
        TypedQuery<ReaderDetailsSQL> query = entityManager.createQuery(
        "SELECT r FROM ReaderDetailsSQL r WHERE r.reader.username = :username", ReaderDetailsSQL.class);

        query.setParameter("username", username);
        return query.getResultStream().findFirst().map(ReaderDetailsSQL::toDomain);
    }

    @Override
    public Optional<ReaderDetails> findByUserId(Long userId) {
        TypedQuery<ReaderDetailsSQL> query = entityManager.createQuery(
        "SELECT r FROM ReaderDetailsSQL r WHERE r.reader.id = :userId", ReaderDetailsSQL.class);

        query.setParameter("userId", userId);
        return query.getResultStream().findFirst().map(ReaderDetailsSQL::toDomain);
    }

    @Override
    public int getCountFromCurrentYear() {
        int year = LocalDate.now().getYear();
        TypedQuery<Long> query = entityManager.createQuery(
        "SELECT COUNT(r) FROM ReaderDetailsSQL r WHERE FUNCTION('YEAR', r.registrationDate) = :year", Long.class);

        query.setParameter("year", year);
        return query.getSingleResult().intValue();
    }

    @Override
    public ReaderDetails save(ReaderDetails readerDetails) {
        ReaderDetailsSQL entity = ReaderDetailsSQL.fromDomain(readerDetails);

        if (entity.getId() == null) {
            entityManager.persist(entity);
        } else {
            entity = entityManager.merge(entity);
        }

        return entity.toDomain();
    }

    @Override
    public Iterable<ReaderDetails> findAll() {
        TypedQuery<ReaderDetailsSQL> query = entityManager.createQuery(
        "SELECT r FROM ReaderDetailsSQL r", ReaderDetailsSQL.class);

        return query.getResultList().stream().map(ReaderDetailsSQL::toDomain).collect(Collectors.toList());
    }

    @Override
    public Page<ReaderDetails> findTopReaders(Pageable pageable) {
        TypedQuery<ReaderDetailsSQL> query = entityManager.createQuery(
        "SELECT r FROM ReaderDetailsSQL r", ReaderDetailsSQL.class);

        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());
        List<ReaderDetails> content = query.getResultList().stream().map(ReaderDetailsSQL::toDomain).collect(Collectors.toList());

        return new PageImpl<>(content, pageable, content.size());
    }

    @Override
    public Page<ReaderBookCountDTO> findTopByGenre(Pageable pageable, String genre, LocalDate startDate, LocalDate endDate) {
        return new PageImpl<>(List.of(), pageable, 0);
    }

    @Override
    public void delete(ReaderDetails readerDetails) {
        ReaderDetailsSQL entity = ReaderDetailsSQL.fromDomain(readerDetails);
        ReaderDetailsSQL managed = entityManager.contains(entity) ? entity : entityManager.merge(entity);
        entityManager.remove(managed);
    }

    @Override
    public List<ReaderDetails> searchReaderDetails(pt.psoft.g1.psoftg1.shared.services.Page page, SearchReadersQuery query) {
        return null;
    }
}
