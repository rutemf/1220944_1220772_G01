package pt.psoft.g1.psoftg1.lendingmanagement.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import pt.psoft.g1.psoftg1.bookmanagement.model.Book;
import pt.psoft.g1.psoftg1.readermanagement.model.Reader;

import java.time.LocalDateTime;

@Entity
@Table(uniqueConstraints={
        @UniqueConstraint(columnNames = {"YEAR", "NUMBER"})
})
public class Lending {
    @EmbeddedId
    LendingNumber lendingNumber;

    @NotNull
    @NotBlank
    @ManyToOne
    @Column(nullable = false, updatable = false)
    Book book;

    @NotNull
    @NotBlank
    @ManyToOne
    @Column(nullable = false, updatable = false)
    Reader reader;

    @NotNull
    @NotBlank
    @Column(nullable = false, updatable = false)
    LocalDateTime startDate;

    @Column(nullable = false)
    LocalDateTime limitDate;

    LocalDateTime returnDate;

    @OneToOne(mappedBy = "lending", cascade = CascadeType.ALL, orphanRemoval = true, optional = true)
    Fine fine;

    protected Lending() {
        // for ORM only
    }

}
