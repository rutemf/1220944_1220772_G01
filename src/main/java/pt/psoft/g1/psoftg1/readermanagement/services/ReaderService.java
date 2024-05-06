package pt.psoft.g1.psoftg1.readermanagement.services;

import java.util.Optional;

import pt.psoft.g1.psoftg1.readermanagement.model.ReaderDetails;

/**
 *
 */
public interface ReaderService {
    ReaderDetails create(CreateReaderRequest request) throws Exception;
    ReaderDetails save(ReaderDetails readerDetails);
    Optional<ReaderDetails> findByUsername(final String username);
    Optional<ReaderDetails> findByPhoneNumber(String phoneNumber);
    Optional<ReaderDetails> findByReaderNumber(String readerNumber);
    Iterable<ReaderDetails> findAll();

/*
    Optional<Reader> update(UpdateReaderRequest request) throws Exception;
*/
}
