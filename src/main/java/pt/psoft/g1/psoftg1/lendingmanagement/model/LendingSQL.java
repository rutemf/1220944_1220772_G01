package pt.psoft.g1.psoftg1.lendingmanagement.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import pt.psoft.g1.psoftg1.bookmanagement.model.BookSQL;
import pt.psoft.g1.psoftg1.readermanagement.model.ReaderDetails;
import pt.psoft.g1.psoftg1.readermanagement.model.ReaderDetailsSQL;
import pt.psoft.g1.psoftg1.shared.services.Base65Service;

import java.time.LocalDate;

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
    private LocalDate startDate;

    @NotNull
    @Column(nullable = false)
    private LocalDate limitDate;

    @Column
    private LocalDate returnedDate;

    @Size(max = 1024)
    @Column(length = 1024)
    private String commentary;

    private int fineValuePerDayInCents;

    @Transient
    private Integer daysUntilReturn;

    @Transient
    private Integer daysOverdue;

    public LendingSQL(Lending lending) {
        Base65Service base65Service = new Base65Service();
        base65Service.generateIdSQL();
        this.lendingNumber = lending.getLendingNumber();
        this.book = lending.getBook() != null ? BookSQL.fromDomain(lending.getBook()) : null;
        this.readerDetails = lending.getReaderDetails() != null ? ReaderDetailsSQL.fromDomain(lending.getReaderDetails()) : null;
        this.startDate = lending.getStartDate();
        this.limitDate = lending.getLimitDate();
        this.returnedDate = lending.getReturnedDate();
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

