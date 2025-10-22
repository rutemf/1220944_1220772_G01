package pt.psoft.g1.psoftg1.lendingmanagement.model;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import pt.psoft.g1.psoftg1.bookmanagement.model.BookNoSQL;
import pt.psoft.g1.psoftg1.readermanagement.model.ReaderDetails;
import pt.psoft.g1.psoftg1.readermanagement.model.ReaderDetailsNoSQL;
import pt.psoft.g1.psoftg1.shared.services.IDGeneratorService;

import java.time.LocalDate;

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
        IDGeneratorService.generateIdNoSQL();
        this.lendingNumber = lending.getLendingNumber().toString();
        this.book = lending.getBook() != null ? BookNoSQL.fromDomain(lending.getBook()) : null;
        this.readerDetails = new ReaderDetailsNoSQL(lending.getReaderDetails());
        this.startDate = lending.getStartDate().toString();
        this.limitDate = lending.getLimitDate().toString();
        this.returnedDate = lending.getReturnedDate().toString();
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
                readerDetails != null ? readerDetails.toDomain() : null,
                daysUntilReturn != null ? daysUntilReturn : 0,
                fineValuePerDayInCents
        );
    }

    public static LendingNoSQL fromDomain(Lending lending) {
        return new LendingNoSQL(lending);
    }
}
