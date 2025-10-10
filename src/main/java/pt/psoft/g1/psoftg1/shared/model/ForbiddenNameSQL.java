package pt.psoft.g1.psoftg1.shared.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import pt.psoft.g1.psoftg1.shared.services.IDGeneratorService;

@Getter
@Setter
@Entity
@Table(name = "forbidden_name")
public class ForbiddenNameSQL {

    @Id
    private String id;

    private String forbiddenName;

    public ForbiddenNameSQL(ForbiddenName forbiddenName) {
        IDGeneratorService IDGeneratorService = new IDGeneratorService();
        this.id = IDGeneratorService.generateIdSQL();
        this.forbiddenName = forbiddenName.getForbiddenName();
    }

    // JPA
    protected ForbiddenNameSQL() {}

    public ForbiddenName toDomain() {
        return new ForbiddenName(forbiddenName);
    }

    public static ForbiddenNameSQL fromDomain(ForbiddenName forbiddenName) {
        return new ForbiddenNameSQL(forbiddenName);
    }
}
