package genres.shared.infrastructure.repositories.impl;

import genres.shared.model.ForbiddenName;
import genres.shared.repositories.ForbiddenNameRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SpringDataForbiddenNameRepository extends ForbiddenNameRepository, CrudRepository<ForbiddenName, Long> {

    @Override
    @Query("SELECT fn " + "FROM ForbiddenName fn " + "WHERE fn.forbiddenName = :forbiddenName")
    Optional<ForbiddenName> findByForbiddenName(String forbiddenName);

}