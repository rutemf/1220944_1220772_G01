package readers.readers.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import readers.readers.model.ReaderDetails;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SpringDataReaderRepository implements ReaderRepository {

    private final EntityManager entityManager;

    @Override
    public Long count() {
        return entityManager.createQuery("SELECT COUNT(rd) FROM ReaderDetails rd", Long.class).getSingleResult();
    }

    @Override
    public Optional<ReaderDetails> findByReaderNumber(String readerNumber) {
        try {
            ReaderDetails result = entityManager.createQuery(
            "SELECT rd FROM ReaderDetails rd WHERE rd.readerNumber.readerNumber = :rn", ReaderDetails.class)
            .setParameter("rn", readerNumber).getSingleResult();
            return Optional.of(result);
        } catch (NoResultException e) {
            return Optional.empty();
        }
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
