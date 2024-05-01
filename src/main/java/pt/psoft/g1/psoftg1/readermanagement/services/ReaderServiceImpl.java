package pt.psoft.g1.psoftg1.readermanagement.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pt.psoft.g1.psoftg1.readermanagement.model.BirthDate;
import pt.psoft.g1.psoftg1.readermanagement.model.PhoneNumber;
import pt.psoft.g1.psoftg1.readermanagement.model.Reader;
import pt.psoft.g1.psoftg1.readermanagement.model.ReaderNumber;
import pt.psoft.g1.psoftg1.readermanagement.repositories.ReaderRepository;
import pt.psoft.g1.psoftg1.usermanagement.model.User;
import pt.psoft.g1.psoftg1.usermanagement.repositories.UserRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReaderServiceImpl implements ReaderService {
    private final ReaderRepository readerRepo;
    private final UserRepository userRepo;
    private int readerID = 0;
    @Override
    public Reader create(CreateReaderRequest request) throws Exception {
        Reader newReader = null;

        //This should be wrapped on a try catch to avoid any domain exceptions, this way we make sure we catch everything
        try {
            if(this.userRepo.findByUsername(request.getUsername()).isPresent()) {
                throw new Exception("A user with the provided username is already registered");
            }

            if(findByReaderNumber(new ReaderNumber(LocalDate.now().getYear(), readerID+1)) != null) {
                throw new Exception("A reader with provided reader number is already registered");
            }

            if(findByPhoneNumber(new PhoneNumber(request.getPhoneNumber())) != null) {
                throw new Exception("A reader with provided phone number is already registered");
            }


            User tempUser = new User(request.getUsername(), request.getPassword());
            User user = this.userRepo.save(tempUser);

            request.setNumber(String.valueOf(++readerID));
            newReader = new Reader(Integer.parseInt(request.getNumber()), user, request.getFullName(), request.getBirthDate(), request.getPhoneNumber(), request.getGdpr(), request.getMarketing(), request.getThirdParty());
            this.readerRepo.save(newReader);
        } catch(Exception e) {
            throw new Exception("One of the provided data does not match domain criteria: " + e.getMessage());
        }

        return newReader;
    }

    @Override
    public Reader save(Reader reader) {
        return this.readerRepo.save(reader);
    }

    @Override
    public Optional<Reader> findByPhoneNumber(PhoneNumber phoneNumber) {
        return this.readerRepo.findByPhoneNumber(phoneNumber.toString());
    }

    @Override
    public Optional<Reader> findByReaderNumber(ReaderNumber readerNumber) {
        return this.readerRepo.findByReaderNumber(readerNumber.toString());
    }

    @Override
    public List<Reader> findAll() {
        return this.readerRepo.findAll();
    }

/*
    @Override
    public Optional<Reader> update(UpdateReaderRequest request) throws Exception {
        String[] dateParts = request.getBirthDate().split("/");

        if(dateParts.length != 3) {
            throw new Exception("Invalid birthDate format");
        }

        int year = Integer.parseInt(dateParts[0]);
        int month = Integer.parseInt(dateParts[1]);
        int day = Integer.parseInt(dateParts[2]);
        LocalDate date = LocalDate.of(year, month, day);

        String[] numberParts = request.getNumber().split("/");
        if(numberParts.length != 2) {
            throw new Exception("Invalid number format");
        }

        int numberYear = Integer.parseInt(dateParts[0]);
        int numberNumber = Integer.parseInt(dateParts[1]);

        Optional<Reader> optReader = this.findByReaderNumber(new ReaderNumber(numberYear, numberNumber));
        if(!optReader.isPresent()) {
            throw new Exception("Could not find reader with provided readerNumber");
        }

        Reader reader = optReader.get();

        this.readerRepo.updateUser(reader.getUser().getId(), request.getUsername(), request.getPassword());
        return this.readerRepo.updateReader(request.getNumber(), request.getFullName(), request.getPhoneNumber(), date, request.getMarketing(), request.getThirdParty());
    }
*/
}
