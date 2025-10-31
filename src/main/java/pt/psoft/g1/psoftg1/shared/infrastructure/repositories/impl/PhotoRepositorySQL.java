package pt.psoft.g1.psoftg1.shared.infrastructure.repositories.impl;

import jakarta.persistence.EntityManager;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import pt.psoft.g1.psoftg1.shared.model.Photo;
import pt.psoft.g1.psoftg1.shared.dataschema.PhotoSQL;
import pt.psoft.g1.psoftg1.shared.repositories.PhotoRepository;
import java.nio.file.Paths;

@Repository
@Profile("sql")
public class PhotoRepositorySQL implements PhotoRepository {

    private final EntityManager entityManager;

    public PhotoRepositorySQL(EntityManager entityManager) { this.entityManager = entityManager; }

    @Override
    public void deleteByPhotoFile(String photoFile) {
        PhotoSQL entity = PhotoSQL.fromDomain(new Photo(Paths.get(photoFile)));
        PhotoSQL managed = entityManager.contains(entity) ? entity : entityManager.merge(entity);
        entityManager.remove(managed);
    }
}
