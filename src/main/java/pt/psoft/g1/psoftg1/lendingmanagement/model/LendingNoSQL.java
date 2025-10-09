package pt.psoft.g1.psoftg1.lendingmanagement.model;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import pt.psoft.g1.psoftg1.bookmanagement.model.BookNoSQL;
import pt.psoft.g1.psoftg1.bookmanagement.model.BookSQL;
import pt.psoft.g1.psoftg1.readermanagement.model.ReaderDetails;
import pt.psoft.g1.psoftg1.shared.services.Base65Service;

import java.time.LocalDate;

@Document(collection = "lendings")
@Getter
@Setter
public class LendingNoSQL {

    @Id
    private String id;

    private LendingNumber lendingNumber;

    private BookNoSQL book;

    private ReaderDetails readerDetails;

    private LocalDate startDate;
    private LocalDate limitDate;
    private LocalDate returnedDate;
    private String commentary;
    private int fineValuePerDayInCents;

    private Integer daysUntilReturn;
    private Integer daysOverdue;

    public LendingNoSQL(Lending lending) {
        Base65Service base65Service = new Base65Service();
        base65Service.generateIdNoSQL();
        this.lendingNumber = lending.getLendingNumber();
        this.book = lending.getBook() != null ? BookNoSQL.fromDomain(lending.getBook()) : null;
        this.readerDetails = lending.getReaderDetails();
        this.startDate = lending.getStartDate();
        this.limitDate = lending.getLimitDate();
        this.returnedDate = lending.getReturnedDate();
        this.commentary = lending.getCommentary();
        this.fineValuePerDayInCents = lending.getFineValuePerDayInCents();
        this.daysUntilReturn = lending.getDaysUntilReturn();
        this.daysOverdue = lending.getDaysOverdue();
    }

    // NoSQL
    protected LendingNoSQL() {}

    public Lending toDomain() {
        return new Lending(
                book != null ? book.toDomain() : null,
                readerDetails,
                daysUntilReturn != null ? daysUntilReturn : 0,
                fineValuePerDayInCents
        );
    }

    public static LendingNoSQL fromDomain(Lending lending) {
        return new LendingNoSQL(lending);
    }
}
