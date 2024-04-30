package pt.psoft.g1.psoftg1.lendingmanagement.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import pt.psoft.g1.psoftg1.bookmanagement.model.Book;
import pt.psoft.g1.psoftg1.readermanagement.model.Reader;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Entity
public class Lending {
    private static final int MAX_DAYS_PER_LENDING = 15;    //TODO: Move this to a properties file

    @EmbeddedId
    @Getter
    private LendingNumber lendingNumber;

    @NotNull
    @NotBlank
    @ManyToOne(fetch=FetchType.LAZY)
    @Column(nullable = false, updatable = false)
    @JoinColumn(name = "ISBN")
    private Book book;

    @NotNull
    @NotBlank
    @ManyToOne(fetch=FetchType.LAZY)
    @Column(nullable = false, updatable = false)
    @JoinColumn(name = "READER_NUMBER")
    private Reader reader;

    @NotNull
    @NotBlank
    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.DATE)
    private LocalDate startDate;

    @NotNull
    @NotBlank
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private LocalDate limitDate;

    @Temporal(TemporalType.DATE)
    private LocalDate returnDate;

    // optimistic-lock
    @Version
    @Getter
    private long version;

    @Column()
    @Size(min = 0, max = 2048) //TODO Ricardo: confirm with client answer
    private String commentary;

    @OneToOne(fetch=FetchType.LAZY, mappedBy = "lending", cascade = CascadeType.ALL, orphanRemoval = true)
    private Fine fine;

    public Lending(Book book, Reader reader, LendingNumber lendingNumber){
        this.lendingNumber = lendingNumber;
        this.book = book;
        this.reader = reader;
        this.startDate = LocalDate.now();
        this.limitDate = LocalDate.now().plusDays(MAX_DAYS_PER_LENDING);
        this.fine = null;
    }

    public void setReturnDate(){
        if (this.returnDate != null){
            throw new IllegalArgumentException("book has already been returned!");
        }
        this.returnDate = LocalDate.now();
    }

    public void setReturned(final long desiredVersion, final String commentary){
        this.commentary = commentary;
        setReturnDate();
        if(returnDate.isAfter(limitDate)){
            this.fine = new Fine(this);
        }
    }

    int getDaysDelayed(){
        return (int) ChronoUnit.DAYS.between(this.returnDate, this.limitDate);
    }

    protected Lending() {
        // for ORM only
    }
}
