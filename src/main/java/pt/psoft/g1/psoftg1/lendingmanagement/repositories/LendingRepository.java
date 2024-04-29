package pt.psoft.g1.psoftg1.lendingmanagement.repositories;

import org.springframework.data.repository.query.Param;
import pt.psoft.g1.psoftg1.lendingmanagement.model.Lending;
import pt.psoft.g1.psoftg1.lendingmanagement.model.LendingNumber;

import java.util.List;
import java.util.Optional;

public interface LendingRepository {

    Optional<Lending> findByLendingNumber(LendingNumber lendingNumber);

    List<Lending> searchByIsbn(@Param("isbn") String isbn);

    List<Lending> searchByReaderNumber(@Param("readerNumber") String readerNumber);

    Iterable<Lending> findAll();

}
