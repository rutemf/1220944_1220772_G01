package pt.psoft.g1.psoftg1.readermanagement.repositories;

import jakarta.validation.constraints.NotNull;
import pt.psoft.g1.psoftg1.readermanagement.model.EmailAddress;
import pt.psoft.g1.psoftg1.readermanagement.model.PhoneNumber;
import pt.psoft.g1.psoftg1.readermanagement.model.Reader;
import pt.psoft.g1.psoftg1.readermanagement.model.ReaderNumber;

import java.util.List;

/**
 *
 */
public interface ReaderRepository {
    Reader findByPhoneNumber(String phoneNumber);
    Reader findByReaderNumber(String readerNumber);
    Reader save(Reader reader);
    List<Reader> findAll();
}
