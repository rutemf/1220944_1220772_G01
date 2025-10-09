package pt.psoft.g1.psoftg1.shared.model;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import pt.psoft.g1.psoftg1.shared.services.Base65Service;

@Getter
@Setter
@Document(collection = "forbidden_names")
public class ForbiddenNameNoSQL {

    @Id
    private String id;

    private String forbiddenName;

    public ForbiddenNameNoSQL(ForbiddenName forbiddenName) {
        Base65Service base65Service = new Base65Service();
        this.id = base65Service.generateIdNoSQL();
        this.forbiddenName = forbiddenName.getForbiddenName();
    }

    // MongoDB
    protected ForbiddenNameNoSQL() {}

    public ForbiddenName toDomain() {
        return new ForbiddenName(forbiddenName);
    }

    public static ForbiddenNameSQL fromDomain(ForbiddenName forbiddenName) {
        return new ForbiddenNameSQL(forbiddenName);
    }
}
