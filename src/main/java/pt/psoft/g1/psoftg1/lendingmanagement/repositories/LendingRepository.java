package pt.psoft.g1.psoftg1.lendingmanagement.repositories;

import pt.psoft.g1.psoftg1.lendingmanagement.model.Lending;
import pt.psoft.g1.psoftg1.shared.services.Page;

import java.util.List;
import java.util.Optional;

public interface LendingRepository {

    Optional<Lending> findByLendingNumber(String lendingNumber);

    List<Lending> listByReaderNumberAndIsbn(String readerNumber, String isbn);

    int getCountFromCurrentYear();

    List<Lending> listOutstandingByReaderNumber(String readerNumber);

    Double getAverageDuration();

    Lending save(Lending lending);

    List<Lending> getOverdue(Page page);

}
