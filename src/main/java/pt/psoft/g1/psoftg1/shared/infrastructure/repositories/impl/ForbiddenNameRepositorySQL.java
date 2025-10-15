package pt.psoft.g1.psoftg1.shared.infrastructure.repositories.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import pt.psoft.g1.psoftg1.shared.model.ForbiddenName;
import pt.psoft.g1.psoftg1.shared.model.ForbiddenNameSQL;
import pt.psoft.g1.psoftg1.shared.repositories.ForbiddenNameRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@Profile("sql")
public class ForbiddenNameRepositorySQL implements ForbiddenNameRepository {

    private final EntityManager entityManager;

    public ForbiddenNameRepositorySQL(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Iterable<ForbiddenName> findAll() {
        TypedQuery<ForbiddenNameSQL> query = entityManager.createQuery(
        "SELECT fn FROM ForbiddenNameSQL fn", ForbiddenNameSQL.class);

        List<ForbiddenNameSQL> results = query.getResultList();
        return results.stream().map(ForbiddenNameSQL::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<ForbiddenName> findByForbiddenNameIsContained(String pat) {
        TypedQuery<ForbiddenNameSQL> query = entityManager.createQuery(
        "SELECT fn FROM ForbiddenNameSQL fn WHERE :pat LIKE CONCAT('%', fn.forbiddenName, '%')", ForbiddenNameSQL.class);
        query.setParameter("pat", pat);

        return query.getResultList().stream().map(ForbiddenNameSQL::toDomain).collect(Collectors.toList());
    }

    @Override
    public ForbiddenName save(ForbiddenName forbiddenName) {
        ForbiddenNameSQL fnSQL = ForbiddenNameSQL.fromDomain(forbiddenName);

        if (fnSQL.getId() == null) {
            entityManager.persist(fnSQL);
        } else {
            fnSQL = entityManager.merge(fnSQL);
        }

        return fnSQL.toDomain();
    }

    @Override
    public Optional<ForbiddenName> findByForbiddenName(String forbiddenName) {
        TypedQuery<ForbiddenNameSQL> query = entityManager.createQuery(
        "SELECT fn FROM ForbiddenNameSQL fn WHERE fn.forbiddenName = :forbiddenName", ForbiddenNameSQL.class);
        query.setParameter("forbiddenName", forbiddenName);

        List<ForbiddenNameSQL> results = query.getResultList();
        return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0).toDomain());
    }

    @Override
    public int deleteForbiddenName(String forbiddenName) {
        return entityManager.createQuery(
     "DELETE FROM ForbiddenNameSQL fn WHERE fn.forbiddenName = :forbiddenName")
        .setParameter("forbiddenName", forbiddenName).executeUpdate();
    }
}
