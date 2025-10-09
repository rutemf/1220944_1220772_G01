package pt.psoft.g1.psoftg1.lendingmanagement.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import pt.psoft.g1.psoftg1.shared.services.Base65Service;

@Getter
@Setter
@Document(collection = "fines")
public class FineNoSQL {

    @Id
    private String id;

    private int fineValuePerDayInCents;

    private int centsValue;

    @DBRef
    private LendingNoSQL lending;

    public FineNoSQL(Fine fine) {
        Base65Service base65Service = new Base65Service();
        this.id = base65Service.generateIdNoSQL();
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
