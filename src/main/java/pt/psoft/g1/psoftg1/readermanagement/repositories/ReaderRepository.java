package pt.psoft.g1.psoftg1.readermanagement.repositories;

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
public interface ReaderRepository {
    Reader create(CreateReaderRequest request) throws Exception;

    Reader findByEmail(EmailAddress emailAddress);
    Reader findByPhoneNumber(PhoneNumber phoneNumber);
    Reader findByReaderNumber(ReaderNumber readerNumber);
}
