package pt.psoft.g1.psoftg1.lendingmanagement.infrastructure.repositories.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import pt.psoft.g1.psoftg1.lendingmanagement.model.Lending;
import pt.psoft.g1.psoftg1.lendingmanagement.repositories.LendingRepository;
import pt.psoft.g1.psoftg1.shared.services.Page;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface SpringDataLendingRepository extends LendingRepository, LendingRepoCustom, CrudRepository<Lending, Long> {
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
            "AND r.readerNumber.readerNumber = :readerNumber ")
    List<Lending> listByReaderNumberAndIsbn(String readerNumber, String isbn);

    @Override
    @Query("SELECT COUNT (l) " +
            "FROM Lending l " +
            "WHERE YEAR(l.startDate) = YEAR(CURRENT_DATE)")
    int getCountFromCurrentYear();

    @Override
    @Query("SELECT l " +
            "FROM Lending l " +
                "JOIN ReaderDetails r ON l.readerDetails.pk = r.pk " +
            "WHERE r.readerNumber.readerNumber = :readerNumber " +
                "AND l.returnedDate IS NULL")
    List<Lending> listOutstandingByReaderNumber(@Param("readerNumber") String readerNumber);

    @Override
    @Query(value =
            "SELECT AVG(DATEDIFF(day, l.start_date, l.returned_date)) " +
            "FROM Lending l"
            , nativeQuery = true)
    Double getAverageDuration();

    @Override
    @Query(value =
            "SELECT AVG(DATEDIFF(day, l.start_date, l.returned_date)) " +
                    "FROM Lending l " +
                    "JOIN BOOK b ON l.BOOK_PK = b.PK " +
                    "WHERE b.ISBN = :isbn"
            , nativeQuery = true)
    Double getAvgLendingDurationByIsbn(@Param("isbn") String isbn);


}

interface LendingRepoCustom {
    List<Lending> getOverdue(Page page);

}

@RequiredArgsConstructor
class LendingRepoCustomImpl implements LendingRepoCustom {
    // get the underlying JPA Entity Manager via spring thru constructor dependency
    // injection
    private final EntityManager em;

    @Override
    public List<Lending> getOverdue(Page page)
    {

        final CriteriaBuilder cb = em.getCriteriaBuilder();
        final CriteriaQuery<Lending> cq = cb.createQuery(Lending.class);
        final Root<Lending> root = cq.from(Lending.class);
        cq.select(root);

        final List<Predicate> where = new ArrayList<>();

        // Select overdue lendings where returnedDate is null and limitDate is before the current date
        where.add(cb.isNull(root.get("returnedDate")));
        where.add(cb.lessThan(root.get("limitDate"), LocalDate.now()));

        cq.where(where.toArray(new Predicate[0]));
        cq.orderBy(cb.asc(root.get("limitDate"))); // Order by limitDate, oldest first

        final TypedQuery<Lending> q = em.createQuery(cq);
        q.setFirstResult((page.getNumber() - 1) * page.getLimit());
        q.setMaxResults(page.getLimit());

        return q.getResultList();
    }
}
