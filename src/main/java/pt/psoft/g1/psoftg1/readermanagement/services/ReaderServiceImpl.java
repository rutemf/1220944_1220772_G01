package pt.psoft.g1.psoftg1.readermanagement.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pt.psoft.g1.psoftg1.readermanagement.model.ReaderDetails;
import pt.psoft.g1.psoftg1.readermanagement.repositories.ReaderRepository;
import pt.psoft.g1.psoftg1.usermanagement.model.User;
import pt.psoft.g1.psoftg1.usermanagement.repositories.UserRepository;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReaderServiceImpl implements ReaderService {
    private final ReaderRepository readerRepo;
    private final UserRepository userRepo;
    private int readerID = 0;
    @Override
    public ReaderDetails create(CreateReaderRequest request) throws Exception {
        ReaderDetails newReaderDetails = null;

        //This should be wrapped on a try catch to avoid any domain exceptions, this way we make sure we catch everything
        try {
            if(this.userRepo.findByUsername(request.getUsername()).isPresent()) {
                throw new Exception("A user with the provided username is already registered");
            }

            if(findByReaderNumber(String.format("%d/%d", LocalDate.now().getYear(), readerID + 1)).isEmpty()) {
                throw new Exception("A reader with provided reader number is already registered");
            }

            if(findByPhoneNumber(request.getPhoneNumber()).isEmpty()) {
                throw new Exception("A reader with provided phone number is already registered");
            }


            User tempUser = User.newUser(request.getUsername(), request.getPassword(), request.getFullName());
            User user = this.userRepo.save(tempUser);

            request.setNumber(String.valueOf(++readerID));
            newReaderDetails = new ReaderDetails(Integer.parseInt(request.getNumber()), user, request.getBirthDate(), request.getPhoneNumber(), request.getGdpr(), request.getMarketing(), request.getThirdParty());
            this.readerRepo.save(newReaderDetails);
        } catch(Exception e) {
            throw new Exception("One of the provided data does not match domain criteria: " + e.getMessage());
        }

        return newReaderDetails;
    }

    @Override
    public ReaderDetails save(ReaderDetails readerDetails) {
        return this.readerRepo.save(readerDetails);
    }

    @Override
    public Optional<ReaderDetails> findByPhoneNumber(String phoneNumber) {
        return this.readerRepo.findByPhoneNumber(phoneNumber.toString());
    }

    @Override
    public Optional<ReaderDetails> findByReaderNumber(String readerNumber) {
        return this.readerRepo.findByReaderNumber(readerNumber.toString());
    }

    @Override
    public Optional<ReaderDetails> findByUsername(final String username) {
        return this.readerRepo.findByUsername(username);
    };


    @Override
    public Iterable<ReaderDetails> findAll() {
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
