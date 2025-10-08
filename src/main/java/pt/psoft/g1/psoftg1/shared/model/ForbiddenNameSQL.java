package pt.psoft.g1.psoftg1.shared.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class ForbiddenNameSQL {

    @Id
    private String id;

    private String forbiddenName;

    public ForbiddenNameSQL(String forbiddenName) {
        this.forbiddenName = forbiddenName;
    }

    // JPA
    protected ForbiddenNameSQL() {}

    public ForbiddenName toDomain() {
        return new ForbiddenName(forbiddenName);
    }

    public static ForbiddenNameSQL fromDomain(ForbiddenName forbiddenName) {
        return new ForbiddenNameSQL(forbiddenName.getForbiddenName());
    }
}
