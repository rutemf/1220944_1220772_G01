package readers.readers.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import readers.readers.model.ReaderDetails;
import readers.readers.model.ReaderStatus;

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

    @Override
    public ReaderDetails markAsActive(String readerNumber) {
        ReaderDetails readerDetails = entityManager.createQuery(
            "SELECT rd FROM ReaderDetails rd WHERE rd.readerNumber.readerNumber = :rn", ReaderDetails.class)
            .setParameter("rn", readerNumber).getSingleResult();

        readerDetails.setStatus(ReaderStatus.ACTIVE);
        entityManager.merge(readerDetails);

        return readerDetails;
    }

    @Override
    public ReaderDetails markAsRejected(String readerNumber) {
        ReaderDetails readerDetails = entityManager.createQuery(
            "SELECT rd FROM ReaderDetails rd WHERE rd.readerNumber.readerNumber = :rn", ReaderDetails.class)
            .setParameter("rn", readerNumber).getSingleResult();

        readerDetails.setStatus(ReaderStatus.REJECTED);
        entityManager.merge(readerDetails);

        return readerDetails;
    }
}
