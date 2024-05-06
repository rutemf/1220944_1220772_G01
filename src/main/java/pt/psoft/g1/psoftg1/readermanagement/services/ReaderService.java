package pt.psoft.g1.psoftg1.readermanagement.services;

import pt.psoft.g1.psoftg1.readermanagement.model.PhoneNumber;
import pt.psoft.g1.psoftg1.readermanagement.model.Reader;
import pt.psoft.g1.psoftg1.readermanagement.model.ReaderNumber;

import java.util.Optional;

/**
 *
 */
public interface ReaderService {
    Reader create(CreateReaderRequest request) throws Exception;
    Reader save(Reader reader);
    Optional<Reader> findByPhoneNumber(PhoneNumber phoneNumber);
    Optional<Reader> findByReaderNumber(ReaderNumber readerNumber);
    Optional<Reader> findByUsername(final String username);
    Iterable<Reader> findAll();

/*
    Optional<Reader> update(UpdateReaderRequest request) throws Exception;
*/
}
