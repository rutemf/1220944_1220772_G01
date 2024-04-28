package pt.psoft.g1.psoftg1.readermanagement.repositories;

import pt.psoft.g1.psoftg1.readermanagement.model.EmailAddress;
import pt.psoft.g1.psoftg1.readermanagement.model.PhoneNumber;
import pt.psoft.g1.psoftg1.readermanagement.model.Reader;
import pt.psoft.g1.psoftg1.readermanagement.model.ReaderNumber;

/**
 *
 */
public interface ReaderRepository {
    Reader findByEmail(EmailAddress emailAddress);
    Reader findByPhoneNumber(PhoneNumber phoneNumber);
    Reader findByReaderNumber(ReaderNumber readerNumber);
    Reader save(Reader reader) throws Exception;
}
