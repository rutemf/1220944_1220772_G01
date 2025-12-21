package books.shared.infrastructure.repositories.impl;

import books.shared.model.ForbiddenName;
import books.shared.repositories.ForbiddenNameRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface SpringDataForbiddenNameRepository
        extends ForbiddenNameRepository, CrudRepository<ForbiddenName, Long> {
    @Query("SELECT fn FROM ForbiddenName fn" + " WHERE :pat LIKE CONCAT('%', fn.forbiddenName, '%') ")
    List<ForbiddenName> findByForbiddenNameIsContained(String pat);

    @Override
    @Query("SELECT fn " + "FROM ForbiddenName fn " + "WHERE fn.forbiddenName = :forbiddenName")
    Optional<ForbiddenName> findByForbiddenName(String forbiddenName);

    @Override
    @Modifying
    @Query("DELETE FROM ForbiddenName fn WHERE fn.forbiddenName = :forbiddenName")
    int deleteForbiddenName(String forbiddenName);

}
