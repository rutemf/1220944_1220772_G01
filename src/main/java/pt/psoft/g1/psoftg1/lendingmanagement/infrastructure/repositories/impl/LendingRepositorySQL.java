package pt.psoft.g1.psoftg1.lendingmanagement.infrastructure.repositories.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import pt.psoft.g1.psoftg1.lendingmanagement.model.Lending;
import pt.psoft.g1.psoftg1.lendingmanagement.model.LendingSQL;
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
        TypedQuery<LendingSQL> query = entityManager.createQuery(
        "SELECT l FROM LendingSQL l WHERE l.lendingNumber.lendingNumber = :lendingNumber", LendingSQL.class);

        query.setParameter("lendingNumber", lendingNumber);
        return query.getResultStream().findFirst().map(LendingSQL::toDomain);
    }

    @Override
    public List<Lending> listByReaderNumberAndIsbn(String readerNumber, String isbn) {
        TypedQuery<LendingSQL> query = entityManager.createQuery(
        "SELECT l FROM LendingSQL l " +
        "JOIN l.book b " +
        "JOIN l.readerDetails r " +
        "WHERE b.isbn.isbn = :isbn " +
        "AND r.readerNumber.readerNumber = :readerNumber", LendingSQL.class);

        query.setParameter("isbn", isbn);
        query.setParameter("readerNumber", readerNumber);
        return query.getResultList().stream().map(LendingSQL::toDomain).toList();
    }

    @Override
    public int getCountFromCurrentYear() {
        TypedQuery<Long> query = entityManager.createQuery(
        "SELECT COUNT(l) FROM LendingSQL l " +
        "WHERE YEAR(l.startDate) = YEAR(CURRENT_DATE)", Long.class);

        return query.getSingleResult().intValue();
    }

    @Override
    public List<Lending> listOutstandingByReaderNumber(String readerNumber) {
        TypedQuery<LendingSQL> query = entityManager.createQuery(
        "SELECT l FROM LendingSQL l " +
        "JOIN l.readerDetails r " +
        "WHERE r.readerNumber.readerNumber = :readerNumber " +
        "AND l.returnedDate IS NULL", LendingSQL.class);

        query.setParameter("readerNumber", readerNumber);
        return query.getResultList().stream().map(LendingSQL::toDomain).toList();
    }

    @Override
    public Double getAverageDuration() {
        TypedQuery<Double> query = entityManager.createQuery(
        "SELECT AVG(DATEDIFF(l.returnedDate, l.startDate)) " +
        "FROM LendingSQL l " +
        "WHERE l.returnedDate IS NOT NULL", Double.class);

        return Optional.ofNullable(query.getSingleResult()).orElse(0.0);
    }

    @Override
    public Double getAvgLendingDurationByIsbn(String isbn) {
        TypedQuery<Double> query = entityManager.createQuery(
        "SELECT AVG(DATEDIFF(l.returnedDate, l.startDate)) " +
        "FROM LendingSQL l " +
        "JOIN l.book b " +
        "WHERE b.isbn.isbn = :isbn " +
        "AND l.returnedDate IS NOT NULL", Double.class);

        query.setParameter("isbn", isbn);
        return Optional.ofNullable(query.getSingleResult()).orElse(0.0);
    }

    @Override
    public List<Lending> getOverdue(Page page) {
        TypedQuery<LendingSQL> query = entityManager.createQuery(
        "SELECT l FROM LendingSQL l " +
        "WHERE l.returnedDate IS NULL " +
        "AND l.limitDate < CURRENT_DATE " +
        "ORDER BY l.limitDate ASC", LendingSQL.class);

        query.setFirstResult((page.getNumber() - 1) * page.getLimit());
        query.setMaxResults(page.getLimit());
        return query.getResultList().stream().map(LendingSQL::toDomain).toList();
    }

    @Override
    public List<Lending> searchLendings(Page page, String readerNumber, String isbn, Boolean returned, LocalDate startDate, LocalDate endDate) {
        StringBuilder sb = new StringBuilder("SELECT l FROM LendingSQL l JOIN l.book b JOIN l.readerDetails r WHERE 1=1");

        if (readerNumber != null) {
            sb.append(" AND r.readerNumber.readerNumber = :readerNumber");
        }

        if (isbn != null) {
            sb.append(" AND b.isbn.isbn = :isbn");
        }

        if (returned != null) {
            if (returned) {
                sb.append(" AND l.returnedDate IS NOT NULL");
            } else {
                sb.append(" AND l.returnedDate IS NULL");
            }
        }

        if (startDate != null) {
            sb.append(" AND l.startDate >= :startDate");
        }

        if (endDate != null) {
            sb.append(" AND l.startDate <= :endDate");
        }

        sb.append(" ORDER BY l.startDate DESC");

        TypedQuery<LendingSQL> query = entityManager.createQuery(sb.toString(), LendingSQL.class);

        if (readerNumber != null) {
            query.setParameter("readerNumber", readerNumber);
        }

        if (isbn != null) {
            query.setParameter("isbn", isbn);
        }

        if (startDate != null) {
            query.setParameter("startDate", startDate);
        }

        if (endDate != null) {
            query.setParameter("endDate", endDate);
        }

        query.setFirstResult((page.getNumber() - 1) * page.getLimit());
        query.setMaxResults(page.getLimit());

        return query.getResultList().stream().map(LendingSQL::toDomain).toList();
    }

    @Override
    public Lending save(Lending lending) {
        LendingSQL lendingSQL = LendingSQL.fromDomain(lending);
        if (lendingSQL.getLendingNumber() == null) {
            entityManager.persist(lendingSQL);
            return lendingSQL.toDomain();
        } else {
            LendingSQL merged = entityManager.merge(lendingSQL);
            return merged.toDomain();
        }
    }

    @Override
    public void delete(Lending lending) {
        LendingSQL lendingSQL = LendingSQL.fromDomain(lending);
        LendingSQL managed = entityManager.contains(lendingSQL) ? lendingSQL : entityManager.merge(lendingSQL);
        entityManager.remove(managed);
    }
}
