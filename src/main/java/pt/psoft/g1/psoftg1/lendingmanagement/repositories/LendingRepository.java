package pt.psoft.g1.psoftg1.lendingmanagement.repositories;

import pt.psoft.g1.psoftg1.lendingmanagement.model.Lending;

import java.util.List;
import java.util.Optional;

public interface LendingRepository {

    Optional<Lending> findByLendingNumber(String lendingNumber);

    Optional<Lending> findOpenByReaderNumberAndIsbn(String readerNumber, String isbn);

    List<Lending> listClosedByReaderNumberAndIsbn(String readerNumber, String isbn);

    List<Lending> listByReaderNumberAndIsbn(String readerNumber, String isbn);

    List<Lending> listAllByIsbn(String isbn);

    List<Lending> listAllByReaderNumber(String readerNumber);

    Iterable<Lending> findAll();

    int getCountFromCurrentYear();

    int getOutstandingCountFromReader(String readerNumber);

    List<Lending> listOutstandingByReaderNumber(String readerNumber);

    Lending save(Lending lending);

}
