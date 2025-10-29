package pt.psoft.g1.psoftg1.lendingmanagement.model;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import pt.psoft.g1.psoftg1.bookmanagement.model.BookNoSQL;
import pt.psoft.g1.psoftg1.bookmanagement.model.BookSQL;
import pt.psoft.g1.psoftg1.readermanagement.model.ReaderDetails;
import pt.psoft.g1.psoftg1.readermanagement.model.ReaderDetailsNoSQL;
import pt.psoft.g1.psoftg1.readermanagement.model.ReaderDetailsSQL;
import pt.psoft.g1.psoftg1.shared.services.IDGeneratorService;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Document(collection = "lendings")
@Getter
@Setter
public class LendingNoSQL {

    @Id
    private String id;
    private String lendingNumber;
    private BookNoSQL book;
    private ReaderDetailsNoSQL readerDetails;
    private String startDate;
    private String limitDate;
    private String returnedDate;
    private String commentary;
    private int fineValuePerDayInCents;
    private Integer daysUntilReturn;
    private Integer daysOverdue;

    public LendingNoSQL(Lending lending) {
        this.id = IDGeneratorService.generateIdNoSQL();
        this.lendingNumber = lending.getLendingNumber() != null ? lending.getLendingNumber().toString() : null;
        this.book = lending.getBook() != null ? BookNoSQL.fromDomain(lending.getBook()) : null;
        this.readerDetails = lending.getReaderDetails() != null ? ReaderDetailsNoSQL.fromDomain(lending.getReaderDetails()) : null;
        this.startDate = lending.getStartDate().toString();
        this.limitDate = lending.getLimitDate().toString();
        this.returnedDate = lending.getReturnedDate() != null ? lending.getReturnedDate().toString() : null;
        this.commentary = lending.getCommentary();
        this.fineValuePerDayInCents = lending.getFineValuePerDayInCents();
        this.daysUntilReturn = lending.getDaysUntilReturn() != null ? lending.getDaysUntilReturn() : 0;
        this.daysOverdue = lending.getDaysOverdue() != null ? lending.getDaysOverdue() : 0;
    }

    // NoSQL
    protected LendingNoSQL() {}

    public Lending toDomain() {
        LocalDate start = LocalDate.parse(this.startDate);
        LocalDate limit = LocalDate.parse(this.limitDate);
        int durationInDays = (int) ChronoUnit.DAYS.between(start, limit);

        Lending lending = new Lending(
                book != null ? book.toDomain() : null,
                readerDetails != null ? readerDetails.toDomain() : null,
                durationInDays,
                fineValuePerDayInCents);

        lending.setLendingNumber(new LendingNumber(this.lendingNumber));

        return lending;
    }

    public static LendingNoSQL fromDomain(Lending lending) {
        return new LendingNoSQL(lending);
    }
}
