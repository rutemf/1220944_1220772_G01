package pt.psoft.g1.psoftg1.lendingmanagement.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pt.psoft.g1.psoftg1.bookmanagement.repositories.BookRepository;
import pt.psoft.g1.psoftg1.exceptions.LendingForbiddenException;
import pt.psoft.g1.psoftg1.exceptions.NotFoundException;
import pt.psoft.g1.psoftg1.lendingmanagement.model.Fine;
import pt.psoft.g1.psoftg1.lendingmanagement.model.Lending;
import pt.psoft.g1.psoftg1.lendingmanagement.repositories.FineRepository;
import pt.psoft.g1.psoftg1.lendingmanagement.repositories.LendingRepository;
import pt.psoft.g1.psoftg1.readermanagement.repositories.ReaderRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LendingServiceImpl implements LendingService{
    private final LendingRepository lendingRepository;
    private final FineRepository fineRepository;
    private final BookRepository bookRepository;
    private final ReaderRepository readerRepository;

    @Override

    public Optional<Lending> findByLendingNumber(String lendingNumber){
        return lendingRepository.findByLendingNumber(lendingNumber);
    }

    @Override
    public Iterable<Lending> findAll() {
        return lendingRepository.findAll();
    }

    @Override
    public Lending create(final CreateLendingDto resource) {
        int count = 0;

        for (Lending lending : lendingRepository.listOutstandingByReaderNumber(resource.getReaderNumber())) {
            //Business rule: cannot create a lending if user has late outstanding books to return.
            if (lending.getDaysDelayed() > 0) {
                throw new LendingForbiddenException("Reader has book(s) past their due date");
            }
            count++;
            //Business rule: cannot create a lending if user already has 3 outstanding books to return.
            if (count >= 3) {
                throw new LendingForbiddenException("Reader has three books outstanding already");
            }
        }

        final var b = bookRepository.findByIsbn(resource.getIsbn())
                .orElseThrow(() -> new NotFoundException("Book not found"));
        final var r = readerRepository.findByReaderNumber(resource.getReaderNumber())
                .orElseThrow(() -> new NotFoundException("Reader not found"));
        int seq = lendingRepository.getCountFromCurrentYear()+1;
        final Lending l = new Lending(b,r,seq);

        return lendingRepository.save(l);
    }

    @Override
    public Lending setReturned(final String lendingNumber, final SetLendingReturnedDto resource, final long desiredVersion) {
//TODO: separate methods for lendingNumber and ReaderNumber&ISBN
        Lending lending;

        try{
            lending = lendingRepository.findOpenByReaderNumberAndIsbn(resource.getReaderNumber(), resource.getIsbn())
                    .orElseThrow(() -> new NotFoundException("Cannot find lending with this book"));
        }catch (NotFoundException e){
            lending = lendingRepository.findByLendingNumber(lendingNumber)
                    .orElseThrow(() -> new NotFoundException("Cannot update lending with this lending number"));
        }
        lending.setReturned(desiredVersion, resource.getCommentary());
        if(lending.getDaysDelayed() > 0){
            final var fine = new Fine(lending);
            fineRepository.save(fine);
        }

        return lendingRepository.save(lending);
    }
}
