package pt.psoft.g1.psoftg1.shared.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import pt.psoft.g1.psoftg1.shared.services.Base65Service;

@Getter
@Setter
@Entity
@Table(name = "forbidden_name")
public class ForbiddenNameSQL {

    @Id
    private String id;

    private String forbiddenName;

    public ForbiddenNameSQL(ForbiddenName forbiddenName) {
        Base65Service base65Service = new Base65Service();
        this.id = base65Service.generateIdSQL();
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
