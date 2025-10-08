package pt.psoft.g1.psoftg1.lendingmanagement.infrastructure.repositories.impl;

import jakarta.persistence.EntityManager;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import pt.psoft.g1.psoftg1.lendingmanagement.model.Fine;
import pt.psoft.g1.psoftg1.lendingmanagement.repositories.FineRepository;

import java.util.Optional;

@Repository
@Profile("sql")
public class FineRepositorySQL implements FineRepository {

    private EntityManager entityManager;

    public FineRepositorySQL(EntityManager entityManager) { this.entityManager = entityManager; }

    @Override
    public Optional<Fine> findByLendingNumber(String lendingNumber) {
        return Optional.empty();
    }

    @Override
    public Iterable<Fine> findAll() {
        return null;
    }

    @Override
    public Fine save(Fine fine) {
        if (fine.getId() == null) {
            entityManager.persist(fine);
            return fine;
        } else {
            return entityManager.merge(fine);
        }
    }
}
