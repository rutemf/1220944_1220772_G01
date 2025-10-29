package pt.psoft.g1.psoftg1.lendingmanagement.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import pt.psoft.g1.psoftg1.shared.services.IDGeneratorService;

@Getter
@Setter
@Document(collection = "fines")
public class FineNoSQL {

    @Id
    private String id;
    private int fineValuePerDayInCents;
    private int centsValue;
    private LendingNoSQL lending;

    public FineNoSQL(Fine fine) {
        this.id = IDGeneratorService.generateIdNoSQL();
        this.fineValuePerDayInCents = fine.getFineValuePerDayInCents();
        this.centsValue = fine.getCentsValue();
        if (fine.getLending() != null) {
            this.lending = LendingNoSQL.fromDomain(fine.getLending());
        } else {
            this.lending = null;
        }
    }

    // NoSQL
    protected FineNoSQL() {}

    public Fine toDomain() {
        if (this.lending == null) return null;
        Lending lendingDomain = this.lending.toDomain();
        Fine fine = new Fine(lendingDomain);
        fine.setCentsValue(this.centsValue);
        fine.setFineValuePerDayInCents(this.fineValuePerDayInCents);
        return fine;
    }

    public static FineNoSQL fromDomain(Fine fine) {
        return new FineNoSQL(fine);
    }

}
