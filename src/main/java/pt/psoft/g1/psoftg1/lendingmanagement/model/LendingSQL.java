package pt.psoft.g1.psoftg1.lendingmanagement.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import pt.psoft.g1.psoftg1.bookmanagement.model.BookSQL;
import pt.psoft.g1.psoftg1.readermanagement.model.ReaderDetailsSQL;
import pt.psoft.g1.psoftg1.shared.services.IDGeneratorService;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Entity
@Getter
@Setter
@Table(name = "Lending", uniqueConstraints = {@UniqueConstraint(columnNames={"LENDING_NUMBER"})})
public class LendingSQL {

    @Id
    private String id;

    private String lendingNumber;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private BookSQL book;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private ReaderDetailsSQL readerDetails;

    @NotNull
    @Column(nullable = false, updatable = false)
    private String startDate;

    @NotNull
    @Column(nullable = false)
    private String limitDate;

    @Column
    private String returnedDate;

    @Size(max = 1024)
    @Column(length = 1024)
    private String commentary;

    private int fineValuePerDayInCents;

    @Transient
    private Integer daysUntilReturn;

    @Transient
    private Integer daysOverdue;

    public LendingSQL(Lending lending) {
        this.id = IDGeneratorService.generateIdSQL();
        this.lendingNumber = lending.getLendingNumber() != null ? lending.getLendingNumber().toString() : null;
        this.book = lending.getBook() != null ? BookSQL.fromDomain(lending.getBook()) : null;
        this.readerDetails = lending.getReaderDetails() != null ? ReaderDetailsSQL.fromDomain(lending.getReaderDetails()) : null;
        this.startDate = lending.getStartDate().toString();
        this.limitDate = lending.getLimitDate().toString();
        this.returnedDate = lending.getReturnedDate() != null ? lending.getReturnedDate().toString() : null;
        this.commentary = lending.getCommentary();
        this.fineValuePerDayInCents = lending.getFineValuePerDayInCents();
        this.daysUntilReturn = lending.getDaysUntilReturn() != null ? lending.getDaysUntilReturn() : 0;
        this.daysOverdue = lending.getDaysOverdue() != null ? lending.getDaysOverdue() : 0;
    }

    // JPA
    protected LendingSQL() { }

    public Lending toDomain() {
        LocalDate start = LocalDate.parse(this.startDate);
        LocalDate limit = LocalDate.parse(this.limitDate);
        int durationInDays = (int) ChronoUnit.DAYS.between(start, limit);

        return new Lending(
                book != null ? book.toDomain() : null,
                readerDetails != null ? readerDetails.toDomain() : null,
                durationInDays,
                fineValuePerDayInCents);
    }

    public static LendingSQL fromDomain(Lending lending) {
        return new LendingSQL(lending);
    }
}

