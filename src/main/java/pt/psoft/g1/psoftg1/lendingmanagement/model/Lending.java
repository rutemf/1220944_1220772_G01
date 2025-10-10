package pt.psoft.g1.psoftg1.lendingmanagement.model;

import lombok.Getter;
import lombok.Setter;
import pt.psoft.g1.psoftg1.bookmanagement.model.Book;
import pt.psoft.g1.psoftg1.readermanagement.model.ReaderDetails;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.Optional;

@Getter
@Setter
public class Lending {

    private LendingNumber lendingNumber;
    private Book book;
    private ReaderDetails readerDetails;
    private LocalDate startDate;
    private LocalDate limitDate;
    private LocalDate returnedDate;
    private String commentary = null;
    private Integer daysUntilReturn;
    private Integer daysOverdue;
    private int fineValuePerDayInCents;

    public Lending(Book book, ReaderDetails readerDetails, int lendingDuration, int fineValuePerDayInCents) {
        this.lendingNumber = new LendingNumber(1, 2);
        this.book = book;
        this.readerDetails = readerDetails;
        this.startDate = LocalDate.now();
        this.limitDate = startDate.plusDays(lendingDuration);
        this.returnedDate = null;
        this.daysUntilReturn = lendingDuration;
        this.daysOverdue = 0;
        this.fineValuePerDayInCents = fineValuePerDayInCents;
    }

    @Override
    public String toString() {
        return "Lending: " + lendingNumber;
    }

    public int getDaysDelayed() {
        return Math.max((int) ChronoUnit.DAYS.between(limitDate, Objects.requireNonNullElseGet(returnedDate, LocalDate::now)), 0);
    }

    public Optional<Integer> getFineValueInCents() {
        return Optional.of(getDaysDelayed() * fineValuePerDayInCents);
    }
}
