package pt.psoft.g1.psoftg1.readermanagement.services;

import java.util.List;
import java.util.Optional;

import pt.psoft.g1.psoftg1.exceptions.NotFoundException;
import pt.psoft.g1.psoftg1.readermanagement.model.EmailAddress;
import pt.psoft.g1.psoftg1.readermanagement.model.PhoneNumber;
import pt.psoft.g1.psoftg1.readermanagement.model.Reader;
import pt.psoft.g1.psoftg1.readermanagement.model.ReaderNumber;
import pt.psoft.g1.psoftg1.readermanagement.services.CreateReaderRequest;
import pt.psoft.g1.psoftg1.usermanagement.services.CreateUserRequest;

/**
 *
 */
public interface ReaderService {
    Reader create(CreateReaderRequest request) throws Exception;
    Reader save(Reader reader) throws Exception;

    Reader findByEmail(EmailAddress emailAddress);
    Reader findByPhoneNumber(PhoneNumber phoneNumber);
    Reader findByReaderNumber(ReaderNumber readerNumber);
}
