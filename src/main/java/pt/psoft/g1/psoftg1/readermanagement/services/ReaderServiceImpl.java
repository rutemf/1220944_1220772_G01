package pt.psoft.g1.psoftg1.readermanagement.services;

import pt.psoft.g1.psoftg1.readermanagement.model.EmailAddress;
import pt.psoft.g1.psoftg1.readermanagement.model.PhoneNumber;
import pt.psoft.g1.psoftg1.readermanagement.model.Reader;
import pt.psoft.g1.psoftg1.readermanagement.model.ReaderNumber;
import pt.psoft.g1.psoftg1.readermanagement.repositories.ReaderRepository;
import pt.psoft.g1.psoftg1.readermanagement.services.CreateReaderRequest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReaderServiceImpl implements ReaderService {
    private ReaderRepository readerRepo;
    private int readerID = 0;
    private List<Reader> readerList = new ArrayList<>();

    @Override
    public Reader create(CreateReaderRequest request) throws Exception {
        Reader newReader = null;

        //This should be wrapped on a try catch to avoid any domain exceptions, this way we make sure we catch everything
        try {
            if(findByReaderNumber(new ReaderNumber(LocalDate.now().getYear(), readerID+1)) != null) {
                throw new Exception("A reader with provided reader number is already registered");
            }

            if(findByEmail(new EmailAddress(request.getEmailAddress())) != null) {
                throw new Exception("A reader with provided email address is already registered");
            }

            if(findByPhoneNumber(new PhoneNumber(request.getPhoneNumber())) != null) {
                throw new Exception("A reader with provided phone number is already registered");
            }

            request.setNumber(++readerID);
            newReader = new Reader(request.getNumber(), request.getUser().getUsername(), request.getUser().getPassword(), request.getFullName(), request.getEmailAddress(), request.getBirthDate(), request.getPhoneNumber(), request.getGdpr(), request.getMarketing(), request.getThirdParty());
        } catch(Exception e) {
            throw new Exception("One of the provided data does not match domain criteria: " + e.getMessage());
        }

        return newReader;
    }

    @Override
    public Reader save(Reader reader) throws Exception {
        return this.readerRepo.save(reader);
    }

    @Override
    public Reader findByEmail(EmailAddress emailAddress) {
        return null;
    }

    @Override
    public Reader findByPhoneNumber(PhoneNumber phoneNumber) {
        return null;
    }

    @Override
    public Reader findByReaderNumber(ReaderNumber readerNumber) {
        return null;
    }
}
