package pt.psoft.g1.psoftg1.lendingmanagement.infrastructure.repositories.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import pt.psoft.g1.psoftg1.lendingmanagement.model.Fine;
import pt.psoft.g1.psoftg1.lendingmanagement.model.FineSQL;
import pt.psoft.g1.psoftg1.lendingmanagement.model.LendingSQL;
import pt.psoft.g1.psoftg1.lendingmanagement.repositories.FineRepository;

import java.util.List;
import java.util.Optional;

@Repository
@Profile("sql")
@CacheConfig(cacheNames = "fines")
public class FineRepositorySQL implements FineRepository {

    private final EntityManager entityManager;

    public FineRepositorySQL(EntityManager entityManager) { this.entityManager = entityManager; }

    @Override
    @Cacheable(key = "#lendingNumber")
    public Optional<Fine> findByLendingNumber(String lendingNumber) {
        TypedQuery<FineSQL> query = entityManager.createQuery(
        "SELECT f FROM FineSQL f WHERE f.lending.lendingNumber = :lendingNumber", FineSQL.class);
        query.setParameter("lendingNumber", lendingNumber);

        List<FineSQL> results = query.getResultList();

        if (results.isEmpty()) {
            return Optional.empty();
        }

        FineSQL fineSQL = results.get(0);
        Fine fine = fineSQL.toDomain();
        return Optional.of(fine);
    }

    @Override
    @Cacheable(key = "'allFines'")
    public Iterable<Fine> findAll() {
        TypedQuery<FineSQL> query = entityManager.createQuery(
        "SELECT f FROM FineSQL f", FineSQL.class);

        List<FineSQL> fineSQLList = query.getResultList();

        return fineSQLList.stream().map(FineSQL::toDomain).toList();
    }

    @Override
    public Fine save(Fine fine) {
        FineSQL fineSQL = FineSQL.fromDomain(fine);

        if (fineSQL.getLending() != null) {
            TypedQuery<LendingSQL> q = entityManager.createQuery(
            "SELECT l FROM LendingSQL l WHERE l.lendingNumber = :lendingNumber", LendingSQL.class);

            q.setParameter("lendingNumber", fineSQL.getLending().getLendingNumber());

            List<LendingSQL> results = q.getResultList();
            if (!results.isEmpty()) {
                fineSQL.setLending(results.get(0));
            }
        }

        if (fineSQL.getId() == null) {
            entityManager.persist(fineSQL);
            return fineSQL.toDomain();
        } else {
            FineSQL merged = entityManager.merge(fineSQL);
            return merged.toDomain();
        }
    }
}
