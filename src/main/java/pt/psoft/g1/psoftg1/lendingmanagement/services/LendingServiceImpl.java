package pt.psoft.g1.psoftg1.lendingmanagement.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pt.psoft.g1.psoftg1.bookmanagement.repositories.BookRepository;
import pt.psoft.g1.psoftg1.exceptions.NotFoundException;
import pt.psoft.g1.psoftg1.lendingmanagement.model.Lending;
import pt.psoft.g1.psoftg1.lendingmanagement.model.LendingNumber;
import pt.psoft.g1.psoftg1.lendingmanagement.repositories.LendingRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LendingServiceImpl implements LendingService{
    private final LendingRepository lendingRepository;
    private final BookRepository bookRepository;
    //private final AuthorRepository authorRepository;
    private final LendingMapper lendingMapper;

    @Override
    public Iterable<Lending> findAll() {
        return lendingRepository.findAll();
    }

    @Override
    public Optional<Lending> findByLendingNumber(final String lendingNumber) {
        return lendingRepository.findByLendingNumber(new LendingNumber(lendingNumber));
    }

    @Override
    public Lending create(final CreateLendingDto resource) {
/*
        final Lending lending = lendingMapper.create(resource);
*/

        return null;
    }

    //TODO
    @Override
    public Lending update(final String lendingNumber, SetLendingReturnedDto resource) {
        // first let's check if the object exists so we don't create a new object with
        // save
        final var l = lendingRepository.findByLendingNumber(new LendingNumber(lendingNumber))
                .orElseThrow(() -> new NotFoundException("Cannot update an object that does not yet exist"));
        //TODO

        return null;
    }
}
