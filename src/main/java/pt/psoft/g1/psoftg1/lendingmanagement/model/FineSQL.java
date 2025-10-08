package pt.psoft.g1.psoftg1.lendingmanagement.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "Fine")
public class FineSQL {
    @Id
    private String id;

    @Column(nullable = false)
    private int fineValuePerDayInCents;

    @Column(nullable = false)
    private int centsValue;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private LendingSQL lending;

    public FineSQL(Fine fine, LendingSQL lendingSQL) {
        this.id = fine.getId();
        this.fineValuePerDayInCents = fine.getFineValuePerDayInCents();
        this.centsValue = fine.getCentsValue();
        this.lending = lendingSQL;
    }

    // JPA
    protected FineSQL() {}

    public Fine toDomain() {
        Fine fine = new Fine(this.lending.toDomain());
        fine.setCentsValue(this.centsValue);
        return fine;
    }

    public static FineSQL fromDomain(Fine fine, LendingSQL lendingSQL) {
        return new FineSQL(fine, lendingSQL);
    }

}
