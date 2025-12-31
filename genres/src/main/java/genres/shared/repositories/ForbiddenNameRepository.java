package genres.shared.repositories;

import genres.shared.model.ForbiddenName;

import java.util.Optional;

public interface ForbiddenNameRepository {
    ForbiddenName save(ForbiddenName forbiddenName);
    Optional<ForbiddenName> findByForbiddenName(String forbiddenName);
}