package pt.psoft.g1.psoftg1.readermanagement.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import pt.psoft.g1.psoftg1.readermanagement.model.ReaderDetails;

/**
 *
 */
public interface ReaderService {
    ReaderDetails create(CreateReaderRequest request);
    ReaderDetails update(Long id, UpdateReaderRequest request, long desireVersion);
    Optional<ReaderDetails> findByUsername(final String username);
    Optional<ReaderDetails> findByReaderNumber(String readerNumber);
    Iterable<ReaderDetails> findAll();
    List<ReaderDetails> findTopReaders(int minTop);
    List<ReaderBookCountDTO> findTopByGenre(String genre, LocalDate startDate, LocalDate endDate);
    //Optional<Reader> update(UpdateReaderRequest request) throws Exception;
}
