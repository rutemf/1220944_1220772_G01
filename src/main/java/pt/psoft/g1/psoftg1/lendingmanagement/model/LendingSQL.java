package pt.psoft.g1.psoftg1.lendingmanagement.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import pt.psoft.g1.psoftg1.bookmanagement.model.BookSQL;
import pt.psoft.g1.psoftg1.readermanagement.model.ReaderDetailsSQL;
import pt.psoft.g1.psoftg1.shared.services.IDGeneratorService;

@Entity
@Getter
@Setter
@Table(name = "Lending", uniqueConstraints = {@UniqueConstraint(columnNames={"LENDING_NUMBER"})})
public class LendingSQL {

    @Id
    private String id;

    @Embedded
    private LendingNumber lendingNumber;

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
        IDGeneratorService.generateIdSQL();
        this.lendingNumber = lending.getLendingNumber();
        this.book = lending.getBook() != null ? BookSQL.fromDomain(lending.getBook()) : null;
        this.readerDetails = lending.getReaderDetails() != null ? ReaderDetailsSQL.fromDomain(lending.getReaderDetails()) : null;
        this.startDate = lending.getStartDate().toString();
        this.limitDate = lending.getLimitDate().toString();
        this.returnedDate = lending.getReturnedDate().toString();
        this.commentary = lending.getCommentary();
        this.fineValuePerDayInCents = lending.getFineValuePerDayInCents();
        this.daysUntilReturn = lending.getDaysUntilReturn();
        this.daysOverdue = lending.getDaysOverdue();
    }

    // JPA
    protected LendingSQL() { }

    public Lending toDomain() {
        return new Lending(
                book != null ? book.toDomain() : null,
                readerDetails != null ? readerDetails.toDomain() : null,
                daysUntilReturn,
                fineValuePerDayInCents);
    }

    public static LendingSQL fromDomain(Lending lending) {
        return new LendingSQL(lending);
    }
}

