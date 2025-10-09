package pt.psoft.g1.psoftg1.lendingmanagement.infrastructure.repositories.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import pt.psoft.g1.psoftg1.bookmanagement.model.BookSQL;
import pt.psoft.g1.psoftg1.genremanagement.model.GenreSQL;
import pt.psoft.g1.psoftg1.lendingmanagement.model.Fine;
import pt.psoft.g1.psoftg1.lendingmanagement.model.FineSQL;
import pt.psoft.g1.psoftg1.lendingmanagement.model.LendingSQL;
import pt.psoft.g1.psoftg1.lendingmanagement.repositories.FineRepository;

import java.util.List;
import java.util.Optional;

@Repository
@Profile("sql")
public class FineRepositorySQL implements FineRepository {

    private final EntityManager entityManager;

    public FineRepositorySQL(EntityManager entityManager) { this.entityManager = entityManager; }

    @Override
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
    public Iterable<Fine> findAll() {
        TypedQuery<FineSQL> query = entityManager.createQuery(
        "SELECT f FROM FineSQL f", FineSQL.class);

        List<FineSQL> fineSQLList = query.getResultList();

        return fineSQLList.stream().map(FineSQL::toDomain).toList();
    }

    @Override
    public Fine save(Fine fine) {
        GenreSQL genreSQL = GenreSQL.fromDomain(fine.getLending().getBook().getGenre());
        BookSQL bookSQL = BookSQL.fromDomain(fine.getLending().getBook(), genreSQL);
        LendingSQL lendingSQL = LendingSQL.fromDomain(fine.getLending(), bookSQL);
        FineSQL fineSQL = FineSQL.fromDomain(fine, lendingSQL);

        if (fineSQL.getId() == null) {
            entityManager.persist(fineSQL);
            return fineSQL.toDomain();
        } else {
            FineSQL merged = entityManager.merge(fineSQL);
            return merged.toDomain();
        }
    }
}
