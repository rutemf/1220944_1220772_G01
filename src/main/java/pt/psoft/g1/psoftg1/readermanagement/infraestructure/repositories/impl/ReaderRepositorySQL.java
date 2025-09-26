package pt.psoft.g1.psoftg1.readermanagement.infraestructure.repositories.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import pt.psoft.g1.psoftg1.readermanagement.model.ReaderDetails;
import pt.psoft.g1.psoftg1.readermanagement.repositories.ReaderRepository;
import pt.psoft.g1.psoftg1.readermanagement.services.ReaderBookCountDTO;
import pt.psoft.g1.psoftg1.readermanagement.services.SearchReadersQuery;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@Profile("sql")
public class ReaderRepositorySQL implements ReaderRepository {

    private final EntityManager entityManager;

    public ReaderRepositorySQL(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<ReaderDetails> findByReaderNumber(String readerNumber) {
        TypedQuery<ReaderDetails> query = entityManager.createQuery(
        "SELECT r FROM ReaderDetails r WHERE r.readerNumber.readerNumber = :readerNumber", ReaderDetails.class);

        query.setParameter("readerNumber", readerNumber);
        return query.getResultStream().findFirst();
    }

    @Override
    public List<ReaderDetails> findByPhoneNumber(String phoneNumber) {
        TypedQuery<ReaderDetails> query = entityManager.createQuery(
        "SELECT r FROM ReaderDetails r WHERE r.phoneNumber.phoneNumber = :phoneNumber", ReaderDetails.class);

        query.setParameter("phoneNumber", phoneNumber);
        return query.getResultList();
    }

    @Override
    public Optional<ReaderDetails> findByUsername(String username) {
        return Optional.empty();
    }

    @Override
    public Optional<ReaderDetails> findByUserId(Long userId) {
        return Optional.empty();
    }

    @Override
    public int getCountFromCurrentYear() {
        return 0;
    }

    @Override
    public ReaderDetails save(ReaderDetails readerDetails) {
        return null;
    }

    @Override
    public Iterable<ReaderDetails> findAll() {
        TypedQuery<ReaderDetails> query = entityManager.createQuery(
        "SELECT r FROM ReaderDetails r", ReaderDetails.class);

        return query.getResultList();
    }

    @Override
    public Page<ReaderDetails> findTopReaders(Pageable pageable) {
        return null;
    }

    @Override
    public Page<ReaderBookCountDTO> findTopByGenre(Pageable pageable, String genre, LocalDate startDate, LocalDate endDate) {
        return null;
    }

    @Override
    public void delete(ReaderDetails readerDetails) {
    }

    @Override
    public List<ReaderDetails> searchReaderDetails(pt.psoft.g1.psoftg1.shared.services.Page page, SearchReadersQuery query) {
        return List.of();
    }
}
