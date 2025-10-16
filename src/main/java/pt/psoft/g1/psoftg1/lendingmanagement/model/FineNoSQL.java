package pt.psoft.g1.psoftg1.lendingmanagement.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import pt.psoft.g1.psoftg1.shared.services.IDBase65GeneratorService;
import pt.psoft.g1.psoftg1.shared.services.IDGeneratorService;

@Getter
@Setter
@Document(collection = "fines")
public class FineNoSQL {

    @Transient
    private IDGeneratorService idGeneratorService;

    @Id
    private String id;

    private int fineValuePerDayInCents;

    private int centsValue;

    @DBRef
    private LendingNoSQL lending;

    public FineNoSQL(Fine fine) {
        this.id = idGeneratorService.generateId();
        this.fineValuePerDayInCents = fine.getFineValuePerDayInCents();
        this.centsValue = fine.getCentsValue();
        this.lending = LendingNoSQL.fromDomain(fine.getLending());
    }

    // NoSQL
    protected FineNoSQL() {}

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
