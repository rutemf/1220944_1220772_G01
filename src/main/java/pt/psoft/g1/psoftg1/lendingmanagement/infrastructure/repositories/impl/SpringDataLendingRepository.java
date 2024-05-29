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
            "WHERE YEAR(l.startDate) = YEAR(CURRENT_DATE)")
    int getCountFromCurrentYear();

    @Override
    @Query("SELECT COUNT (l) " +
            "FROM Lending l " +
                "JOIN ReaderDetails r ON l.readerDetails.pk = r.pk " +
            "WHERE r.readerNumber.readerNumber = :readerNumber " +
                "AND l.returnedDate IS NULL")
    int getOutstandingCountFromReader(@Param("readerNumber") String readerNumber);

    @Override
    @Query("SELECT l " +
            "FROM Lending l " +
                "JOIN ReaderDetails r ON l.readerDetails.pk = r.pk " +
            "WHERE r.readerNumber.readerNumber = :readerNumber " +
                "AND l.returnedDate IS NULL")
    List<Lending> listOutstandingByReaderNumber(@Param("readerNumber") String readerNumber);

    @Override
    @Query("SELECT avg(count(l)) " +
            "FROM Lending l " +
            "WHERE l.book.genre.genre = :genre " +
            "AND YEAR(l.startDate) = :year " +
            "AND MONTH(l.startDate) = :month ")
    double getAverageLendingsPerGenrePerMonth(int year, int month, String genre);

}
/*
interface LendingRepoCustom {
    List<Lending> listOverdueByTardiness(Page page);

}

@RequiredArgsConstructor
class LendingRepoCustomImpl implements LendingRepoCustom {
    // get the underlying JPA Entity Manager via spring thru constructor dependency
    // injection
    private final EntityManager em;

    @Override
    public List<Lending> listOverdueByTardiness(Page page){

        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<Lending> cq = cb.createQuery(Lending.class);
        final Root<Lending> root = cq.from(Lending.class);
        cq.select(root);

        final List<Predicate> where = new ArrayList<>();

        where.add(cb.greaterThan(root.))
    }

}*/
