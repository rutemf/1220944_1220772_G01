package auth_users.shared.repositories;

import auth_users.shared.model.ForbiddenName;

import java.util.List;
import java.util.Optional;

public interface ForbiddenNameRepository {
    Iterable<ForbiddenName> findAll();

    List<ForbiddenName> findByForbiddenNameIsContained(String pat);

    ForbiddenName save(ForbiddenName forbiddenName);

    Optional<ForbiddenName> findByForbiddenName(String forbiddenName);

    int deleteForbiddenName(String forbiddenName);

}
