package pt.psoft.g1.psoftg1.lendingmanagement.infrastructure.repositories.impl;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import pt.psoft.g1.psoftg1.lendingmanagement.model.Lending;
import pt.psoft.g1.psoftg1.lendingmanagement.repositories.LendingRepository;

import java.util.List;
import java.util.Optional;

public interface SpringDataLendingRepository extends LendingRepository, CrudRepository<Lending, Long> {
    @Override
    @Query("SELECT l " +
            "FROM Lending l " +
            "WHERE l.lendingNumber.lendingNumber = :lendingNumber")
    Optional<Lending> findByLendingNumber(String lendingNumber);

    //http://www.h2database.com/html/commands.html

    @Override
    @Query("SELECT l " +
            "FROM Lending l " +
                "JOIN Book b ON l.book.pk = b.pk " +
                "JOIN ReaderDetails r ON l.readerDetails.pk = r.pk " +
            "WHERE b.isbn.isbn = :isbn " +
                "AND r.readerNumber.readerNumber = :readerNumber " +
                "AND l.returnedDate IS NULL")
    Optional<Lending> findOpenByReaderNumberAndIsbn(@Param("readerNumber") String readerNumber,
                                                    @Param("isbn") String isbn);

    @Override
    @Query("SELECT l " +
            "FROM Lending l " +
            "JOIN Book b ON l.book.pk = b.pk " +
            "JOIN ReaderDetails r ON l.readerDetails.pk = r.pk " +
            "WHERE b.isbn.isbn = :isbn " +
            "AND r.readerNumber.readerNumber = :readerNumber " +
            "AND l.returnedDate IS NOT NULL")
    List<Lending> listClosedByReaderNumberAndIsbn(@Param("readerNumber")String readerNumber,
                                                  @Param("isbn")String isbn);

    @Override
    @Query("SELECT l " +
            "FROM Lending l " +
            "JOIN Book b ON l.book.pk = b.pk " +
            "JOIN ReaderDetails r ON l.readerDetails.pk = r.pk " +
            "WHERE b.isbn.isbn = :isbn " +
            "AND r.readerNumber.readerNumber = :readerNumber ")
    List<Lending> listByReaderNumberAndIsbn(String readerNumber, String isbn);

    @Override
    @Query("SELECT l " +
            "FROM Lending l " +
                "JOIN Book b ON l.book.pk = b.pk " +
            "WHERE b.isbn.isbn = :isbn")
    List<Lending> listAllByIsbn(@Param("isbn") String isbn);

    @Override
    @Query("SELECT l " +
            "FROM Lending l " +
                "JOIN ReaderDetails r ON l.readerDetails.pk = r.pk " +
            "WHERE r.readerNumber.readerNumber = :readerNumber")
    List<Lending> listAllByReaderNumber(@Param("readerNumber") String readerNumber);

    @Override
    @Query("SELECT COUNT (l) " +
            "FROM Lending l " +
            "WHERE l.lendingNumber.year = YEAR(CURRENT_DATE)")
    int getCountFromCurrentYear();

    @Override
    @Query("SELECT COUNT (l) " +
            "FROM Lending l " +
                "JOIN ReaderDetails r ON l.readerDetails.pk = r.pk " +
            "WHERE r.readerNumber.readerNumber = :readerNumber " +
                "AND CURRENT_DATE > l.limitDate")
    int getOutstandingCountFromReader(String readerNumber);

    @Override
    @Query("SELECT l " +
            "FROM Lending l " +
                "JOIN ReaderDetails r ON l.readerDetails.pk = r.pk " +
            "WHERE r.readerNumber.readerNumber = :readerNumber " +
                "AND CURRENT_DATE > l.limitDate")
    List<Lending> listOutstandingByReaderNumber(String readerNumber);


}
