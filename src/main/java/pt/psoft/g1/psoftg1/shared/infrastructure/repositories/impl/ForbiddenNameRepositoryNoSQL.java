package pt.psoft.g1.psoftg1.shared.infrastructure.repositories.impl;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import pt.psoft.g1.psoftg1.shared.model.ForbiddenName;
import pt.psoft.g1.psoftg1.shared.repositories.ForbiddenNameRepository;

import java.util.List;
import java.util.Optional;

@Repository
@Profile("nosql")
public class ForbiddenNameRepositoryNoSQL implements ForbiddenNameRepository {

    @Override
    public Iterable<ForbiddenName> findAll() {
        return null;
    }

    @Override
    public List<ForbiddenName> findByForbiddenNameIsContained(String pat) {
        return List.of();
    }

    @Override
    public ForbiddenName save(ForbiddenName forbiddenName) {
        return null;
    }

    @Override
    public Optional<ForbiddenName> findByForbiddenName(String forbiddenName) {
        return Optional.empty();
    }

    @Override
    public int deleteForbiddenName(String forbiddenName) {
        return 0;
    }
}
