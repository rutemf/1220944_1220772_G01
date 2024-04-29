package pt.psoft.g1.psoftg1.lendingmanagement.infrastructure.repositories.impl;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import pt.psoft.g1.psoftg1.lendingmanagement.model.Lending;
import pt.psoft.g1.psoftg1.lendingmanagement.model.LendingNumber;
import pt.psoft.g1.psoftg1.lendingmanagement.repositories.LendingRepository;

import java.util.List;
import java.util.Optional;

public interface SpringDataLendingRepository extends LendingRepository, CrudRepository<Lending, LendingNumber> {
    @Override
    Optional<Lending> findByLendingNumber(LendingNumber lendingNumber);

    @Override
    @Query("SELECT l " +
            "FROM Lending l " +
            "JOIN Book b ON l.book.isbn = b.isbn " +
            "WHERE b.isbn = :isbn")
    List<Lending> searchByIsbn(@Param("isbn") String isbn);

    @Override
    @Query("SELECT l " +
            "FROM Lending l " +
            "JOIN Reader r ON l.reader.readerNumber = r.readerNumber " +
            "WHERE r.readerNumber = :readerNumber")
    List<Lending> searchByReaderNumber(@Param("readerNumber") String readerNumber);


}
