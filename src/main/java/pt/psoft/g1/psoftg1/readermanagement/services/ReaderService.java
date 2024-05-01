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
    Reader save(Reader reader);
    Optional<Reader> findByPhoneNumber(PhoneNumber phoneNumber);
    Optional<Reader> findByReaderNumber(ReaderNumber readerNumber);
    List<Reader> findAll();

/*
    Optional<Reader> update(UpdateReaderRequest request) throws Exception;
*/
}
