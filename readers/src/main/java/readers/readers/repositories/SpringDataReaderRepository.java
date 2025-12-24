package readers.readers.repositories;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import readers.readers.model.ReaderDetails;

@Repository
@RequiredArgsConstructor
public class SpringDataReaderRepository implements ReaderRepository {

    private final EntityManager entityManager;

    @Override
    public Long count() {
        return entityManager.createQuery("SELECT COUNT(rd) FROM ReaderDetails rd", Long.class).getSingleResult();
    }

    @Override
    public ReaderDetails save(ReaderDetails readerDetails) {
        if (readerDetails.getId() == null || entityManager.find(ReaderDetails.class, readerDetails.getId()) == null) {
            entityManager.persist(readerDetails);
        } else {
            readerDetails = entityManager.merge(readerDetails);
        }

        return readerDetails;
    }
}
