package readers.readers.repositories;

import readers.readers.model.ReaderDetails;

import java.util.Optional;

public interface ReaderRepository {
    Long count();
    ReaderDetails save(ReaderDetails readerDetails);
    Optional<ReaderDetails> findByReaderNumber(String readerNumber);
    ReaderDetails markAsActive(String readerNumber);
    ReaderDetails markAsRejected(String readerNumber);
}
