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

    //http://www.h2database.com/html/commands.html

    @Override
    @Query("SELECT l " +
            "FROM Lending l " +
            "JOIN Book b ON l.book.isbn = b.isbn " +
            "JOIN Reader r ON l.reader.readerNumber = l.reader.readerNumber " +
            "WHERE b.isbn = :isbn AND l.returnDate IS NULL")
    Optional<Lending> findOpenByReaderNumberAndIsbn(@Param("readerNumber") String readerNumber, @Param("isbn") String isbn);

    @Override
    @Query("SELECT l " +
            "FROM Lending l " +
            "JOIN Book b ON l.book.isbn = b.isbn " +
            "WHERE b.isbn = :isbn")
    List<Lending> listAllByIsbn(@Param("isbn") String isbn);

    @Override
    @Query("SELECT l " +
            "FROM Lending l " +
            "JOIN Reader r ON l.reader.readerNumber = r.readerNumber " +
            "WHERE r.readerNumber = :readerNumber")
    List<Lending> listAllByReaderNumber(@Param("readerNumber") String readerNumber);

    @Override
    @Query("SELECT COUNT (l) " +
            "FROM Lending l " +
            "WHERE l.lendingNumber.year = YEAR(CURRENT_DATE)")
    int getCountFromCurrentYear();

    @Override
    @Query("SELECT COUNT (l) " +
            "FROM Lending l " +
            "JOIN Reader r ON l.reader.readerNumber = r.readerNumber " +
            "WHERE r.readerNumber = :readerNumber AND CURRENT_DATE > l.limitDate")
    int getOutstandingCountFromReader(String readerNumber);

    @Override
    @Query("SELECT l " +
            "FROM Lending l " +
            "JOIN Reader r ON l.reader.readerNumber = r.readerNumber " +
            "WHERE r.readerNumber = :readerNumber AND CURRENT_DATE > l.limitDate")
    List<Lending> listOutstandingByReaderNumber(String readerNumber);


}
