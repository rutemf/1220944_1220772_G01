package pt.psoft.g1.psoftg1.readermanagement.repositories;

import pt.psoft.g1.psoftg1.readermanagement.model.Reader;

import java.util.List;
import java.util.Optional;

/**
 *
 */
public interface ReaderRepository {
    Reader findByPhoneNumber(String phoneNumber);
    Optional<Reader> findByReaderNumber(String readerNumber);
    Reader save(Reader reader);
    List<Reader> findAll();
}
