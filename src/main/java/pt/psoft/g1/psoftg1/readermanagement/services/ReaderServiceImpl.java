package pt.psoft.g1.psoftg1.readermanagement.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pt.psoft.g1.psoftg1.exceptions.ConflictException;
import pt.psoft.g1.psoftg1.exceptions.NotFoundException;
import pt.psoft.g1.psoftg1.readermanagement.model.ReaderDetails;
import pt.psoft.g1.psoftg1.readermanagement.repositories.ReaderRepository;
import pt.psoft.g1.psoftg1.usermanagement.model.Reader;
import pt.psoft.g1.psoftg1.usermanagement.repositories.UserRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;

@Service
@RequiredArgsConstructor
public class ReaderServiceImpl implements ReaderService {
    private final ReaderRepository readerRepo;
    private final UserRepository userRepo;
    private final ReaderMapper readerMapper;

    private int readerID = 0;
    @Override
    public ReaderDetails create(CreateReaderRequest request) {
        if (userRepo.findByUsername(request.getUsername()).isPresent()) {
            throw new ConflictException("Username already exists!");
        }

        int count = readerRepo.getCountFromCurrentYear();
        Reader reader = readerMapper.createReader(request);
        ReaderDetails rd = readerMapper.createReaderDetails(count+1, reader, request);
/*
        User tempUser = Reader.newReader(request.getUsername(), request.getPassword(), request.getFullName());
        User user = userRepo.save(tempUser);
        request.setNumber(String.valueOf(++readerID));
        ReaderDetails newReaderDetails = new ReaderDetails(Integer.parseInt(request.getNumber()), user, request.getBirthDate(), request.getPhoneNumber(), request.getGdpr(), request.getMarketing(), request.getThirdParty());
        readerRepo.save(newReaderDetails);
        return newReaderDetails;
*/
        userRepo.save(reader);
        return readerRepo.save(rd);
    }

    @Override
    public ReaderDetails update(final Long id, final UpdateReaderRequest request, final long desiredVersion){
        final ReaderDetails readerDetails = readerRepo.findByUserId(id)
                .orElseThrow(() -> new NotFoundException("Cannot find reader"));

        readerDetails.applyPatch(desiredVersion, request);

        userRepo.save(readerDetails.getReader());
        return readerRepo.save(readerDetails);
    }


    @Override
    public Optional<ReaderDetails> findByReaderNumber(String readerNumber) {
        return this.readerRepo.findByReaderNumber(readerNumber);
    }

    @Override
    public Optional<ReaderDetails> findByUsername(final String username) {
        return this.readerRepo.findByUsername(username);
    };


    @Override
    public Iterable<ReaderDetails> findAll() {
        return this.readerRepo.findAll();
    }

    @Override
    public List<ReaderDetails> findTopReaders(int minTop) {
        if(minTop < 1) {
            throw new IllegalArgumentException("Minimum top reader must be greater than 0");
        }

        Pageable pageableRules = PageRequest.of(0,minTop);
        Page<ReaderDetails> page = readerRepo.findTopReaders(pageableRules);
        return page.getContent();
    }

/*
    @Override
    public Optional<Reader> update(UpdateReaderRequest request) {
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
