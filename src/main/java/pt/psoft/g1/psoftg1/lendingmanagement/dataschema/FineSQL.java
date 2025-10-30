package pt.psoft.g1.psoftg1.lendingmanagement.dataschema;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import pt.psoft.g1.psoftg1.lendingmanagement.model.Fine;
import pt.psoft.g1.psoftg1.shared.services.IDGeneratorService;

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

    public FineSQL(Fine fine) {
        this.id = IDGeneratorService.generateIdSQL();
        this.fineValuePerDayInCents = fine.getFineValuePerDayInCents();
        this.centsValue = fine.getCentsValue();
        this.lending = LendingSQL.fromDomain(fine.getLending());
    }

    // JPA
    protected FineSQL() {
    }

    public Fine toDomain() {
        Fine fine = new Fine(this.lending.toDomain());
        fine.setCentsValue(this.centsValue);
        fine.setFineValuePerDayInCents(this.fineValuePerDayInCents);
        return fine;
    }

    public static FineSQL fromDomain(Fine fine) {
        return new FineSQL(fine);
    }

}
