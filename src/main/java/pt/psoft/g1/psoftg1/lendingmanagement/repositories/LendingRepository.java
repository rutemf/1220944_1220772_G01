package pt.psoft.g1.psoftg1.lendingmanagement.repositories;

import pt.psoft.g1.psoftg1.lendingmanagement.model.Lending;
import pt.psoft.g1.psoftg1.lendingmanagement.model.LendingNumber;

import java.util.List;
import java.util.Optional;

public interface LendingRepository {

    Optional<Lending> findByLendingNumber(LendingNumber lendingNumber);

    Optional<Lending> findOpenByReaderNumberAndIsbn(String readerNumber, String isbn);

    List<Lending> listAllByIsbn(String isbn);

    List<Lending> listAllByReaderNumber(String readerNumber);

    Iterable<Lending> findAll();

    int getCountFromCurrentYear();

    Lending save(Lending lending);

}
