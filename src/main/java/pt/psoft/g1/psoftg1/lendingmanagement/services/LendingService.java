package pt.psoft.g1.psoftg1.lendingmanagement.services;

import pt.psoft.g1.psoftg1.lendingmanagement.model.Lending;
import pt.psoft.g1.psoftg1.shared.services.Page;

import java.util.List;
import java.util.Optional;

public interface LendingService {
    /**
     * @param lendingNumber
     * @return {@code Optional<Lending>}
     */
    Optional<Lending> findByLendingNumber(String lendingNumber);
    /**
     * @param readerNumber - Reader Number of the Reader associated with the lending
     * @param isbn - ISBN of the book associated with the lending
     * @return {@code Iterable<Lending>}
     */
    List<Lending> listByReaderNumberAndIsbn(String readerNumber, String isbn);

    Lending create(CreateLendingRequest resource); //No ID passed, as it is auto generated

    Lending setReturned(String id, SetLendingReturnedDto resource, long desiredVersion);

    String getAverageDuration();

    List<Lending> getOverdue(Page page);
}
