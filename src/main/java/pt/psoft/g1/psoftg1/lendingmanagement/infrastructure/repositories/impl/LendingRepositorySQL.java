package pt.psoft.g1.psoftg1.lendingmanagement.infrastructure.repositories.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import pt.psoft.g1.psoftg1.lendingmanagement.model.Lending;
import pt.psoft.g1.psoftg1.lendingmanagement.repositories.LendingRepository;
import pt.psoft.g1.psoftg1.shared.services.Page;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@Profile("sql")
public class LendingRepositorySQL implements LendingRepository {

    private final EntityManager entityManager;

    public LendingRepositorySQL(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<Lending> findByLendingNumber(String lendingNumber) {
        TypedQuery<Lending> query = entityManager.createQuery(
        "SELECT l FROM Lending l WHERE l.lendingNumber.lendingNumber = :lendingNumber", Lending.class);

        query.setParameter("lendingNumber", lendingNumber);
        return query.getResultStream().findFirst();
    }

    @Override
    public List<Lending> listByReaderNumberAndIsbn(String readerNumber, String isbn) {
        TypedQuery<Lending> query = entityManager.createQuery(
        "SELECT l FROM Lending l " +
        "JOIN l.book b " +
        "JOIN l.readerDetails r " +
        "WHERE b.isbn.isbn = :isbn " +
        "AND r.readerNumber.readerNumber = :readerNumber", Lending.class);

        query.setParameter("isbn", isbn);
        query.setParameter("readerNumber", readerNumber);
        return query.getResultList();
    }

    @Override
    public int getCountFromCurrentYear() {
        TypedQuery<Long> query = entityManager.createQuery(
        "SELECT COUNT(l) FROM Lending l " +
        "WHERE YEAR(l.startDate) = YEAR(CURRENT_DATE)", Long.class);

        return query.getSingleResult().intValue();
    }

    @Override
    public List<Lending> listOutstandingByReaderNumber(String readerNumber) {
        TypedQuery<Lending> query = entityManager.createQuery(
        "SELECT l FROM Lending l " +
        "JOIN l.readerDetails r " +
        "WHERE r.readerNumber.readerNumber = :readerNumber " +
        "AND l.returnedDate IS NULL", Lending.class);

        query.setParameter("readerNumber", readerNumber);
        return query.getResultList();
    }

    @Override
    public Double getAverageDuration() {
        TypedQuery<Double> query = entityManager.createQuery(
        "SELECT AVG(DATEDIFF(l.returnedDate, l.startDate)) " +
        "FROM Lending l " +
        "WHERE l.returnedDate IS NOT NULL", Double.class);

        return Optional.ofNullable(query.getSingleResult()).orElse(0.0);
    }

    @Override
    public Double getAvgLendingDurationByIsbn(String isbn) {
        return 0.0;
    }

    @Override
    public List<Lending> getOverdue(Page page) {
        TypedQuery<Lending> query = entityManager.createQuery(
        "SELECT l FROM Lending l " +
        "WHERE l.returnedDate IS NULL " +
        "AND l.limitDate < CURRENT_DATE " +
        "ORDER BY l.limitDate ASC", Lending.class);

        query.setFirstResult((page.getNumber() - 1) * page.getLimit());
        query.setMaxResults(page.getLimit());
        return query.getResultList();
    }

    @Override
    public List<Lending> searchLendings(Page page, String readerNumber, String isbn, Boolean returned, LocalDate startDate, LocalDate endDate) {
        return List.of();
    }

    @Override
    public Lending save(Lending lending) {
        if (lending.getLendingNumber() == null) {
            entityManager.persist(lending);
            return lending;
        } else {
            return entityManager.merge(lending);
        }
    }

    @Override
    public void delete(Lending lending) {
        Lending managed = entityManager.contains(lending) ? lending : entityManager.merge(lending);
        entityManager.remove(managed);
    }
}
