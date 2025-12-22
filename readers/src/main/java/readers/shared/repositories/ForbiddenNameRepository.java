package readers.shared.repositories;

import readers.shared.model.ForbiddenName;

import java.util.Optional;

public interface ForbiddenNameRepository {
    ForbiddenName save(ForbiddenName forbiddenName);
    Optional<ForbiddenName> findByForbiddenName(String forbiddenName);
}