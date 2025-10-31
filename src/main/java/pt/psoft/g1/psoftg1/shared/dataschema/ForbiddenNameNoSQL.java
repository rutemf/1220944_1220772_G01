package pt.psoft.g1.psoftg1.shared.dataschema;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import pt.psoft.g1.psoftg1.shared.model.ForbiddenName;
import pt.psoft.g1.psoftg1.shared.services.IDGeneratorService;

@Getter
@Setter
@Document(collection = "forbidden_names")
public class ForbiddenNameNoSQL {

    @Id
    private String id;

    private String forbiddenName;

    public ForbiddenNameNoSQL(ForbiddenName forbiddenName) {
        this.id = IDGeneratorService.generateIdNoSQL();
        this.forbiddenName = forbiddenName.getForbiddenName();
    }

    // MongoDB
    protected ForbiddenNameNoSQL() {}

    public ForbiddenName toDomain() {
        return new ForbiddenName(forbiddenName);
    }

    public static ForbiddenNameNoSQL fromDomain(ForbiddenName forbiddenName) {
        return new ForbiddenNameNoSQL(forbiddenName);
    }
}
